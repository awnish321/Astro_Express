package com.astroexpress.user.ui.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ImageUploadDialogBinding;
import com.astroexpress.user.databinding.RatingReviewDialogBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;
import com.astroexpress.user.model.request.RatingReviewRequestModel;
import com.astroexpress.user.model.responsemodel.ChatEndResponseModel;
import com.astroexpress.user.model.responsemodel.SaveChatImageResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.ImageUtils;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadDialog extends Dialog {

    ImageUploadDialogBinding binding;

    public ImageUploadDialog(@NonNull Context context, String result, OnSubmitListeners onSubmitListeners) {
        super(context);
        binding = ImageUploadDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        Uri Selected_Image_Uri =Uri.parse(result);
        String picturePathIMG = null;

        binding.image.setImageURI(Selected_Image_Uri);

        binding.imgBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Selected_Image_Uri != null) {
                    File file = new File(picturePathIMG);

                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("UserId", AllStaticFields.userData.getUserId())
                            .addFormDataPart("AstrologerId", "2");

                    RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                    builder.addFormDataPart("Attachments[]", file.getName(), requestFile);
                    RequestBody requestBody = builder.build();

                    RetrofitAPIService.getApiClient().saveChatImage(requestBody).enqueue(new Callback<SaveChatImageResponseModel>() {
                        @Override
                        public void onResponse(Call<SaveChatImageResponseModel> call, Response<SaveChatImageResponseModel> response) {

                            try {

                                if (response.isSuccessful() && response.body() != null) {

                                    if (response.body().getCode().equals("200")) {
                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
                        public void onFailure(Call<SaveChatImageResponseModel> call, Throwable t) {
//                                        binding.lottieProgressBar.setVisibility(View.GONE);
                            Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(context, "please select an image", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void setTitle(String title) {

        if (title != null && !title.trim().equals("")) {
//            binding.txtTitle.setText(title);
        }

    }

}
