package com.astroexpress.user.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.astroexpress.user.databinding.MyProgressDialogBinding;

public class MyProgressDialog extends Dialog {

    MyProgressDialogBinding binding;

    public MyProgressDialog(@NonNull Context context)
    {
        super(context);
        binding = MyProgressDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

    public void setTitle(String title){

        if (title!=null && !title.trim().equals("")) {
            binding.txtTitle.setText(title);
        }

    }

}
