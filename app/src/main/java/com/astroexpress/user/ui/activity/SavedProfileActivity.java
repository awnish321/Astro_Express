package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.astroexpress.user.R;
import com.astroexpress.user.databinding.ActivityMyProfileBinding;
import com.astroexpress.user.databinding.ActivitySavedProfileBinding;
import com.astroexpress.user.utility.NetworkChange;

public class SavedProfileActivity extends AppCompatActivity {

    ActivitySavedProfileBinding binding;
    NetworkChange networkChange =new NetworkChange();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivitySavedProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.layoutToolbar.toolbar);
        binding.layoutToolbar.toolbar.setTitle("Saved User");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
//            case R.id.share:
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Astro Express");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download this Application now:- https://play.google.com/store/apps/details?");
//                startActivity(Intent.createChooser(shareIntent, "share via"));
//                break;

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
        registerReceiver(networkChange, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }
}