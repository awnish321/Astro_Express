package com.astroexpress.user.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.AstrologerNotRespondingChatDialogBinding;
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

public class AstrologerNotRespondingChatDialog extends Dialog {

    AstrologerNotRespondingChatDialogBinding binding;

    public AstrologerNotRespondingChatDialog(@NonNull Context context, OnSubmitListeners onSubmitListeners) {
        super(context);
        binding = AstrologerNotRespondingChatDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitListeners.onSubmit(null);
                dismiss();
            }
        });
    }

}
