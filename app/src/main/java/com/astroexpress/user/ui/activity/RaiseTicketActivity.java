package com.astroexpress.user.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityRaiseTicketBinding;
import com.astroexpress.user.model.request.CallRequestModel;
import com.astroexpress.user.model.request.SubmitQueryRequestModel;
import com.astroexpress.user.model.responsemodel.SubmitQueryResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseTicketActivity extends AppCompatActivity {

    Toolbar toolbar;
    NetworkChange networkChange = new NetworkChange();
    ActivityRaiseTicketBinding binding;
    Context context;
    Boolean subject = false, description = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaiseTicketBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        context = RaiseTicketActivity.this;
        toolbar = findViewById(R.id.layoutToolbar);
        toolbar.setTitle("Raise Ticket");
        setSupportActionBar(toolbar);

        binding.btnSubmitTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallAuth();
            }
        });
    }

    private void CallAuth() {

        if (binding.editSubject.getText().length() > 0) {
            subject = true;
        } else {
            binding.subjectError.setError("subject field is empty");
        }
        if (binding.editDescription.getText().length() > 0) {
            description = true;
        } else {
            binding.descriptionError.setError("Description field is empty");
        }

        if (subject == true && description == true) {
            CallSubmitQuery();
        }
    }

    private void CallSubmitQuery() {

        SubmitQueryRequestModel submitQueryRequestModel = new SubmitQueryRequestModel
                (AllStaticFields.userData.getUserId(),
                AllStaticFields.userData.getFirstName()+""+AllStaticFields.userData.getLastName(),
                binding.editSubject.getText().toString(),
                binding.editDescription.getText().toString(),
                AllStaticFields.userData.getEmail(),
                AllStaticFields.userData.getMobile()
        );

        RetrofitAPIService.getApiClient().submitQuery(submitQueryRequestModel).enqueue(new Callback<SubmitQueryResponseModel>() {
            @Override
            public void onResponse(Call<SubmitQueryResponseModel> call, Response<SubmitQueryResponseModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            Toast.makeText(context, " Your Ticket Id is "+response.body().getResult().getHelpTokenId(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, NewDashboardActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<SubmitQueryResponseModel> call, Throwable t) {

            }
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
        registerReceiver(networkChange, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

}