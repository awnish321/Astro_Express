package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityRaisedTokenBinding;
import com.astroexpress.user.model.responsemodel.GetQueryResponseModel;
import com.astroexpress.user.model.responsemodel.SuggestedRemediesResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaisedTokenActivity extends AppCompatActivity {
    ActivityRaisedTokenBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRaisedTokenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         context=RaisedTokenActivity.this;
        binding.layoutToolbar.toolbar.setTitle("Raised Token");
        setSupportActionBar(binding.layoutToolbar.toolbar);

        callGetAllQuery();    
    }

    private void callGetAllQuery() {
        RetrofitAPIService.getApiClient().getAllQuery(AllStaticFields.userData.getUserId()).enqueue(new Callback<GetQueryResponseModel>() {
            @Override
            public void onResponse(Call<GetQueryResponseModel> call, Response<GetQueryResponseModel> response) {

                binding.lottieProgressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            setList(response.body().getResult());

                        }else if (response.body().getCode().equals("404")) {

                            binding.noData.getRoot().setVisibility(View.VISIBLE);
                            binding.noData.cardBtnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }
            }

            @Override
            public void onFailure(Call<GetQueryResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setList(List<GetQueryResponseModel.Result> result) {

        binding.llRaisedQuery.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            GetQueryResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.single_raised_ticket_item, null);
            TextView txtDateTime = view.findViewById(R.id.txtDateTime);
            TextView txtStatus = view.findViewById(R.id.txtStatus);
            TextView txtTitle = view.findViewById(R.id.txtTitle);
            TextView txtDescription = view.findViewById(R.id.txtDescription);

            txtDateTime.setText(itemData.getCreatedOn());
            txtTitle.setText(itemData.getTitle());
            txtDescription.setText(itemData.getDescription());

            if (itemData.getStatus().equals("0")){
                txtStatus.setText("Pending");
                txtStatus.setTextColor(getResources().getColor(R.color.orange));
            }else {
                txtStatus.setText("Resolved");
                txtStatus.setTextColor(getResources().getColor(R.color.green));
            }

            binding.llRaisedQuery.addView(view);

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

}