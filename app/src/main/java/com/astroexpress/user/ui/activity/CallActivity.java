package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.user.MainActivity;
import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityCallBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;
import com.astroexpress.user.model.request.CallRequestModel;
import com.astroexpress.user.model.request.RequestForChatRequestModel;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.CallResponseModel;
import com.astroexpress.user.model.responsemodel.MakeVoiceCallResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.ui.dialog.AstrologerNotRespondingCallDialog;
import com.astroexpress.user.ui.dialog.AstrologerNotRespondingChatDialog;
import com.astroexpress.user.ui.dialog.WalletBalanceCheckDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;
import com.astroexpress.user.utility.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallActivity extends AppCompatActivity {

    ActivityCallBinding binding;
    Context context;
    NetworkChange networkChange = new NetworkChange();

    private AstrologerResponseModel.Result astrologerData;
    WalletBalanceCheckDialog walletBalanceCheckDialog;
    private String astrologerId,userId;
    long AstrologerChargePerMinute;
    String RemainingFreeSession = null;
    AstrologerNotRespondingCallDialog astrologerNotRespondingCallDialog;
    Boolean ValidForFree;
    CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        context = CallActivity.this;
        userId = getIntent().getStringExtra("UserId");
        astrologerId = getIntent().getStringExtra("AstrologerId");
        AstrologerChargePerMinute = Long.parseLong(getIntent().getStringExtra("AstrologerChargePerMinute"));
        RemainingFreeSession = getIntent().getStringExtra("RemainingFreeSession");
        ValidForFree = Boolean.valueOf(getIntent().getStringExtra("ValidForFree"));

        callWalletBalanceApi();

        walletBalanceCheckDialog = new WalletBalanceCheckDialog(context, new OnSubmitListeners() {
            @Override
            public void onSubmit(String value) {
                startActivity(new Intent(context, WalletActivity.class));
                walletBalanceCheckDialog.dismiss();
                finish();
            }

            @Override
            public void onCancel(String value) {
                finish();
            }
        });

    }

    private void callWalletBalanceApi() {

        RetrofitAPIService.getApiClient().getWallet(AllStaticFields.userData.getUserId()).enqueue(new Callback<WalletResponseModel>() {
            @Override
            public void onResponse(Call<WalletResponseModel> call, Response<WalletResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200"))
                        {
                            long totalAvailableMinute = 0, walletBalance = 0, finalTimeForChat = 0;
                            WalletResponseModel.Result result = response.body().getResult();

                            if (result.getMinuteAmount() != null && Long.parseLong(result.getMinuteAmount()) > 0) {
                                totalAvailableMinute = Long.parseLong(result.getMinuteAmount());
                            }
                            if (result.getRechargeAmount() != null && Double.parseDouble(result.getRechargeAmount()) > 0) {
                                double value = Double.parseDouble(result.getRechargeAmount());
                                walletBalance = (long) value;
                            }
                            if (totalAvailableMinute==10)
                            {
                                if (ValidForFree && Long.parseLong(RemainingFreeSession) > 0)
                                {
                                    callVoiceCallThroughBackend(Boolean.parseBoolean("True"));
                                }else
                                {
                                    finalTimeForChat = (walletBalance / AstrologerChargePerMinute);
                                    if (finalTimeForChat>=3)
                                    {
                                        callVoiceCallThroughBackend(Boolean.parseBoolean("False"));
                                    }else
                                    {
                                        binding.CorrWrong.setVisibility(View.GONE);
                                        walletBalanceCheckDialog.setTitle("Wallet Balance is low");
                                        walletBalanceCheckDialog.show();
                                    }
                                }
                            }else {
                                finalTimeForChat = (walletBalance / AstrologerChargePerMinute);
                                if (finalTimeForChat>=3)
                                {
                                    callVoiceCallThroughBackend(Boolean.parseBoolean("False"));
                                }else
                                {
                                    binding.CorrWrong.setVisibility(View.GONE);
                                    walletBalanceCheckDialog.setTitle("Wallet Balance is low");
                                    walletBalanceCheckDialog.show();
                                }
                            }

                        }
                        else if (response.body().getCode().equals("404")) {
                            binding.CorrWrong.setVisibility(View.GONE);
                            walletBalanceCheckDialog.setTitle("Wallet Balance is low");
                            walletBalanceCheckDialog.show();
                        }
                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<WalletResponseModel> call, Throwable t) {
                finish();
            }
        });
    }

    private void callVoiceCallThroughBackend(boolean callType) {
        RetrofitAPIService.getApiClient().makeVoiceCall(AllStaticFields.userData.getUserId(), astrologerId,callType).enqueue(new Callback<MakeVoiceCallResponseModel>() {
            @Override
            public void onResponse(Call<MakeVoiceCallResponseModel> call, Response<MakeVoiceCallResponseModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getCode().equals("200"))
                        {
                            startNewNotificationTimer();
                        }
                        else
                        {
                            dialog();
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                            dialog();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                        dialog();
                }
            }

            @Override
            public void onFailure(Call<MakeVoiceCallResponseModel> call, Throwable t) {
                dialog();
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

    @Override
    protected void onPause(){
       super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        startActivity(new Intent(context, NewDashboardActivity.class));
        finish();
   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (timer != null) {
            timer.cancel();
        }
        finish();
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
    }

    public void dialog() {
        astrologerNotRespondingCallDialog = new AstrologerNotRespondingCallDialog(context, new OnSubmitListeners() {
            @Override
            public void onSubmit(String value) {
                startActivity(new Intent(context,NewDashboardActivity.class));
            }

            @Override
            public void onCancel(String value) {
                startActivity(new Intent(context,NewDashboardActivity.class));
            }
        });
        astrologerNotRespondingCallDialog.show();
        astrologerNotRespondingCallDialog.setCanceledOnTouchOutside(false);
    }

    public void startNewNotificationTimer() {
        timer =new CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                dialog();
            }
        };
        timer.start();
    }

}