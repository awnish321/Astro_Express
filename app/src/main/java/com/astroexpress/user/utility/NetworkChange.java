package com.astroexpress.user.utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.astroexpress.user.R;

public class NetworkChange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!CheckNetConnection.isConnectedToInternet(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.cheak_internet_connection,null);
            builder.setView(layout_dialog);

            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.btnRetry);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setGravity(Gravity.CENTER);

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }
}

