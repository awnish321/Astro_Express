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

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityChatHistoryBinding;
import com.astroexpress.user.model.responsemodel.ChatHistoryResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHistoryActivity extends AppCompatActivity {

    Context context;
    String action;
    ActivityChatHistoryBinding binding;
    NetworkChange networkChange = new NetworkChange();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatHistoryBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        Toolbar toolbar = binding.layoutToolbar.toolbar;
        toolbar.setTitle("Chat History");
        setSupportActionBar(toolbar);
        context = ChatHistoryActivity.this;
        loadChatHistoryFromAPI();
    }

    private void loadChatHistoryFromAPI() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);

        RetrofitAPIService.getApiClient().getChatHistory(AllStaticFields.userData.getUserId()).enqueue(new Callback<ChatHistoryResponseModel>() {
            @Override
            public void onResponse(Call<ChatHistoryResponseModel> call, Response<ChatHistoryResponseModel> response) {

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
            public void onFailure(Call<ChatHistoryResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setList(List<ChatHistoryResponseModel.Result> result) {

        binding.llList.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            ChatHistoryResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_user_chat, null);
            TextView txtAstrologerName = view.findViewById(R.id.txtAstrologerName);
            TextView txtLastMessage = view.findViewById(R.id.txtLastMessage);
            TextView txtAstrologerChatDate = view.findViewById(R.id.txtAstrologerChatDate);
            TextView txtAstrologerChatTime = view.findViewById(R.id.txtAstrologerChatTime);
            ImageView imgProfile = view.findViewById(R.id.profile_img);
            Button btnSuggestedRemedies = view.findViewById(R.id.suggestedRemedies);
            LinearLayout llChatDetail = view.findViewById(R.id.llChatDetail);

            txtAstrologerName.setText(itemData.getFirstName() + " " + itemData.getLastName());
            txtAstrologerChatDate.setText(dateFormatter(itemData.getCreatedOn()));
            txtAstrologerChatTime.setText(timeFormatter(itemData.getCreatedOn()));
            txtLastMessage.setText(itemData.getLastChatMessage());

            if (itemData.getProfileThumbnail() != null) {
                Picasso.get().load(itemData.getProfileThumbnail())
                        .resize(250, 250)
                        .into(imgProfile);

            }

            if (itemData.getProfileImageUrl() != null) {

                Picasso.get().load(itemData.getProfileImageUrl())
                        .resize(250, 250)
                        .into(imgProfile);

//                new ImageDownloader(itemData.getProfileImageUrl(), new ImageDownloader.ImageDownloadListener() {
//                    @Override
//                    public void onImageDownload(Bitmap bitmap) {
//                        imgProfile.setImageBitmap(bitmap);
//                    }
//                }).execute();
            }

            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, AstrologerProfileActivity.class)
                            .putExtra("AstrologerId", itemData.getAstrologerId()));

                }
            });

            view.setTag(itemData);

            llChatDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//
                    Intent intent = new Intent(context, ChatHistoryDetailActivity.class);
                    intent.putExtra("astrologerId", itemData.getAstrologerId());
                    intent.putExtra("userId", AllStaticFields.userData.getUserId());
                    startActivity(intent);
//                    AllStaticFields.astrologerData = (AstrologerResponseModel.Result) view.getTag();
//                    startActivity(new Intent(context, ChatHistoryDetailActivity.class));

                }
            });

            btnSuggestedRemedies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, SuggestedRemediesActivity.class)
                            .putExtra("astrologerId", itemData.getAstrologerId())
                            .putExtra("userId", AllStaticFields.userData.getUserId()));

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

    public static String timeFormatter(String unFormattedTime) {

        String time = null;
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            Date convertedDate = inputFormat.parse(unFormattedTime);
            time = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return time;
    }

}