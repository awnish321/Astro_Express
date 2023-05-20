package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astroexpress.user.R;
import com.astroexpress.user.utility.NetworkChange;

public class ComingSoonActivity extends AppCompatActivity {

    Toolbar toolbar;
    NetworkChange networkChange = new NetworkChange();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Coming Soon");
        setSupportActionBar(toolbar);
    }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
//                case R.id.share:
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Astro Express");
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Download this Application now:- https://play.google.com/store/apps/details?");
//                    startActivity(Intent.createChooser(shareIntent, "share via"));
//                    break;

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