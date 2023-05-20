package com.astroexpress.user.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.astroexpress.user.databinding.AstrologerNotRespondingCallDialogBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;


public class AstrologerNotRespondingCallDialog extends Dialog {

    AstrologerNotRespondingCallDialogBinding binding;

    public AstrologerNotRespondingCallDialog(@NonNull Context context, OnSubmitListeners onSubmitListeners) {super(context);
        binding = AstrologerNotRespondingCallDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSubmitListeners.onSubmit(null);
//            }
//        });

        binding.imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmitListeners.onSubmit(null);
            }
        });

    }

}
