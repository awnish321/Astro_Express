package com.astroexpress.user.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.adapter.MultipleProfileImageAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityAstrologerProfileBinding;
import com.astroexpress.user.databinding.ActivityProfileImageBinding;
import com.astroexpress.user.model.responsemodel.AstrologerMultipleImageResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileImageActivity extends AppCompatActivity {

    ActivityProfileImageBinding binding;
    Context context;
    String[] myStringArray;
    String productImage;
    int currentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = ProfileImageActivity.this;

        Intent intent = getIntent();
        String cust = (String) intent.getSerializableExtra("position");
        currentImage=Integer.parseInt(cust);
        callAstrologerImageApi();
    }
    private void callAstrologerImageApi() {

        RetrofitAPIService.getApiClient().frequentAstrologerContent(AllStaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<AstrologerMultipleImageResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerMultipleImageResponseModel> call, Response<AstrologerMultipleImageResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            try {

                                if (response.isSuccessful() && response.body() != null) {
                                    if (response.body().getCode().equals("200")) {
                                        if (response.body() != null) {
                                            myStringArray = new String[response.body().getResult().size()];
                                            for (int i = 0; i < response.body().getResult().size(); i++) {
                                                myStringArray[i] = response.body().getResult().get(i).getFileUrls();
                                            }

                                            binding.prev.setVisibility(View.VISIBLE);
                                            binding.next.setVisibility(View.VISIBLE);
                                            Picasso.get().load(myStringArray[currentImage]).resize(300,300).into(binding.productDetailImage);
                                            binding.prev.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    currentImage = (myStringArray.length + currentImage - 1) % myStringArray.length;
                                                    Picasso.get().load(myStringArray[currentImage]).resize(300,300).into(binding.productDetailImage);
                                                }
                                            });

                                            binding.next.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    currentImage = (currentImage + 1) % myStringArray.length;
                                                    Picasso.get().load(myStringArray[currentImage]).resize(300,300).into(binding.productDetailImage);
                                                }
                                            });
                                        } else {
                                            Picasso.get().load(myStringArray[currentImage]).resize(300,300).into(binding.productDetailImage);
                                        }
                                    }

                                    binding.progress.setVisibility(View.GONE);

                                } else {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                AllStaticMethods.saveException(e);
                            }

                        } else {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<AstrologerMultipleImageResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

}