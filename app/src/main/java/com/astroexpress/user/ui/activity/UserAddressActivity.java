package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.user.adapter.LoadSavedAddressAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityUserAddressBinding;
import com.astroexpress.user.model.request.SaveUserAddressRequestModel;
import com.astroexpress.user.model.responsemodel.GetAllSavedResponseModel;
import com.astroexpress.user.model.responsemodel.UserAddressSaveResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddressActivity extends AppCompatActivity {

    Context context;
    ActivityUserAddressBinding binding;
    String remedyId,remedyPrice;
    Boolean IsOwnedRemedy;
    Boolean userName, userAddress1, userAddress2, userLandmark, userCity, userMobile, userPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.layoutToolbar.toolbar;
        toolbar.setTitle("Saved Address");
        setSupportActionBar(toolbar);

        context=UserAddressActivity.this;
        remedyId = getIntent().getStringExtra("remedyId");
        remedyPrice = getIntent().getStringExtra("remedyPrice");
        IsOwnedRemedy=getIntent().getBooleanExtra("IsOwnedRemedy",false);

        callUserSavedAddressApi();

        binding.btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.flView.setVisibility(View.VISIBLE);
                binding.loadUserSavedAddressList.setVisibility(View.GONE);
                binding.btnAddNewAddress.setVisibility(View.GONE);
                binding.llSaveAddress.setVisibility(View.VISIBLE);
            }
        });

        binding.btnSubmitNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForValidation();
            }
        });

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
    private void callSaveUserAddressApi(SaveUserAddressRequestModel saveUserAddressRequestModel) {

        RetrofitAPIService.getApiClient().saveUserAddress(saveUserAddressRequestModel).enqueue(new Callback<UserAddressSaveResponseModel>() {
            @Override
            public void onResponse(Call<UserAddressSaveResponseModel> call, Response<UserAddressSaveResponseModel> response) {
                try {
                    binding.lottieProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful() && response.body() != null) {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        callUserSavedAddressApi();

                    } else {
                        binding.lottieProgressBar.setVisibility(View.GONE);

                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                    Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAddressSaveResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callUserSavedAddressApi() {
        RetrofitAPIService.getApiClient().getAllAddress(AllStaticFields.userData.getUserId()).enqueue(new Callback<GetAllSavedResponseModel>() {
            @Override
            public void onResponse(Call<GetAllSavedResponseModel> call, Response<GetAllSavedResponseModel> response) {
                try {
                    binding.lottieProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful() && response.body() != null)
                    {

                        if (response.body().getCode().equals("200"))
                        {
                            binding.loadUserSavedAddressList.setVisibility(View.VISIBLE);
                            binding.llSaveAddress.setVisibility(View.GONE);
                            List<GetAllSavedResponseModel.Result> myList = new ArrayList<>();
                            myList.addAll(response.body().getResult());
                            LoadSavedAddressAdapter loadSavedAddressAdapter = new LoadSavedAddressAdapter(context, myList,remedyId,remedyPrice,IsOwnedRemedy);
                            binding.loadUserSavedAddressList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            binding.loadUserSavedAddressList.setAdapter(loadSavedAddressAdapter);
                            binding.btnAddNewAddress.setVisibility(View.VISIBLE);

                        }
                        else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.btnAddNewAddress.setVisibility(View.VISIBLE);
                            binding.flView.setVisibility(View.GONE);
                        }

                    } else {
                        binding.lottieProgressBar.setVisibility(View.GONE);

                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                    Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    binding.lottieProgressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<GetAllSavedResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void checkForValidation() {

        if (!binding.editUserName.getText().toString().isEmpty()) {
            userName = true;
        } else {
            binding.editUserName.setError("Please enter user name");
            userName=false;
        }

        if (!binding.editUserAddress1.getText().toString().isEmpty()) {
            userAddress1 = true;
        } else {
            userAddress1=false;
            binding.editUserAddress1.setError("Please enter House No/Plot No/Building No");
        }

        if (!binding.editUserAddress2.getText().toString().isEmpty()) {
            userAddress2 = true;
        } else {
            userAddress2=false;
            binding.editUserAddress2.setError("Please enter Area/Colony/Road Name");
        }

        if (!binding.editUserLandmark.getText().toString().isEmpty()) {
            userLandmark = true;
        } else {
            userLandmark=false;
            binding.editUserLandmark.setError("Please enter Landmark");
        }

        if (!binding.editUserCity.getText().toString().isEmpty()) {
            userCity = true;
        } else {
            userCity=false;
            binding.editUserCity.setError("Please enter City");
        }

        if (!binding.editUserMobile.getText().toString().isEmpty()) {
            userMobile = true;
        } else {
            userMobile=false;
            binding.editUserMobile.setError("Please enter Mobile");
        }

        if (!binding.editUserPin.getText().toString().isEmpty()) {
            userPin = true;
        } else {
            userPin=false;
            binding.editUserPin.setError("Please enter Pin");
        }

        if (userName && userAddress1 && userAddress2 && userLandmark && userCity && userMobile && userPin)
        {
            SaveUserAddressRequestModel saveUserAddressRequestModel = new SaveUserAddressRequestModel(
                    binding.editUserAddress1.getText().toString(),
                    binding.editUserAddress2.getText().toString(),
                    null,
                    binding.editUserLandmark.getText().toString(),
                    binding.editUserCity.getText().toString(),
                    binding.editUserPin.getText().toString(),
                    binding.editUserName.getText().toString(),
                    binding.editUserMobile.getText().toString(),
                    "+91",
                    null,
                    null,AllStaticFields.userData.getUserId(),
                    null,
                    false
            );

            callSaveUserAddressApi(saveUserAddressRequestModel);
            binding.lottieProgressBar.setVisibility(View.VISIBLE);
        }
    }

}