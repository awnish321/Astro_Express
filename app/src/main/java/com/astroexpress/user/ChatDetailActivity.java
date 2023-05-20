package com.astroexpress.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityChatBinding;
import com.astroexpress.user.databinding.ActivityChatDetailBinding;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.ChatListResponseModel;
import com.astroexpress.user.ui.activity.ChatActivity;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.ImageDownloader;
import com.astroexpress.user.utility.ImageUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity {
    Context context;
    ActivityChatDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        context = ChatDetailActivity.this;

        callGetChatApi();
    }


    private void callGetChatApi() {
        RetrofitAPIService.getApiClient().getChatList(AllStaticFields.userData.getUserId(), AllStaticFields.astrologerData.getAstrologerId()).enqueue(new Callback<ChatListResponseModel>() {
            @Override
            public void onResponse(Call<ChatListResponseModel> call, Response<ChatListResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {


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
            public void onFailure(Call<ChatListResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setList(List<AstrologerResponseModel.Result> result) {

        binding.llList.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            AstrologerResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_astrologer_for_selection, null);
            TextView txtName = view.findViewById(R.id.txtName);
            TextView txtSpacelist = view.findViewById(R.id.txtSpacelist);
            TextView txtLanguage = view.findViewById(R.id.txtLanguage);
            TextView txtExperience = view.findViewById(R.id.txtExperience);
            TextView txtCharge = view.findViewById(R.id.txtCharge);
            ImageView imgProfile = view.findViewById(R.id.imgProfile);
            ImageView imgBtnCall = view.findViewById(R.id.imgBtnCall);
            ImageView imgBtnChat = view.findViewById(R.id.imgBtnChat);


            txtName.setText(itemData.getFirstName() + " " + itemData.getLastName());
            txtSpacelist.setText(itemData.getSpeciality());
            txtLanguage.setText(itemData.getLanguage());
            txtExperience.setText(itemData.getExperience() + " Yrs");
            txtCharge.setText(itemData.getChargePerMinute() + " per minute");

            imgBtnCall.setVisibility(View.GONE);
            imgBtnChat.setVisibility(View.GONE);

            if (itemData.getProfileThumbnail() != null) {
                imgProfile.setImageBitmap(ImageUtils.convert(itemData.getProfileThumbnail()));
            }

            if (itemData.getProfileImageUrl() != null) {
                new ImageDownloader(itemData.getProfileImageUrl(), new ImageDownloader.ImageDownloadListener() {
                    @Override
                    public void onImageDownload(Bitmap bitmap) {
                        imgProfile.setImageBitmap(bitmap);
                    }
                }).execute();
            }

            view.setTag(itemData);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AllStaticFields.astrologerData = (AstrologerResponseModel.Result) view.getTag();
                    startActivity(new Intent(context, ChatActivity.class)
                            .putExtra("AstrologerId", AllStaticFields.astrologerData.getAstrologerId())
                            .putExtra("UserId", AllStaticFields.userData.getUserId())
                    );

                }
            });

            binding.llList.addView(view);

        }
    }


}