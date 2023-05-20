package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ActivityHelpdeskBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;
import com.astroexpress.user.ui.dialog.AstrologerNotRespondingCallDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.NetworkChange;

public class HelpdeskActivity extends AppCompatActivity
{
    ActivityHelpdeskBinding binding;
    Toolbar toolbar;
    NetworkChange networkChange = new NetworkChange();
    AstrologerNotRespondingCallDialog astrologerNotRespondingCallDialog;
    Context context;
    String startTime,endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpdeskBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        context =HelpdeskActivity.this;

        setContentView(rootView);
        toolbar = findViewById(R.id.layoutToolbar);
        toolbar.setTitle("Help Desk");
        setSupportActionBar(toolbar);

        binding.cardRaiseIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AllStaticFields.userData !=null) {
                    startActivity(new Intent(context, RaiseTicketActivity.class));
                }else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }
            }
        });

        binding.cardContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ContactUsActivity.class));

            }
        });

        binding.cardRaisedToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AllStaticFields.userData !=null) {
                    startActivity(new Intent(context, RaisedTokenActivity.class));
                }else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }            }
        });

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
        registerReceiver(networkChange,intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

}