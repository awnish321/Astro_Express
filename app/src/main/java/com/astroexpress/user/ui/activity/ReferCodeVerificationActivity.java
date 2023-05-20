package com.astroexpress.user.ui.activity;

import static org.apache.commons.text.WordUtils.capitalize;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.astroexpress.user.BuildConfig;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityReferCodeVerificationBinding;
import com.astroexpress.user.model.request.LoginSignupRequestModel;
import com.astroexpress.user.model.responsemodel.LoginResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferCodeVerificationActivity extends AppCompatActivity {

    ActivityReferCodeVerificationBinding binding;
    String phoneNumber, FirebaseUserId,referAndEarn=null;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReferCodeVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = ReferCodeVerificationActivity.this;
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        FirebaseUserId = getIntent().getStringExtra("UserId");

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.editReferAndEarn.getText().toString().trim().equals("")){
                    Toast.makeText(context, "enter your referral code", Toast.LENGTH_SHORT).show();
                }else{
                    referAndEarn=binding.editReferAndEarn.getText().toString().trim();
                    binding.llPageLayout.setVisibility(View.GONE);
                    binding.lottieProgressBar.setVisibility(View.VISIBLE);
                    callSignUpApi();
                }
            }
        });

        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llPageLayout.setVisibility(View.GONE);
                binding.lottieProgressBar.setVisibility(View.VISIBLE);
                callSignUpApi();
            }
        });


    }

    private void callSignUpApi() {

        LoginSignupRequestModel loginSignupRequestModel = new LoginSignupRequestModel(
                null,
                null,
                null,
                null,
                null,
                FirebaseUserId,
                null,
                null,
                phoneNumber,
                referAndEarn
        );

        RetrofitAPIService.getApiClient().signup(loginSignupRequestModel).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {
//                        binding.lottieProgressBar.setVisibility(View.GONE);
                        if (response.body().getCode().equals("200") || response.body().getCode().equals("1062")) {
                            SharedPreferenceManager.setUserData(context, response.body().getResult());
                            AllStaticFields.userData = response.body().getResult();
                            startActivity(new Intent(context, NewDashboardActivity.class));
                            finish();
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static String getAndroidVersion() {
        String versionName = "";

        try {
            versionName = String.valueOf(Build.VERSION.RELEASE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public String getBrandModelName() {
        String model = Build.MODEL;
        return capitalize(model) ;
    }

    public String getBrandName() {
        String manufacturer = Build.MANUFACTURER;
        return capitalize(manufacturer) ;
    }

    public static String getAppVersion()  {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        return capitalize(versionName);
    }


}