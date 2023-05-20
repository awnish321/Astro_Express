package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityUserFollowListBinding;
import com.astroexpress.user.model.FollowingModel;
import com.astroexpress.user.model.request.FollowUnfollowRequestModel;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.FollowUnfollowResponseModel;
import com.astroexpress.user.model.responsemodel.FollowedAstrologerResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFollowListActivity extends AppCompatActivity {

    ActivityUserFollowListBinding binding;
    Context context;
    String userId, astrologerId;
    Boolean followingStatus =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserFollowListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = UserFollowListActivity.this;
        setSupportActionBar(binding.layoutToolbar.toolbar);
        binding.layoutToolbar.toolbar.setTitle("Following");

        callFollowedAstrologerAPI();
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

    private void callFollowedAstrologerAPI() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);
        RetrofitAPIService.getApiClient().getFollowedAstrologer(AllStaticFields.userData.getUserId()).enqueue(new Callback<FollowedAstrologerResponseModel>() {
            @Override
            public void onResponse(Call<FollowedAstrologerResponseModel> call, Response<FollowedAstrologerResponseModel> response) {

                binding.lottieProgressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            setList(response.body().getResult());

                        } else if (response.body().getCode().equals("404")) {

                            binding.noData.getRoot().setVisibility(View.VISIBLE);
                            binding.noData.cardBtnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

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
            public void onFailure(Call<FollowedAstrologerResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setList(List<FollowedAstrologerResponseModel.Result> result) {

        binding.llList.removeAllViews();

        for (int i = 0; i < result.size(); i++) {

            FollowedAstrologerResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_following, null);
            TextView txtAstrologerName = view.findViewById(R.id.txtAstrologerName);
            ImageView imgAstroPic = view.findViewById(R.id.imgAstroPic);
            LinearLayout llViewArea = view.findViewById(R.id.llViewArea);
            Button btnUnfollow =view.findViewById(R.id.btnUnfollow);

            txtAstrologerName.setText(itemData.getFirstName());

            if (itemData.getProfileImageUrl() != null) {

                Picasso.get().load(itemData.getProfileImageUrl())
                        .resize(250, 250)
                        .into(imgAstroPic);
            }

            view.setTag(itemData);
            llViewArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, AstrologerProfileActivity.class)
                            .putExtra("AstrologerId", itemData.getAstrologerId())
                            .putExtra("UserId", AllStaticFields.userData.getUserId())
                    );
                }
            });

            btnUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (result.size()==1){
                        binding.llList.removeAllViews();
                    }
                    userId=AllStaticFields.userData.getUserId();
                    astrologerId=itemData.getAstrologerId();
                    callFollowUnfollowAstrologerApi();
                }
            });

            binding.llList.addView(view);

        }

    }

    private void callFollowUnfollowAstrologerApi() {

        FollowUnfollowRequestModel followUnfollowRequestModel = new FollowUnfollowRequestModel(userId, astrologerId, followingStatus);
        RetrofitAPIService.getApiClient().followUnfollow(followUnfollowRequestModel).enqueue(new Callback<FollowUnfollowResponseModel>() {
            @Override
            public void onResponse(Call<FollowUnfollowResponseModel> call, Response<FollowUnfollowResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            if (response.body().getResult().getIsFollow() == Boolean.FALSE) {
                                callFollowedAstrologerAPI();

                            } else {
                                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<FollowUnfollowResponseModel> call, Throwable t) {

            }
        });

    }

}