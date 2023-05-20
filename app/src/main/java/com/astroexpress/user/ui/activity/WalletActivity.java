package com.astroexpress.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityWalletBinding;
import com.astroexpress.user.model.request.OrderRequestModel;
import com.astroexpress.user.model.request.RequestForChatRequestModel;
import com.astroexpress.user.model.responsemodel.OrderResponseModel;
import com.astroexpress.user.model.responsemodel.PaymentTransactionListResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.ui.dialog.MyProgressDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    MyProgressDialog myProgressDialog;
    String enteredAmount;

    NetworkChange networkChange = new NetworkChange();
    private ActivityWalletBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = WalletActivity.this;
        myProgressDialog = new MyProgressDialog(context);

        binding.layoutToolbar.toolbar.setTitle("Wallet");
        setSupportActionBar(binding.layoutToolbar.toolbar);
        binding.txtUserName.setText(AllStaticFields.userData.getFirstName());
        binding.addMoney.setOnClickListener(this);
        binding.couponAddMoneyAmount0.setOnClickListener(this);
        binding.couponAddMoneyAmount1.setOnClickListener(this);
        binding.couponAddMoneyAmount2.setOnClickListener(this);
        binding.couponAddMoneyAmount3.setOnClickListener(this);
        binding.couponAddMoneyAmount4.setOnClickListener(this);
        binding.couponAddMoneyAmount5.setOnClickListener(this);
        binding.couponAddMoneyAmount6.setOnClickListener(this);
        binding.couponAddMoneyAmount7.setOnClickListener(this);
        binding.couponAddMoneyAmount8.setOnClickListener(this);

        callWalletBalanceApi();
        getPaymentTransactionApi();
    }

    private void getPaymentTransactionApi() {
        binding.lottieProgressBar.setVisibility(View.VISIBLE);
        RetrofitAPIService.getApiClient().getPaymentTransaction(AllStaticFields.userData.getUserId()).enqueue(new Callback<PaymentTransactionListResponseModel>() {
            @Override
            public void onResponse(Call<PaymentTransactionListResponseModel> call, Response<PaymentTransactionListResponseModel> response) {
                binding.lottieProgressBar.setVisibility(View.GONE);

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            setList(response.body().getResult());

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
            public void onFailure(Call<PaymentTransactionListResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setList(List<PaymentTransactionListResponseModel.Result> result) {

        binding.llList.removeAllViews();

        for (int i = 0; i < result.size(); i++) {

            PaymentTransactionListResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_for_payment_transaction, null);
            TextView txtDateTime = view.findViewById(R.id.txtDateTime);
            TextView txtPaymentMode = view.findViewById(R.id.txtPaymentMode);
            TextView txtAmount = view.findViewById(R.id.txtAmount);
            TextView txtPaymentType = view.findViewById(R.id.txtPaymentType);
            TextView txtPaymentStatus = view.findViewById(R.id.txtPaymentStatus);
            LinearLayout llPaymentMode = view.findViewById(R.id.llPaymentMode);

            txtDateTime.setText(dateFormatter(itemData.getTransactionDateTime()));

            if (itemData.getPaymentMode().equals("N/A")) {
                llPaymentMode.setVisibility(View.GONE);
            } else {
                txtPaymentMode.setText(itemData.getPaymentMode());
            }
            if (itemData.getPaymentStatus().equals("authorized")) {
                txtPaymentStatus.setText("Failed");
                txtPaymentStatus.setTextColor(getResources().getColor(R.color.red));
            } else {
                txtPaymentStatus.setText(itemData.getPaymentStatus());
            }
            txtAmount.setText(itemData.getAmount());
            txtPaymentType.setText(itemData.getOrderType());

            view.setTag(itemData);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllStaticFields.paymentTransactionData = (PaymentTransactionListResponseModel.Result) view.getTag();
                    startActivity(new Intent(context, TransactionDetailActivity.class));
                }
            });

            binding.llList.addView(view);

        }
    }

    private void callWalletBalanceApi() {

        RetrofitAPIService.getApiClient().getWallet(AllStaticFields.userData.getUserId()).enqueue(new Callback<WalletResponseModel>() {
            @Override
            public void onResponse(Call<WalletResponseModel> call, Response<WalletResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            AllStaticFields.walletData = response.body().getResult();
                            binding.txtWalletAmount.setText("\u20b9 " + response.body().getResult().getRechargeAmount());
                            binding.txtWalletMinute.setText(response.body().getResult().getMinuteAmount());

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return true;
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
    public void onBackPressed() {
        startActivity(new Intent(context, NewDashboardActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addMoney:

                enteredAmount = binding.amount.getText().toString();

                if (enteredAmount.trim().equals("")) {
                    binding.amount.setError("Invalid Amount");
                } else {
                    callGenerateOrderApi();
                }
                break;

            case R.id.couponAddMoneyAmount0:

                int enteredAmount25 = 25;
                enteredAmount = Integer.toString(enteredAmount25);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount1:

                int enteredAmount50 = 50;
                enteredAmount = Integer.toString(enteredAmount50);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount2:

                int enteredAmount100 = 100;
                enteredAmount = Integer.toString(enteredAmount100);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount3:

                int enteredAmount200 = 200;
                enteredAmount = Integer.toString(enteredAmount200);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount4:

                int enteredAmount300 = 300;
                enteredAmount = Integer.toString(enteredAmount300);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount5:

                int enteredAmount500 = 500;
                enteredAmount = Integer.toString(enteredAmount500);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount6:

                int enteredAmount1000 = 1000;
                enteredAmount = Integer.toString(enteredAmount1000);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount7:

                int enteredAmount1500 = 1500;
                enteredAmount = Integer.toString(enteredAmount1500);
                callGenerateOrderApi();
                break;

            case R.id.couponAddMoneyAmount8:

                int enteredAmount2000 = 2000;
                enteredAmount = Integer.toString(enteredAmount2000);
                callGenerateOrderApi();
                break;
        }
    }

    private void callGenerateOrderApi() {

        myProgressDialog.show();
        OrderRequestModel orderRequestModel = new OrderRequestModel(
                AllStaticFields.userData.getUserId(),
                AppConstants.WALLET_RECHARGE,
                enteredAmount,
                false,
                null,
                false,
                null,
                null,
                null);

        RetrofitAPIService.getApiClient().generateOrder(orderRequestModel).enqueue(new Callback<OrderResponseModel>() {
            @Override
            public void onResponse(Call<OrderResponseModel> call, Response<OrderResponseModel> response) {
                myProgressDialog.dismiss();
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            AllStaticFields.orderData = response.body().getResult();
                            Intent intent = new Intent(context, BillingActivity.class);
                            intent.setAction(AppConstants.WALLET_RECHARGE);
                            startActivityForResult(intent, 1);

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
            public void onFailure(Call<OrderResponseModel> call, Throwable t) {
                myProgressDialog.dismiss();
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            callWalletBalanceApi();
            getPaymentTransactionApi();
        }
    }

    public static String dateFormatter(String unFormattedDate) {
        String datetime = null;
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date convertedDate = inputFormat.parse(unFormattedDate);
            datetime = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return datetime;

    }

}