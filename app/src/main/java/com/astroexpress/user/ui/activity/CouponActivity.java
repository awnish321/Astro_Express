package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityBillingBinding;
import com.astroexpress.user.databinding.ActivityCouponBinding;
import com.astroexpress.user.model.responsemodel.CouponResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponActivity extends AppCompatActivity {

    ActivityCouponBinding binding;
    Context context;
    String couponCode=null;
    NetworkChange networkChange = new NetworkChange();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCouponBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = CouponActivity.this;

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Coupon");
        setSupportActionBar(toolbar);
        callCouponApi();

        binding.txtBtnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                couponCode= binding.edtCouponCode.getText().toString();
                callCouponApi();

            }
        });

    }

    private void callCouponApi() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);

        RetrofitAPIService.getApiClient().getCoupon(couponCode,AppConstants.WALLET_RECHARGE).enqueue(new Callback<CouponResponseModel>() {
            @Override
            public void onResponse(Call<CouponResponseModel> call, Response<CouponResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            setList(response.body().getResult());
                            binding.lottieProgressBar.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.lottieProgressBar.setVisibility(View.GONE);
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                        binding.lottieProgressBar.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                    binding.lottieProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<CouponResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setList(List<CouponResponseModel.Result> result) {

        binding.llList.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            CouponResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_coupon_code, null);
            TextView txtCouponCode = view.findViewById(R.id.txtCouponCode);
            TextView txtTitle = view.findViewById(R.id.txtTitle);
            TextView txtDescription = view.findViewById(R.id.txtDescription);

            txtCouponCode.setText(itemData.getCouponCode() );
            txtTitle.setText(itemData.getTitle());
            txtDescription.setText(itemData.getDescrption());

            view.setTag(itemData);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AllStaticFields.couponData = (CouponResponseModel.Result) view.getTag();
                    setResult(RESULT_OK);
                    finish();
                }
            });

            binding.llList.addView(view);

        }

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

}