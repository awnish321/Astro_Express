package com.astroexpress.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivitySuggestedRemediesBinding;
import com.astroexpress.user.model.request.UpdateRemedyBookingStatusRequestModel;
import com.astroexpress.user.model.responsemodel.SuggestedRemediesResponseModel;
import com.astroexpress.user.model.responsemodel.UpdateRemedyBookingStatusResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestedRemediesActivity extends AppCompatActivity {

    ActivitySuggestedRemediesBinding binding;
    NetworkChange networkChange = new NetworkChange();
    String astrologerId, userId, remediesType;
    Context context;
    Integer remediesId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestedRemediesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = SuggestedRemediesActivity.this;

        Toolbar toolbar = binding.layoutToolbar.toolbar;
        binding.layoutToolbar.toolbar.setTitle("Suggested Remedies");
        setSupportActionBar(toolbar);

        astrologerId = getIntent().getExtras().getString("astrologerId");
        userId = getIntent().getExtras().getString("userId");

        callGetChatApi();

    }

    private void callGetChatApi() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);

        RetrofitAPIService.getApiClient().getSuggestedRemedy(userId, astrologerId).enqueue(new Callback<SuggestedRemediesResponseModel>() {
            @Override
            public void onResponse(Call<SuggestedRemediesResponseModel> call, Response<SuggestedRemediesResponseModel> response) {

                binding.lottieProgressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            setList(response.body().getResult());

                        }else if (response.body().getCode().equals("404")) {

                            binding.noData.getRoot().setVisibility(View.VISIBLE);
                            binding.noData.cardBtnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<SuggestedRemediesResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setList(List<SuggestedRemediesResponseModel.Result> result) {

        binding.llSuggestedRemedies.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            SuggestedRemediesResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_suggested_remedies, null);
            TextView txtDateTime = view.findViewById(R.id.txtDateTime);
            TextView txtAstrologerName = view.findViewById(R.id.txtAstrologerName);
            TextView txtAstrologerName1 = view.findViewById(R.id.txtAstrologerName1);
            TextView txtRemediesName = view.findViewById(R.id.txtRemediesName);
            TextView txtRemediesName1 = view.findViewById(R.id.txtRemediesName1);
            TextView txtProductType = view.findViewById(R.id.txtProductType);
            TextView txtProductType1 = view.findViewById(R.id.txtProductType1);
            TextView txtRemediesStatus1 = view.findViewById(R.id.txtRemediesStatus1);
            TextView txtRemediesStatus = view.findViewById(R.id.txtRemediesStatus);
            TextView txtMoreDetail = view.findViewById(R.id.txtMoreDetail);

            LinearLayout llRemediesDetail = view.findViewById(R.id.llRemediesDetail);
            LinearLayout llDisplayData = view.findViewById(R.id.llDisplayData);
            Button btnBookRemedies = view.findViewById(R.id.btnBookRemedies);
            ImageView imgRemedies = view.findViewById(R.id.imgRemedies);
            CardView cardImgRemedies = view.findViewById(R.id.cardImgRemedies);
            TextView txtProductPrice = view.findViewById(R.id.txtProductPrice);
            TextView txtProductCategory = view.findViewById(R.id.txtProductCategory);
            TextView txtRemediesDescription = view.findViewById(R.id.txtRemediesDescription);
            txtRemediesStatus.setText(itemData.getBookingStatus());

            if (itemData.getBookingStatusId().equals("0")) {
                btnBookRemedies.setVisibility(View.VISIBLE);
            }else {
                btnBookRemedies.setVisibility(View.INVISIBLE);
            }
            txtDateTime.setText(itemData.getCreatedOn());
            txtRemediesName.setText(itemData.getProductName());
            txtAstrologerName.setText(itemData.getFirstName()+""+itemData.getLastName());

            if (itemData.getAttachments() != null && itemData.getAttachments().size()>0)
            {
                cardImgRemedies.setVisibility(View.VISIBLE);
                Picasso.get().load(itemData.getAttachments().get(0))
                        .resize(250, 250)
                        .into(imgRemedies);
            }

//                for (int j = 0; j < Arrays.asList(itemData.getAttachments()).size(); j++) {
//                    Picasso.get().load(Arrays.asList(itemData.getAttachments()).indexOf(j))
//                            .resize(250, 250)
//                            .into(imgRemedies);
//                }

            remediesType = itemData.getPrice().trim();
            if (remediesType.equals("0")) {
                txtProductType.setText("Free Remedies");
                btnBookRemedies.setVisibility(View.GONE);

            } else {
                txtProductType.setText("Paid Remedies");
            }

            txtMoreDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(llRemediesDetail.getVisibility()==View.VISIBLE){
                        llRemediesDetail.setVisibility(View.GONE);

                        llDisplayData.setBackgroundColor(getResources().getColor(R.color.white));
                        txtDateTime.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtRemediesName.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtRemediesName1.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtProductType.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtProductType1.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtMoreDetail.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtAstrologerName.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtAstrologerName1.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtRemediesStatus1.setTextColor(getResources().getColor(R.color.dark_blue));
                        txtRemediesStatus.setTextColor(getResources().getColor(R.color.dark_blue));

                    }else {
                        llRemediesDetail.setVisibility(View.VISIBLE);
                        llDisplayData.setBackground(getResources().getDrawable(R.drawable.suggest));
                        txtDateTime.setTextColor(getResources().getColor(R.color.white));
                        txtRemediesName.setTextColor(getResources().getColor(R.color.white));
                        txtRemediesName1.setTextColor(getResources().getColor(R.color.white));
                        txtProductType.setTextColor(getResources().getColor(R.color.white));
                        txtProductType1.setTextColor(getResources().getColor(R.color.white));
                        txtMoreDetail.setTextColor(getResources().getColor(R.color.white));
                        txtAstrologerName.setTextColor(getResources().getColor(R.color.white));
                        txtAstrologerName1.setTextColor(getResources().getColor(R.color.white));
                        txtRemediesStatus1.setTextColor(getResources().getColor(R.color.white));
                        txtRemediesStatus.setTextColor(getResources().getColor(R.color.white));
                        txtProductCategory.setText(itemData.getCategoryName());
                        txtRemediesDescription.setText(itemData.getDescription());
                        txtProductPrice.setText("\u20b9 " + itemData.getPrice());
                    }
                }
            });

            btnBookRemedies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,UserAddressActivity.class)
                            .putExtra("remedyId",itemData.getRemedyId().toString())
                            .putExtra("IsOwnedRemedy",itemData.getIsOwnedRemedy())
                            .putExtra("remedyPrice",itemData.getPrice().toString()));
                    finish();

//                    UpdateRemedyBookingStatusRequestModel updateRemedyBookingStatusRequestModel=new UpdateRemedyBookingStatusRequestModel(itemData.getRemedyId());
//                    RetrofitAPIService.getApiClient().updateRemedyBookingStatus(updateRemedyBookingStatusRequestModel).enqueue(new Callback<UpdateRemedyBookingStatusResponseModel>() {
//                        @Override
//                        public void onResponse(Call<UpdateRemedyBookingStatusResponseModel> call, Response<UpdateRemedyBookingStatusResponseModel> response) {
//
//                            try {
//
//                                if (response.isSuccessful() && response.body() != null) {
//
//                                    if (response.body().getCode().equals("200")) {
//
//                                        startActivity(new Intent(context,UserAddressActivity.class));
//                                        finish();
////                                        Toast.makeText(context, "You have successfully booked your Remedies", Toast.LENGTH_SHORT).show();
////                                        btnBookRemedies.setVisibility(View.INVISIBLE);
////                                        btnBookRemedies.setBackgroundColor(getResources().getColor(R.color.white));
//                                    }
//
//                                } else {
//                                    Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//                                }
//
//                            } catch (Exception e) {
//                                AllStaticMethods.saveException(e);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<UpdateRemedyBookingStatusResponseModel> call, Throwable t) {
//                            binding.lottieProgressBar.setVisibility(View.GONE);
//                            Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });

                }
            });

            binding.llSuggestedRemedies.addView(view);
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