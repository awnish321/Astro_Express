package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityCallHistoryBinding;
import com.astroexpress.user.databinding.ActivityChatHistoryBinding;
import com.astroexpress.user.model.responsemodel.CallResponseModel;
import com.astroexpress.user.model.responsemodel.ChatHistoryResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.ImageDownloader;
import com.astroexpress.user.utility.ImageUtils;
import com.astroexpress.user.utility.NetworkChange;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallHistoryActivity extends AppCompatActivity {

    Context context;
    String action;
    ActivityCallHistoryBinding binding;
    NetworkChange networkChange = new NetworkChange();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallHistoryBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        Toolbar toolbar = binding.layoutToolbar.toolbar;
        toolbar.setTitle("Call History");
        setSupportActionBar(toolbar);
        context = CallHistoryActivity.this;
        loadCallHistoryFromAPI();
    }

    private void loadCallHistoryFromAPI() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);

        RetrofitAPIService.getApiClient().getVoiceCall(AllStaticFields.userData.getUserId()).enqueue(new Callback<CallResponseModel>() {
            @Override
            public void onResponse(Call<CallResponseModel> call, Response<CallResponseModel> response) {

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
            public void onFailure(Call<CallResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setList(List<CallResponseModel.Result> result) {

        binding.llList.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            CallResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_user_call, null);
            TextView txtAstrologerName = view.findViewById(R.id.txtAstrologerName);
            TextView txtDateOfCall = view.findViewById(R.id.txtDateOfCall);
            TextView txtDuration = view.findViewById(R.id.txtDuration);
            ImageView imgCallStatus = view.findViewById(R.id.imgCallStatus);

            txtAstrologerName.setText(itemData.getFirstName() + " " + itemData.getLastName());
            txtDateOfCall.setText(dateFormatter(itemData.getCreatedOn()));
            if (itemData.getIVRStatus().equals("success") && !itemData.getDuration().equals("0")) {
                imgCallStatus.setImageResource(R.drawable.ic_connect);
                txtDuration.setText(timeFormatter(itemData.getDuration()));
                txtDuration.setTextColor(getResources().getColor(R.color.dark_blue));
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, AstrologerProfileActivity.class)
                            .putExtra("AstrologerId", itemData.getAstrologerId()));
                }
            });
            
            view.setTag(itemData);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, CallHistoryDetailActivity.class);
//                    intent.putExtra("astrologerId", itemData.getAstrologerId());
//                    intent.putExtra("userId", AllStaticFields.userData.getUserId());
//                    startActivity(intent);
////                    AllStaticFields.astrologerData = (AstrologerResponseModel.Result) view.getTag();
////                    startActivity(new Intent(context, ChatHistoryDetailActivity.class));
//
//                }
//            });

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

    public static String timeFormatter(String unconvertedSecond) {
        long elapsedHours = 0,elapsedMinutes = 0,elapsedSeconds = 0,startTime = 0;
                startTime = Long.parseLong(unconvertedSecond)*1000;


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        elapsedHours = startTime / hoursInMilli;
        startTime = startTime % hoursInMilli;

        elapsedMinutes = startTime / minutesInMilli;
        startTime = startTime % minutesInMilli;

        elapsedSeconds = startTime/secondsInMilli;

        if (elapsedHours==0){
            return (elapsedMinutes + " Min " + elapsedSeconds+ " Sec" );
        }else {

            return (elapsedHours + " Hour " + elapsedMinutes + " Min " + elapsedSeconds + " Sec");
        }
    }
}