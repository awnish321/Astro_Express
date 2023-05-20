package com.astroexpress.user.ui.activity;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CALL;
import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CHAT;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.astroexpress.user.R;
import com.astroexpress.user.adapter.MultipleProfileImageAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityAstrologerProfileBinding;
import com.astroexpress.user.model.request.FollowUnfollowRequestModel;
import com.astroexpress.user.model.responsemodel.AstrologerDetailResponseModel;
import com.astroexpress.user.model.responsemodel.AstrologerMultipleImageResponseModel;
import com.astroexpress.user.model.responsemodel.FollowUnfollowResponseModel;
import com.astroexpress.user.model.responsemodel.FollowedAstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.GetRatingReviewResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstrologerProfileActivity extends AppCompatActivity {
    ActivityAstrologerProfileBinding binding;
    Context context;
    NetworkChange networkChange = new NetworkChange();
    String userId = null, astrologerId = null;
    Boolean followingStatus;
    DatabaseReference dbCallingRefBusyStatus;
    String size="0";
    String RemainingFreeSession=null;
    Boolean ValidForFree;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityAstrologerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = AstrologerProfileActivity.this;
        setSupportActionBar(binding.layoutToolbar.toolbar);

        astrologerId = getIntent().getStringExtra("AstrologerId");
        ValidForFree = getIntent().getBooleanExtra("ValidForFree",false);
        binding.layoutToolbar.toolbar.setTitle("Astrologer Profile");
        dbCallingRefBusyStatus = FirebaseDatabase.getInstance().getReference("Chats").child("Astrologer");

        callWalletBalanceApi();

        callGetRatingReviewByAstrologerId();

        callAstrologerDetailApi();

        callAstrologerImageApi();

        if (AllStaticFields.userData != null) {
            userId = AllStaticFields.userData.getUserId();
            callFollowedAstrologerAPI();
        } else {
            binding.btnFollowStatus.setVisibility(View.GONE);
        }

        binding.btnFollowStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.btnFollowStatus.getText().toString().trim().equals("Follow")) {
                    followingStatus = true;
                } else {
                    followingStatus = false;
                }
                callFollowAstrologerApi();
            }
        });

    }

    private void callGetRatingReviewByAstrologerId() {
        RetrofitAPIService.getApiClient().getRatingReviewByAstrologerId(astrologerId).enqueue(new Callback<GetRatingReviewResponseModel>() {
            @Override
            public void onResponse(Call<GetRatingReviewResponseModel> call, Response<GetRatingReviewResponseModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            setAllRatingReviewList(response.body().getResult());
                            size= String.valueOf(response.body().getResult().size());
                            binding.totalUsers.setText(size);

                        } else {
                            binding.cardReview.setVisibility(View.GONE);
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
            public void onFailure(Call<GetRatingReviewResponseModel> call, Throwable t) {

            }
        });
    }

    private void setAllRatingReviewList(List<GetRatingReviewResponseModel.Result> result) {

        binding.llList.removeAllViews();

        for (int i = 0; i < result.size(); i++) {

            GetRatingReviewResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_rating_review, null);
            TextView txtUserName = view.findViewById(R.id.txtUserName);
            TextView txtUserReview = view.findViewById(R.id.txtUserReview);
//            TextView txtRating = view.findViewById(R.id.txtRating);
            TextView txtUserReviewDate = view.findViewById(R.id.txtUserReviewDate);
            RatingBar userRatingBar = view.findViewById(R.id.userRatingBar);

            txtUserReviewDate.setText(dateFormatter(itemData.getCreatedOn()));
            userRatingBar.setRating((Float.parseFloat(itemData.getRatingCount())));
            txtUserName.setText(itemData.getUserName());
            txtUserReview.setText(itemData.getReview()==null || itemData.getReview().equals("")? " No Review " : itemData.getReview());
//            txtRating.setText("("+itemData.getRatingCount()+ "/5 )");
            view.setTag(itemData);

            binding.llList.addView(view);

        }

    }

    private void callFollowAstrologerApi() {

        FollowUnfollowRequestModel followUnfollowRequestModel = new FollowUnfollowRequestModel(userId, astrologerId, followingStatus);
        RetrofitAPIService.getApiClient().followUnfollow(followUnfollowRequestModel).enqueue(new Callback<FollowUnfollowResponseModel>() {
            @Override
            public void onResponse(Call<FollowUnfollowResponseModel> call, Response<FollowUnfollowResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            if (response.body().getResult().getIsFollow() == Boolean.TRUE) {
                                binding.btnFollowStatus.setText("un  Follow");

                            } else {
                                binding.btnFollowStatus.setText("Follow");
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

    private void callAstrologerDetailApi() {

        RetrofitAPIService.getApiClient().getAstrologerDetail(astrologerId).enqueue(new Callback<AstrologerDetailResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerDetailResponseModel> call, Response<AstrologerDetailResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getCode().equals("200"))
                        {
                            AllStaticFields.astrologerDetailData = response.body().getResult();
                            binding.llMyProfileData.setVisibility(View.VISIBLE);
                            binding.lottieProgressBar.setVisibility(View.GONE);
                            RemainingFreeSession = response.body().getResult().getRemaningFreeSession().toString();
                            LoadAstrologerData();
                        }
                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<AstrologerDetailResponseModel> call, Throwable t) {

            }
        });

    }

    private void LoadAstrologerData() {

        if (AllStaticFields.astrologerDetailData.getIsOnlineForCall())
        {
            if (AllStaticFields.astrologerDetailData.getIsBusyOnCall()){
                binding.btnCall.setEnabled(false);
                binding.btnCall.setBackground(getResources().getDrawable(R.drawable.buttonendground));
            }else {
                binding.btnCall.setBackground(ContextCompat.getDrawable(context, R.drawable.rzp_green_button));
                binding.btnCall.setEnabled(true);
            }
        } else {
            binding.btnCall.setEnabled(false);
        }
        if (AllStaticFields.astrologerDetailData.getIsOnlineForChat())  {
            if (AllStaticFields.astrologerDetailData.getIsBusyOnCall()){
                binding.btnChat.setEnabled(false);
                binding.btnChat.setBackground(getResources().getDrawable(R.drawable.buttonendground));
            }else {
                binding.btnChat.setBackground(ContextCompat.getDrawable(context, R.drawable.rzp_green_button));
                binding.btnChat.setEnabled(true);
            }
        } else {
            binding.btnChat.setEnabled(false);
        }

        if (AllStaticFields.userData != null)
        {
            if (Long.valueOf(RemainingFreeSession) > 0 && !AllStaticFields.walletData.getMinuteAmount().equals("0") && ValidForFree)
            {
                binding.imgFreeCallChat.setVisibility(View.VISIBLE);
                binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                binding.txtDiscountCharge.setVisibility(View.GONE);
                if (AllStaticFields.astrologerDetailData.getOfferApplied())
                {
                    if (AllStaticFields.astrologerDetailData.getDiscountType().equals("Percentage")) {
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double DiscountPrice = (amount / 100.0f) * Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - DiscountPrice);
                        binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));

                    } else {
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double discountedAmount = Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - discountedAmount);
                        binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));
                    }

                } else {
                    binding.txtCharge.setText("\u20B9 " + AllStaticFields.astrologerDetailData.getChargePerMinute() + "/Min");
                }
            } else
            {
                if (AllStaticFields.astrologerDetailData.getOfferApplied()) {
                    binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                    if (AllStaticFields.astrologerDetailData.getDiscountType().equals("Percentage")) {
                        binding.txtCharge.setText("\u20b9 " + AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double DiscountPrice = (amount / 100.0f) * Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - DiscountPrice);
                        binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                    } else {
                        binding.txtCharge.setText("\u20B9 " + AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double discountedAmount = Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - discountedAmount);
                        binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                    }

                } else
                {
                    binding.frameLayoutPrice.setVisibility(View.GONE);
                    binding.txtDiscountCharge.setText("\u20B9 " + AllStaticFields.astrologerDetailData.getChargePerMinute() + " Per Minute");
                }
            }
        } else {
            if (Long.valueOf(RemainingFreeSession)  > 0 && ValidForFree)
            {
                binding.imgFreeCallChat.setVisibility(View.VISIBLE);
                binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                binding.txtDiscountCharge.setVisibility(View.GONE);
                if (AllStaticFields.astrologerDetailData.getOfferApplied()) {
                    if (AllStaticFields.astrologerDetailData.getDiscountType().equals("Percentage")) {
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double DiscountPrice = (amount / 100.0f) * Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - DiscountPrice);
                        binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));

                    } else {
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double discountedAmount = Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - discountedAmount);
                        binding.txtCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + "/Min"));
                    }
                } else {
                    binding.txtCharge.setText("\u20B9 " + AllStaticFields.astrologerDetailData.getChargePerMinute() + "/Min");
                }
            }else
            {
                if (AllStaticFields.astrologerDetailData.getOfferApplied()) {
                    binding.frameLayoutPrice.setVisibility(View.VISIBLE);
                    if (AllStaticFields.astrologerDetailData.getDiscountType().equals("Percentage")) {
                        binding.txtCharge.setText("\u20b9 " + AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double DiscountPrice = (amount / 100.0f) * Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - DiscountPrice);
                        binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                    } else {
                        binding.txtCharge.setText("\u20B9 " + AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double amount = Double.parseDouble(AllStaticFields.astrologerDetailData.getChargePerMinute());
                        double discountedAmount = Double.parseDouble(AllStaticFields.astrologerDetailData.getDiscountAmount());
                        double DiscountFinalPrice = (amount - discountedAmount);
                        binding.txtDiscountCharge.setText("\u20B9 " + String.valueOf(DiscountFinalPrice + " Per Minute"));

                    }

                } else
                {
                    binding.frameLayoutPrice.setVisibility(View.GONE);
                    binding.txtDiscountCharge.setText("\u20B9 " + AllStaticFields.astrologerDetailData.getChargePerMinute() + " Per Minute");
                }
            }
        }

        binding.ratingBar.setRating(Float.parseFloat(AllStaticFields.astrologerDetailData.getRating()));
        binding.txtRating.setText(AllStaticFields.astrologerDetailData.getRating());
        binding.txtUserFirstName.setText(AllStaticFields.astrologerDetailData.getFirstName().trim());

        if (AllStaticFields.astrologerDetailData.getLastName().equals(" ")){
            binding.txtUserLastName.setVisibility(View.GONE);
        }else {
        binding.txtUserLastName.setText(AllStaticFields.astrologerDetailData.getLastName().trim());
        }
        binding.txtAstroLanguage.setText(AllStaticFields.astrologerDetailData.getLanguage().trim());
        binding.txtAstroSpeciality.setText(AllStaticFields.astrologerDetailData.getSpeciality().trim());
        binding.txtAstroAboutUs.setText(AllStaticFields.astrologerDetailData.getAboutUs().trim());
//        binding.txtConsultantCount.setText(AllStaticFields.astrologerDetailData.getConsultantCount().trim());
        binding.txtAstroTotalCallMinute.setText(AllStaticFields.astrologerDetailData.getTotalCallMinuts().trim());
        binding.txtAstroTotalChatMinute.setText(AllStaticFields.astrologerDetailData.getTotalChatMinuts().trim());

        if (AllStaticFields.astrologerDetailData.getProfileImageUrl() != null) {

            Picasso.get().load(AllStaticFields.astrologerDetailData.getProfileImageUrl())
                    .resize(300, 300)
                    .into(binding.imgAstroPic);
        }

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AllStaticFields.userData != null) {
                    if (AllStaticFields.sessionId == null) {
                        startActivity(new Intent(context, UpdateRegistrationFormActivity.class).setAction(ASTROLOGERS_FOR_CALL)
                                .putExtra("AstrologerId", AllStaticFields.astrologerDetailData.getAstrologerId())
                                .putExtra("AstrologerChargePerMinute", AllStaticFields.astrologerDetailData.getChargePerMinute())
                                .putExtra("UserId", AllStaticFields.userData.getUserId())
                                .putExtra("RemainingFreeSession", RemainingFreeSession)
                                .putExtra("ValidForFree", ValidForFree)
                        );
                    } else {
                        Toast.makeText(context, "you are in call/chat disconnect to continue", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent1 = new Intent(context, LoginSignupActivity.class);
                    startActivity(intent1);
                }
            }
        });

        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AllStaticFields.userData != null) {
                    if (AllStaticFields.sessionId == null) {

                        startActivity(new Intent(context, UpdateRegistrationFormActivity.class).setAction(ASTROLOGERS_FOR_CHAT)
                                .putExtra("AstrologerId", AllStaticFields.astrologerDetailData.getAstrologerId())
                                .putExtra("UserId", AllStaticFields.userData.getUserId())
                                .putExtra("RemainingFreeSession", RemainingFreeSession)
                                .putExtra("ValidForFree", ValidForFree)
                        );
                    } else {
                        Toast.makeText(context, "you are in chat disconnect to continue", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent1 = new Intent(context, LoginSignupActivity.class);
                    startActivity(intent1);
                }
            }
        });

    }

    private void callAstrologerImageApi() {

        RetrofitAPIService.getApiClient().frequentAstrologerContent(astrologerId).enqueue(new Callback<AstrologerMultipleImageResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerMultipleImageResponseModel> call, Response<AstrologerMultipleImageResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            MultipleProfileImageAdapter multipleProfileImageAdapter = new MultipleProfileImageAdapter(context, response.body().getResult());
                            binding.viewPagerMultipleProfileImage.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                            binding.viewPagerMultipleProfileImage.setAdapter(multipleProfileImageAdapter);

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

    private void callFollowedAstrologerAPI() {

        if (AllStaticFields.userData != null) {

            RetrofitAPIService.getApiClient().getFollowedAstrologer(AllStaticFields.userData.getUserId()).enqueue(new Callback<FollowedAstrologerResponseModel>() {
                @Override
                public void onResponse(Call<FollowedAstrologerResponseModel> call, Response<FollowedAstrologerResponseModel> response) {

                    try {

                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getCode().equals("200")) {

                                follList(response.body().getResult());

                            } else {
//                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void follList(List<FollowedAstrologerResponseModel.Result> result) {

        for (int i = 0; i < result.size(); i++) {

            FollowedAstrologerResponseModel.Result itemData = result.get(i);

            if (astrologerId.equals(itemData.getAstrologerId())) {
                binding.btnFollowStatus.setText("un  Follow");
                break;
            }
            else {
                binding.btnFollowStatus.setText("Follow");
            }
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

    public static String dateFormatter(String unFormattedDate) {
        String datetime = null;
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date convertedDate = inputFormat.parse(unFormattedDate);
            datetime = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return datetime;

    }

    private void callWalletBalanceApi() {

        if (AllStaticFields.userData != null) {
            RetrofitAPIService.getApiClient().getWallet(AllStaticFields.userData.getUserId()).enqueue(new Callback<WalletResponseModel>() {
                @Override
                public void onResponse(Call<WalletResponseModel> call, Response<WalletResponseModel> response) {

                    try {

                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getCode().equals("200")) {

                                AllStaticFields.walletData = response.body().getResult();

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
    }

}