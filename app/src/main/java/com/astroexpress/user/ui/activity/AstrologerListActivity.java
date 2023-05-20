package com.astroexpress.user.ui.activity;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CALL;
import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CHAT;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.astroexpress.user.R;
import com.astroexpress.user.adapter.LoadAstrologerCallListAdapter;
import com.astroexpress.user.adapter.LoadAstrologerChatListAdapter;
import com.astroexpress.user.adapter.LoadAstrologerListAdapter;
import com.astroexpress.user.adapter.LoadAstrologerListTrending1DashboardAdapter;
import com.astroexpress.user.adapter.LoadAstrologerTrendingListAdapter;
import com.astroexpress.user.adapter.MySpinnerAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityAstrologerListBinding;
import com.astroexpress.user.model.SpinnerModel;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.ImageUtils;
import com.astroexpress.user.utility.NetworkChange;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstrologerListActivity extends AppCompatActivity {

    Context context;
    String action;
    ActivityAstrologerListBinding binding;
    NetworkChange networkChange = new NetworkChange();
    List<AstrologerResponseModel.Result> astrologerList = new ArrayList<>();
    List<AstrologerResponseModel.Result> astrologerListOriginal = new ArrayList<>();
    List<AstrologerResponseModel.Result> astrologerTrendingListOriginal = new ArrayList<>();
    private List<SpinnerModel> priceOrderList = new ArrayList<>();
    private List<SpinnerModel> experienceOrderList = new ArrayList<>();
    private List<SpinnerModel> ratingOrderList = new ArrayList<>();
    String waitTime = "10 min";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAstrologerListBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        Toolbar toolbar = binding.layoutToolbar.toolbar;
        toolbar.setTitle("Astrologers");

        setSupportActionBar(toolbar);
        context = AstrologerListActivity.this;
        action = getIntent().getAction();
        callList();
        callWalletBalanceApi();
        binding.scrollDownUpdate.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPage();
                binding.scrollDownUpdate.setRefreshing(false);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<AstrologerResponseModel.Result> myList = new ArrayList<>();

                if (astrologerTrendingListOriginal.isEmpty()) {
                    for (AstrologerResponseModel.Result itemData : astrologerList) {
                        if (itemData.getFirstName().toLowerCase().contains(newText.toLowerCase())) {
                            myList.add(itemData);
                        }
                    }
                }
                else{
                    for (AstrologerResponseModel.Result itemData : astrologerTrendingListOriginal) {
                        if (itemData.getFirstName().toLowerCase().contains(newText.toLowerCase())) {
                            myList.add(itemData);
                        }
                    }

                }
                switch (action) {
                    case AppConstants
                            .ASTROLOGERS_FOR_CHAT:
                        LoadAstrologerChatListAdapter loadAstrologerChatListAdapter = new LoadAstrologerChatListAdapter(context, myList);
                        binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        binding.loadAstrologerList.setAdapter(loadAstrologerChatListAdapter);
                        break;
                    case AppConstants
                            .ASTROLOGERS_FOR_CALL:
                        LoadAstrologerCallListAdapter loadAstrologerCallListAdapter = new LoadAstrologerCallListAdapter(context, myList);
                        binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        binding.loadAstrologerList.setAdapter(loadAstrologerCallListAdapter);
                        break;
                    case AppConstants
                            .ASTROLOGERS_LIST:
                        LoadAstrologerListAdapter loadAstrologerListAdapter = new LoadAstrologerListAdapter(context, myList);
                        binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        binding.loadAstrologerList.setAdapter(loadAstrologerListAdapter);
                        break;
                        case AppConstants
                                .ASTROLOGERS_LIST_TRENDING:
                        LoadAstrologerTrendingListAdapter loadAstrologerTrendingListAdapter = new LoadAstrologerTrendingListAdapter(context, myList);
                        binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        binding.loadAstrologerList.setAdapter(loadAstrologerTrendingListAdapter);
                        break;
                }

                return false;
            }
        });

        populatePriceSpinner();
        populateExperienceSpinner();
        populateRatingSpinner();

}
    private void populateRatingSpinner() {
        ratingOrderList.clear();
        ratingOrderList.add(new SpinnerModel("0", "Rating", null));
        ratingOrderList.add(new SpinnerModel("1", "Low To High", null));
        ratingOrderList.add(new SpinnerModel("2", "High To Low", null));

        binding.spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterRatingList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter orderSpinnerAdapter = new MySpinnerAdapter(context, ratingOrderList);
        binding.spinnerRating.setAdapter(orderSpinnerAdapter);
    }

    private void populateExperienceSpinner() {
        experienceOrderList.clear();
        experienceOrderList.add(new SpinnerModel("0", "Experience", null));
        experienceOrderList.add(new SpinnerModel("1", "Low To High", null));
        experienceOrderList.add(new SpinnerModel("2", "High To Low", null));

        binding.spinnerExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterExperienceList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter orderSpinnerAdapter = new MySpinnerAdapter(context, experienceOrderList);
        binding.spinnerExperience.setAdapter(orderSpinnerAdapter);
    }

    private void populatePriceSpinner() {
        priceOrderList.clear();
        priceOrderList.add(new SpinnerModel("0", "Price", null));
        priceOrderList.add(new SpinnerModel("1", "Low To High", null));
        priceOrderList.add(new SpinnerModel("2", "High To Low", null));

        binding.spinnerPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterPriceList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        MySpinnerAdapter orderSpinnerAdapter = new MySpinnerAdapter(context, priceOrderList);
        binding.spinnerPrice.setAdapter(orderSpinnerAdapter);

    }

    private void filterPriceList() {
        switch (binding.spinnerPrice.getSelectedItemPosition()) {

            case 0:
                astrologerList.clear();
                astrologerList.addAll(astrologerListOriginal);
                break;
            case 1:
                Collections.sort(astrologerList, new Comparator<AstrologerResponseModel.Result>() {
                    public int compare(AstrologerResponseModel.Result obj1, AstrologerResponseModel.Result obj2) {
                        // ## Ascending order
                        return Double.valueOf(obj1.getChargePerMinute()).compareTo(Double.valueOf(obj2.getChargePerMinute())); // To compare string values
                    }
                });
                break;
            case 2:
                Collections.sort(astrologerList, new Comparator<AstrologerResponseModel.Result>() {
                    public int compare(AstrologerResponseModel.Result obj1, AstrologerResponseModel.Result obj2) {
                        // ## Descending order
                        return Double.valueOf(obj2.getChargePerMinute()).compareTo(Double.valueOf(obj1.getChargePerMinute())); // To compare string values
                    }
                });
                break;

        }

        switch (action) {
            case AppConstants
                    .ASTROLOGERS_FOR_CHAT:
                LoadAstrologerChatListAdapter loadAstrologerChatListAdapter = new LoadAstrologerChatListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerChatListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_FOR_CALL:
                LoadAstrologerCallListAdapter loadAstrologerCallListAdapter = new LoadAstrologerCallListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerCallListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_LIST:
                LoadAstrologerListAdapter loadAstrologerListAdapter = new LoadAstrologerListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerListAdapter);
                break;
                case AppConstants
                        .ASTROLOGERS_LIST_TRENDING:
                    LoadAstrologerTrendingListAdapter loadAstrologerTrendingListAdapter = new LoadAstrologerTrendingListAdapter(context, astrologerList);
                    binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    binding.loadAstrologerList.setAdapter(loadAstrologerTrendingListAdapter);
                break;
        }

    }

    private void filterExperienceList() {
        switch (binding.spinnerExperience.getSelectedItemPosition()) {
            case 0:
                astrologerList.clear();
                astrologerList.addAll(astrologerListOriginal);
                break;
            case 1:
                Collections.sort(astrologerList, new Comparator<AstrologerResponseModel.Result>() {
                    public int compare(AstrologerResponseModel.Result obj1, AstrologerResponseModel.Result obj2) {
                        // ## Ascending order
                        return Double.valueOf(obj1.getExperience()).compareTo(Double.valueOf(obj2.getExperience())); // To compare string values
                    }
                });
                break;
            case 2:
                Collections.sort(astrologerList, new Comparator<AstrologerResponseModel.Result>() {
                    public int compare(AstrologerResponseModel.Result obj1, AstrologerResponseModel.Result obj2) {
                        // ## Descending order
                        return Double.valueOf(obj2.getExperience()).compareTo(Double.valueOf(obj1.getExperience())); // To compare string values
                    }
                });
                break;
        }

        switch (action) {
            case AppConstants
                    .ASTROLOGERS_FOR_CHAT:
                LoadAstrologerChatListAdapter loadAstrologerChatListAdapter = new LoadAstrologerChatListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerChatListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_FOR_CALL:
                LoadAstrologerCallListAdapter loadAstrologerCallListAdapter = new LoadAstrologerCallListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerCallListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_LIST:
                LoadAstrologerListAdapter loadAstrologerListAdapter = new LoadAstrologerListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_LIST_TRENDING:
                LoadAstrologerTrendingListAdapter loadAstrologerTrendingListAdapter = new LoadAstrologerTrendingListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerTrendingListAdapter);
                break;
        }
    }

    private void filterRatingList() {

        switch (binding.spinnerRating.getSelectedItemPosition()) {
            case 0:
                astrologerList.clear();
                astrologerList.addAll(astrologerListOriginal);
                break;
            case 1:
                Collections.sort(astrologerList, new Comparator<AstrologerResponseModel.Result>() {
                    public int compare(AstrologerResponseModel.Result obj1, AstrologerResponseModel.Result obj2) {
                        // ## Ascending order
                        return Double.valueOf(obj1.getRatingCount()).compareTo(Double.valueOf(obj2.getRatingCount())); // To compare string values
                    }
                });
                break;
            case 2:
                Collections.sort(astrologerList, new Comparator<AstrologerResponseModel.Result>() {
                    public int compare(AstrologerResponseModel.Result obj1, AstrologerResponseModel.Result obj2) {
                        // ## Descending order
                        return Double.valueOf(obj2.getRatingCount()).compareTo(Double.valueOf(obj1.getRatingCount())); // To compare string values
                    }
                });
                break;

        }

        switch (action) {
            case AppConstants
                    .ASTROLOGERS_FOR_CHAT:
                LoadAstrologerChatListAdapter loadAstrologerChatListAdapter = new LoadAstrologerChatListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerChatListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_FOR_CALL:
                LoadAstrologerCallListAdapter loadAstrologerCallListAdapter = new LoadAstrologerCallListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerCallListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_LIST:
                LoadAstrologerListAdapter loadAstrologerListAdapter = new LoadAstrologerListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerListAdapter);
                break;
            case AppConstants
                    .ASTROLOGERS_LIST_TRENDING:
                LoadAstrologerTrendingListAdapter loadAstrologerTrendingListAdapter = new LoadAstrologerTrendingListAdapter(context, astrologerList);
                binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                binding.loadAstrologerList.setAdapter(loadAstrologerTrendingListAdapter);
                break;
        }
    }

    private void refreshPage() {

        switch (action) {
            case AppConstants
                    .ASTROLOGERS_FOR_CHAT:
                loadAstrologersListForChatFromAPI();
                break;
            case AppConstants
                    .ASTROLOGERS_FOR_CALL:
                loadAstrologersListForCallFromAPI();
                break;
            case AppConstants
                    .ASTROLOGERS_LIST:
                loadAstrologersAllListFromAPI();
                break;
            case AppConstants
                    .ASTROLOGERS_LIST_TRENDING:
                loadTrendingAstrologerListApi();
                break;
        }
        populatePriceSpinner();
        populateExperienceSpinner();
        populateRatingSpinner();
        callWalletBalanceApi();
    }

    private void callList() {

        switch (action) {
            case AppConstants
                    .ASTROLOGERS_FOR_CHAT:
                loadAstrologersListForChatFromAPI();
                break;
            case AppConstants
                    .ASTROLOGERS_FOR_CALL:
                loadAstrologersListForCallFromAPI();
                break;
            case AppConstants
                    .ASTROLOGERS_LIST:
                loadAstrologersAllListFromAPI();
                break;
            case AppConstants
                    .ASTROLOGERS_LIST_TRENDING:
                loadTrendingAstrologerListApi();
             break;
        }

    }

    private void loadAstrologersListForCallFromAPI() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);
        RetrofitAPIService.getApiClient().getAstrologer("OnlineForCall").enqueue(new Callback<AstrologerResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {

                binding.lottieProgressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            astrologerList.clear();
                            astrologerListOriginal.clear();
                            astrologerTrendingListOriginal.clear();
                            astrologerList.addAll(response.body().getResult());
                            astrologerListOriginal.addAll(response.body().getResult());

                            updateFreeValidList();

                            LoadAstrologerCallListAdapter loadAstrologerCallListAdapter = new LoadAstrologerCallListAdapter(context, astrologerListOriginal);
                            binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            binding.loadAstrologerList.setAdapter(loadAstrologerCallListAdapter);
//                            populateSpinner();

                        } else if (response.body().getCode().equals("404")) {

                            binding.noData.getRoot().setVisibility(View.VISIBLE);
                            binding.noData.cardBtnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

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
            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadAstrologersListForChatFromAPI() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);
        RetrofitAPIService.getApiClient().getAstrologer("OnlineForChat").enqueue(new Callback<AstrologerResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {

                binding.lottieProgressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            astrologerList.clear();
                            astrologerListOriginal.clear();
                            astrologerTrendingListOriginal.clear();
                            astrologerList.addAll(response.body().getResult());
                            astrologerListOriginal.addAll(response.body().getResult());
                            updateFreeValidList();

                            LoadAstrologerChatListAdapter loadAstrologerChatListAdapter = new LoadAstrologerChatListAdapter(context, astrologerListOriginal);
                            binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            binding.loadAstrologerList.setAdapter(loadAstrologerChatListAdapter);
//                            populateSpinner();
                        } else if (response.body().getCode().equals("404")) {

                            binding.noData.getRoot().setVisibility(View.VISIBLE);
                            binding.noData.cardBtnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

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
            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTrendingAstrologerListApi() {
        binding.lottieProgressBar.setVisibility(View.VISIBLE);
        RetrofitAPIService.getApiClient().getAstrologer("IsBoosted").enqueue(new Callback<AstrologerResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {
                binding.lottieProgressBar.setVisibility(View.GONE);

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            astrologerList.clear();
                            astrologerListOriginal.clear();
                            astrologerTrendingListOriginal.clear();
                            astrologerList.addAll(response.body().getResult());
                            astrologerListOriginal.addAll(response.body().getResult());

//                            updateFreeValidList();
                            updateTrendingAstrologerList(astrologerList);

                        } else if (response.body().getCode().equals("404")) {

                            binding.noData.getRoot().setVisibility(View.VISIBLE);
                            binding.noData.cardBtnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

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
            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadAstrologersAllListFromAPI() {

        binding.lottieProgressBar.setVisibility(View.VISIBLE);
        RetrofitAPIService.getApiClient().getAstrologer("All").enqueue(new Callback<AstrologerResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {

                binding.lottieProgressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            astrologerList.clear();
                            astrologerListOriginal.clear();
                            astrologerList.addAll(response.body().getResult());
                            astrologerListOriginal.addAll(response.body().getResult());

                            updateFreeValidList();

                            LoadAstrologerListAdapter loadAstrologerListAdapter = new LoadAstrologerListAdapter(context, astrologerListOriginal);
                            binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            binding.loadAstrologerList.setAdapter(loadAstrologerListAdapter);
//                            populateSpinner();

                        } else if (response.body().getCode().equals("404")) {

                            binding.noData.getRoot().setVisibility(View.VISIBLE);
                            binding.noData.cardBtnBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

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
            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateFreeValidList() {

        int x = 0;
        for (int i = 0; i < astrologerList.size(); i++) {
            if (x < 5) {
                astrologerList.get(i).setValidForFree(true);
                astrologerListOriginal.get(i).setValidForFree(true);
                x++;
            } else {
                break;
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void callWalletBalanceApi() {

        if (AllStaticFields.userData != null) {
            RetrofitAPIService.getApiClient().getWallet(AllStaticFields.userData.getUserId()).enqueue(new Callback<WalletResponseModel>() {
                @Override
                public void onResponse(Call<WalletResponseModel> call, Response<WalletResponseModel> response) {

                    try {

                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getCode().equals("200")) {

                                AllStaticFields.walletData = response.body().getResult();

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
                public void onFailure(Call<WalletResponseModel> call, Throwable t) {

                }
            });
        }
    }

    private void updateTrendingAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList)
    {
        int x = 0;
        for (int i = 0; i < astrologerDataAllList.size(); i++) {
            if (astrologerDataAllList.get(i).getBoosted()) {
                if (x < 5) {
                    astrologerDataAllList.get(i).setValidForFree(true);
                    astrologerTrendingListOriginal.add(astrologerDataAllList.get(i));
                    x++;
                } else {
                    astrologerTrendingListOriginal.add(astrologerDataAllList.get(i));
                }
            }
        }

        LoadAstrologerTrendingListAdapter loadAstrologerTrendingListAdapter = new LoadAstrologerTrendingListAdapter(context, astrologerTrendingListOriginal);
        binding.loadAstrologerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.loadAstrologerList.setAdapter(loadAstrologerTrendingListAdapter);

    }

}