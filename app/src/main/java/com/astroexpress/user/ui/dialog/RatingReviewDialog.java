package com.astroexpress.user.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.RatingReviewDialogBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;
import com.astroexpress.user.model.request.RatingReviewRequestModel;
import com.astroexpress.user.model.responsemodel.SaveRatingReviewResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingReviewDialog extends Dialog {

    RatingReviewDialogBinding binding;
    Float ratingCount;

    public RatingReviewDialog(@NonNull Context context, OnSubmitListeners onSubmitListeners) {super(context);
        binding = RatingReviewDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingCount = (binding.userRating.getRating());
                if(ratingCount!=0)
                {
                RatingReviewRequestModel ratingReviewRequestModel = new RatingReviewRequestModel(
                        AllStaticFields.userData.getUserId(),
                        AllStaticFields.astrologerId,
                        Float.toString(binding.userRating.getRating()),
                        binding.txtUserReview.getText().toString().trim());
                RetrofitAPIService.getApiClient().saveUserRating(ratingReviewRequestModel).enqueue(new Callback<SaveRatingReviewResponseModel>() {
                    @Override
                    public void onResponse(Call<SaveRatingReviewResponseModel> call, Response<SaveRatingReviewResponseModel> response) {

                        try {

                            if (response.isSuccessful() && response.body() != null) {

                                if (response.body().getCode().equals("200")) {
                                    Toast.makeText(context, " you have successfully submitted Rating and review ", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            AllStaticMethods.saveException(e);
                        }

                    }

                    @Override
                    public void onFailure(Call<SaveRatingReviewResponseModel> call, Throwable t) {

                    }
                });
            }
                dismiss();
                onSubmitListeners.onSubmit(null);
            }

        });

        binding.imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmitListeners.onSubmit(null);
            }
        });

    }

}
