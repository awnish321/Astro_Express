package com.astroexpress.user.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.astroexpress.user.databinding.CheakWalletBalanceDialogBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;

public class WalletBalanceCheckDialog extends Dialog {

    CheakWalletBalanceDialogBinding binding;

    public WalletBalanceCheckDialog(@NonNull Context context, OnSubmitListeners onSubmitListeners) {
        super(context);
        binding = CheakWalletBalanceDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.cardRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitListeners.onSubmit(null);
                dismiss();
            }
        });

        binding.imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitListeners.onCancel(null);
                dismiss();
            }
        });

    }

    public void setTitle(String title) {

        if (title != null && !title.trim().equals("")) {
            binding.txtTitle.setText(title);
        }

    }

    public void setBody(String body) {

        if (body != null && !body.trim().equals("")) {
            binding.txtBody.setText(body);
        }

    }

}
