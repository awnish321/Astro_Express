package com.astroexpress.user.ui.activity;

import static org.apache.commons.text.WordUtils.capitalize;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.astroexpress.user.databinding.ActivityTermAndConditionBinding;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TermAndConditionActivity extends AppCompatActivity {
    ActivityTermAndConditionBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermAndConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = TermAndConditionActivity.this;

//        binding.txtData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context,NewDashboardActivity.class));
//            }
//        });

    }
    }
