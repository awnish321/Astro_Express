package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityBillingBinding;
import com.astroexpress.user.model.request.OrderRequestModel;
import com.astroexpress.user.model.responsemodel.CouponResponseModel;
import com.astroexpress.user.model.responsemodel.OrderResponseModel;
import com.astroexpress.user.ui.dialog.MyProgressDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingActivity extends AppCompatActivity {

    ActivityBillingBinding binding;
    Context context;
    MyProgressDialog myProgressDialog;
    String receivedMoney;
    CouponResponseModel.Result couponData;
    NetworkChange networkChange = new NetworkChange();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        context = BillingActivity.this;
        myProgressDialog = new MyProgressDialog(context);
        setSupportActionBar(binding.layoutToolbar.toolbar);
        binding.layoutToolbar.toolbar.setTitle("Billing Page");

        setUIData(AllStaticFields.orderData);

        binding.txtApplyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, CouponActivity.class)
                        .setAction(AppConstants.WALLET_RECHARGE)
                        .putExtra("Amount", AllStaticFields.orderData.getTotalAmount()), 1);
            }
        });

        binding.txtBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.txtApplyCoupon.setVisibility(View.VISIBLE);
                binding.llSelectedCoupon.setVisibility(View.GONE);
                AllStaticFields.couponData = null;
                binding.llCouponName.setVisibility(View.GONE);
                callGenerateOrderApi();
            }
        });

        binding.proceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, PaymentActivity.class).setAction(AppConstants.RAZORPAY_PG), 2);
            }
        });

    }

    private void setUIData(OrderResponseModel.Result orderData) {
        if (orderData.getOrderType().matches(AppConstants.REMEDY_BOOKING))
        {
            binding.cardApplyCoupon.setVisibility(View.GONE);
            binding.txtRechargeType.setText("Remedy Price");
        }
        binding.totalAmount.setText(orderData.getTotalAmount());
        binding.cGst.setText(orderData.getCgst());
        binding.sGst.setText(orderData.getSgst());
        binding.couponDiscount.setText(orderData.getDiscountAmount());
        binding.effectivePrice.setText(orderData.getEffectiveAmount());
        binding.finalAmount.setText(orderData.getFinalAmount());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
//            couponData = AllStaticFields.couponData;

            binding.couponDiscount.setText(AllStaticFields.couponData.getCouponCode() == null ? "" : AllStaticFields.couponData.getCouponCode());
            binding.txtCouponCode.setText(AllStaticFields.couponData.getCouponCode() == null ? "" : AllStaticFields.couponData.getCouponCode());
            binding.txtTitle.setText(AllStaticFields.couponData.getTitle() == null ? "" : AllStaticFields.couponData.getTitle());

            callGenerateOrderApi();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private void callGenerateOrderApi() {

        OrderRequestModel orderRequestModel = new OrderRequestModel(
                AllStaticFields.userData.getUserId(),
                AppConstants.WALLET_RECHARGE,
                AllStaticFields.orderData.getTotalAmount(),
                false,
                null,
                AllStaticFields.couponData != null,
                AllStaticFields.couponData != null ? AllStaticFields.couponData.getCouponCode() : null,
                null,
                null);

        myProgressDialog.show();

        RetrofitAPIService.getApiClient().generateOrder(orderRequestModel).enqueue(new Callback<OrderResponseModel>() {
            @Override
            public void onResponse(Call<OrderResponseModel> call, Response<OrderResponseModel> response) {
                myProgressDialog.dismiss();
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            AllStaticFields.orderData = response.body().getResult();
                            setUIData(AllStaticFields.orderData);

                            if (AllStaticFields.couponData != null) {
                                binding.llCouponName.setVisibility(View.VISIBLE);
                                binding.txtCouponName.setText(AllStaticFields.couponData.getCouponCode());
                                binding.couponDiscount.setText(AllStaticFields.couponData.getDiscountAmount() );

                                binding.txtApplyCoupon.setVisibility(View.GONE);
                                binding.llSelectedCoupon.setVisibility(View.VISIBLE);
                            }

                        } else {
                            binding.couponDiscount.setText("0");
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
//            case R.id.share:
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Astro Express");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download this Application now:- https://play.google.com/store/apps/details?");
//                startActivity(Intent.createChooser(shareIntent, "share via"));
//                break;

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
}