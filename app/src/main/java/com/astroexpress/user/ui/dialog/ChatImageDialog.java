package com.astroexpress.user.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import com.astroexpress.user.model.request.ChatRequestModel;
import androidx.annotation.NonNull;

import com.astroexpress.user.databinding.ChatImageDialogBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;
import com.squareup.picasso.Picasso;

public class ChatImageDialog extends Dialog {

    ChatImageDialogBinding binding;

    public ChatImageDialog(@NonNull Context context,ChatRequestModel result, OnSubmitListeners onSubmitListeners) {
        super(context);
        binding = ChatImageDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Picasso.get().load(result.getFileUrl()).noFade().into(binding.chatImg);


    }

}
