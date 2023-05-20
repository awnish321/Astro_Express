package com.astroexpress.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.astroexpress.user.adapter.LoadBlogListDashboardAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityMainBinding;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;

import com.astroexpress.user.model.responsemodel.BlogResponseModel;
import com.astroexpress.user.model.responsemodel.TestimonialResponseModels;
import com.astroexpress.user.model.responsemodel.UserTestimonialResponseModel;
import com.astroexpress.user.ui.activity.LoginSignupActivity;
import com.astroexpress.user.ui.activity.NewDashboardActivity;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.SharedPreferenceManager;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3500;
    Context context;
    ActivityMainBinding binding;
    List<AstrologerResponseModel.Result> astrologerAllList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);

        context = MainActivity.this;
        AllStaticFields.userData = SharedPreferenceManager.getUserData(context);
        AllStaticFields.astrologerDataBoostedAstrologerList.clear();
        AllStaticFields.astrologerDataOnlineCallList.clear();
        AllStaticFields.astrologerDataOnlineChatList.clear();

        callAstrologerAllApi();
        callBlogApi();
        callUserTestimonialApi();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferenceManager.getUserData(context) == null) {
                    Intent intent = new Intent(context, LoginSignupActivity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(context, NewDashboardActivity.class));
                }
                finish();
            }
        }, SPLASH_SCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void callAstrologerAllApi() {

        RetrofitAPIService.getApiClient().getAstrologer("All").enqueue(new Callback<AstrologerResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            updateTrendingAstrologerList(response.body().getResult());
//                            updateCallAstrologerList(response.body().getResult());
//                            updateChatAstrologerList(response.body().getResult());

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
            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAstrologerList() {
        int x = 0, y = 0, z = 0;
        for (int i = 0; i < astrologerAllList.size(); i++) {
            if (astrologerAllList.get(i).getBoosted()) {
                if (x < 5) {
                    astrologerAllList.get(i).setValidForFree(true);
                    AllStaticFields.astrologerDataBoostedAstrologerList.add(astrologerAllList.get(i));
                    x++;
                } else {
                    astrologerAllList.get(i).setValidForFree(false);
                    AllStaticFields.astrologerDataBoostedAstrologerList.add(astrologerAllList.get(i));
                }
            }
        }
        for (int j = 0; j < astrologerAllList.size(); j++) {
            if (astrologerAllList.get(j).isOnlineForCall()) {
                if (y < 5) {
                    astrologerAllList.get(j).setValidForFree(true);
                    AllStaticFields.astrologerDataOnlineCallList.add(astrologerAllList.get(j));
                    y++;
                } else {
                    astrologerAllList.get(j).setValidForFree(false);
                    AllStaticFields.astrologerDataOnlineCallList.add(astrologerAllList.get(j));
                }
            }
        }
        for (int k = 0; k < astrologerAllList.size(); k++) {
            if (astrologerAllList.get(k).getOnlineForChat()) {
                if (z < 5) {
                    astrologerAllList.get(k).setValidForFree(true);
                    AllStaticFields.astrologerDataOnlineChatList.add(astrologerAllList.get(k));
                    z++;
                } else {
                    astrologerAllList.get(k).setValidForFree(false);
                    AllStaticFields.astrologerDataOnlineChatList.add(astrologerAllList.get(k));
                }
            }
        }
    }

    private void updateTrendingAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList) {
        int x = 0;
        for (int i = 0; i < astrologerDataAllList.size(); i++) {
            if (astrologerDataAllList.get(i).getBoosted()) {
                AllStaticFields.astrologerDataBoostedAstrologerList.add(astrologerDataAllList.get(i));
            }
        }
    }

    private void updateCallAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList) {
        int x = 0;
        for (int j = 0; j < astrologerDataAllList.size(); j++) {
            if (astrologerDataAllList.get(j).isOnlineForCall()) {
                AllStaticFields.astrologerDataOnlineCallList.add(astrologerDataAllList.get(j));
            }
        }

    }

    private void updateChatAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList) {
        int x = 0;
        for (int k = 0; k < astrologerDataAllList.size(); k++) {
            if (astrologerDataAllList.get(k).getOnlineForChat()) {
                AllStaticFields.astrologerDataOnlineChatList.add(astrologerDataAllList.get(k));

            }
        }
    }

    private void callBlogApi() {

        RetrofitAPIService.getApiClient().getAllBlogs().enqueue(new Callback<BlogResponseModel>() {
            @Override
            public void onResponse(Call<BlogResponseModel> call, Response<BlogResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getCode().equals("200")) {
                            AllStaticFields.blogAllData.clear();
                            AllStaticFields.blogAllData.addAll(response.body().getResult());

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
            public void onFailure(Call<BlogResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void callUserTestimonialApi() {
        RetrofitAPIService.getApiClient().getAllUserTestimonial().enqueue(new Callback<UserTestimonialResponseModel>() {
            @Override
            public void onResponse(Call<UserTestimonialResponseModel> call, Response<UserTestimonialResponseModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            AllStaticFields.testimonialAllData.clear();
                            AllStaticFields.testimonialAllData.addAll(response.body().getResult());

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
            public void onFailure(Call<UserTestimonialResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }


}