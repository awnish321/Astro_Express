package com.astroexpress.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.astroexpress.user.databinding.ActivityMyProfileBinding;
import com.astroexpress.user.ui.dialog.MyProgressDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.NetworkChange;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyProfileActivity extends AppCompatActivity {

    ActivityMyProfileBinding binding;
    Context context;
    NetworkChange networkChange = new NetworkChange();
    MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = MyProfileActivity.this;

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.layoutToolbar.toolbar);
        binding.layoutToolbar.toolbar.setTitle("My Profile");

        setUserData();

        binding.userProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMyProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        binding.userProfileFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, UserFollowListActivity.class));
            }
        });
    }

    private void setUserData() {

        if (AllStaticFields.userData != null) {
            binding.txtUserFirstName.setText(AllStaticFields.userData.getFirstName()==null || AllStaticFields.userData.getFirstName().equals("")? "Guest" : AllStaticFields.userData.getFirstName());
            binding.txtUserDob.setText(AllStaticFields.userData.getDateOfBirth()==null ||AllStaticFields.userData.getDateOfBirth().equals("0000-00-00") ? "--/--/----" : dateFormatter(AllStaticFields.userData.getDateOfBirth()));
            binding.txtUserCurrentCity.setText(AllStaticFields.userData.getCurrentCityName()==null||AllStaticFields.userData.getCurrentCityName().equals("") ? "" : AllStaticFields.userData.getCurrentCityName());
            binding.txtUserProblemArea.setText(AllStaticFields.userData.getProblemArea()==null||AllStaticFields.userData.getProblemArea().equals("") ? "" : AllStaticFields.userData.getProblemArea());
            binding.txtUserTimeOfBirth.setText(AllStaticFields.userData.getTimeOfBirth()==null||AllStaticFields.userData.getTimeOfBirth().equals("") ? "" : timeFormatter(AllStaticFields.userData.getTimeOfBirth()));
            binding.txtUserMobile.setText(AllStaticFields.userData.getMobile()==null ? "" : AllStaticFields.userData.getMobile().trim());
            binding.txtUserOccupation.setText(AllStaticFields.userData.getOccupation()==null||AllStaticFields.userData.getOccupation().equals("") ? "" : AllStaticFields.userData.getOccupation());
            binding.txtUserPlaceOfBirth.setText(AllStaticFields.userData.getPlaceOfBirth()==null||AllStaticFields.userData.getPlaceOfBirth().equals("") ? "" : AllStaticFields.userData.getPlaceOfBirth());
            binding.txtUserGender.setText(AllStaticMethods.getGenderNameByGenderId(AllStaticFields.userData.getGender()));

        }
        else {
            Intent intent1 = new Intent(context, LoginSignupActivity.class);
            startActivity(intent1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            setUserData();
        }

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
        registerReceiver(networkChange, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

    public static String dateFormatter(String unFormattedDate) {
        String datetime = null;
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = inputFormat.parse(unFormattedDate);
            datetime = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return datetime;

    }

    public static String timeFormatter(String unFormattedTime) {

        String time = null;
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm");

        try {
            Date convertedDate = inputFormat.parse(unFormattedTime);
            time = outputFormat.format(convertedDate);

        } catch (ParseException e) {

        }
        return time;
    }
}