package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityMyWalletTransactionBinding;
import com.astroexpress.user.model.responsemodel.WalletDeductionResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletTransactionActivity extends AppCompatActivity {

    Context context;
    ActivityMyWalletTransactionBinding binding;
    NetworkChange networkChange = new NetworkChange();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWalletTransactionBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        Toolbar toolbar = binding.layoutToolbar.toolbar;
        toolbar.setTitle("Transaction History");
        setSupportActionBar(toolbar);
        context = MyWalletTransactionActivity.this;
        loadMyTransactionFromAPI();
        binding.proceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyWalletTransactionActivity.this, WalletActivity.class);
                startActivity(intent1);
            }
        });
        callWalletBalanceApi();

    }

    private void callWalletBalanceApi() {

        RetrofitAPIService.getApiClient().getWallet(AllStaticFields.userData.getUserId()).enqueue(new Callback<WalletResponseModel>() {
            @Override
            public void onResponse(Call<WalletResponseModel> call, Response<WalletResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            binding.txtUserWalletAmount.setVisibility(View.VISIBLE);
                            binding.txtUserWalletAmount.setText("\u20b9 " + response.body().getResult().getRechargeAmount());

                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<WalletResponseModel> call, Throwable t) {

            }
        });

    }

    private void loadMyTransactionFromAPI() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);

        RetrofitAPIService.getApiClient().getWalletTransaction(AllStaticFields.userData.getUserId()).enqueue(new Callback<WalletDeductionResponseModel>() {
            @Override
            public void onResponse(Call<WalletDeductionResponseModel> call, Response<WalletDeductionResponseModel> response) {
                {
                    binding.lottieProgressBar.setVisibility(View.GONE);
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getCode().equals("200")) {

                                setList(response.body().getResult());

                            } else if (response.body().getCode().equals("404")) {

                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        AllStaticMethods.saveException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletDeductionResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setList(List<WalletDeductionResponseModel.Result> result) {

        binding.llList.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            WalletDeductionResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_wallet_transaction, null);
            TextView txtCreatedOn = view.findViewById(R.id.txtCreatedOn);
            TextView txtFirstName = view.findViewById(R.id.txtFirstName);
            TextView txtAmount = view.findViewById(R.id.txtAmount);
            TextView txtWalletTransaction = view.findViewById(R.id.txtWalletTransaction);
            TextView txtDebitedForm = view.findViewById(R.id.txtDebitedForm);
            TextView txtStartTime = view.findViewById(R.id.txtStartTime);
//            TextView txtEndTime = view.findViewById(R.id.txtEndTime);
            TextView txtTransactionFor = view.findViewById(R.id.txtTransactionFor);

            txtCreatedOn.setText(dateFormatter(itemData.getCreatedOn()));
            txtFirstName.setText(itemData.getFirstName() + " " + itemData.getLastName());
            txtAmount.setText (" \u20B9"+ itemData.getAmount());
            txtWalletTransaction.setText(itemData.getWalletTransactionId());
            txtDebitedForm.setText(itemData.getDebitedForm());
            txtStartTime.setText(timeFormatter(itemData.getStartTime(),itemData.getEndTime()));
//            txtEndTime.setText(timeFormatter(itemData.getEndTime()));
            txtTransactionFor.setText(itemData.getTransactionFor());

            view.setTag(itemData);
            binding.llList.addView(view);

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public static String dateFormatter(String dateTime){
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateTime);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
        }

    public static String timeFormatter(String StartTime,String EndTime) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
//        String outputPattern = "HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date startTime = null;
        Date endTime = null;
        long elapsedDays,elapsedHours = 0,elapsedMinutes = 0,elapsedSeconds = 0;

        try {
            startTime = inputFormat.parse(StartTime);
            endTime = inputFormat.parse(EndTime);

            long different = endTime.getTime() - startTime.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            elapsedSeconds = different / secondsInMilli;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (elapsedHours==0){
            return (elapsedMinutes + " Min " + elapsedSeconds+ " Sec" );
        }else {

            return (elapsedHours + " Hour " + elapsedMinutes + " Min " + elapsedSeconds + " Sec");
        }
    }


}