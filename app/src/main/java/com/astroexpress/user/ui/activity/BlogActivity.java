package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.astroexpress.user.R;
import com.astroexpress.user.adapter.LoadBlogListAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityBlogBinding;
import com.astroexpress.user.model.responsemodel.BlogResponseModel;
import com.astroexpress.user.model.responsemodel.TestimonialResponseModels;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogActivity extends AppCompatActivity {

    Context context;
    ActivityBlogBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBlogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context =BlogActivity.this;

        toolbar = findViewById(R.id.layoutToolbar);
        toolbar.setTitle("Blog");
        setSupportActionBar(toolbar);

        LoadBlogListAdapter loadBlogListAdapter = new LoadBlogListAdapter(context, AllStaticFields.blogAllData);
        binding.loadBlogList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.loadBlogList.setAdapter(loadBlogListAdapter);
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

}