package com.astroexpress.user.ui.activity;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CALL;
import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CHAT;
import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_LIST_TRENDING;
import static com.astroexpress.user.utility.AppConstants.RC_APP_UPDATE;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.astroexpress.user.adapter.LoadBlogListAdapter;
import com.astroexpress.user.adapter.LoadBlogListDashboardAdapter;
import com.astroexpress.user.adapter.LoadUserTestimonialListAdapter;
import com.astroexpress.user.adapter.LoadTestimonialDashboardAdapter;
import com.astroexpress.user.R;
import com.astroexpress.user.adapter.LoadAstrologerListTrending1DashboardAdapter;
import com.astroexpress.user.adapter.SliderAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityNewDashboardBinding;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.BannerResponseModel;
import com.astroexpress.user.model.responsemodel.BlogResponseModel;
import com.astroexpress.user.model.responsemodel.ChatHistoryResponseModel;
import com.astroexpress.user.model.responsemodel.ChatSessionResponseModel;
import com.astroexpress.user.model.responsemodel.TestimonialResponseModels;
import com.astroexpress.user.model.responsemodel.UserStatusResponseModel;
import com.astroexpress.user.model.responsemodel.UserTestimonialResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.NetworkChange;
import com.astroexpress.user.utility.SharedPreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ActivityNewDashboardBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    NetworkChange networkChange = new NetworkChange();
    Context context;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Timer timer;
    DatabaseReference databaseReference;
    String uid;
    FirebaseDatabase firebaseDatabase;
    private String sessionId = "";
    private String astrologerId, userId = null;
    private Handler sliderHandler = new Handler();

    String RemainingFreeSession = null;
    private AppUpdateManager mAppUpdateManager;
    private int inAppUpdateType;
    private Task<AppUpdateInfo> appUpdateInfoTask;
    private InstallStateUpdatedListener installStateUpdatedListener;

    List<AstrologerResponseModel.Result> astrologerList = new ArrayList<>();

    List<AstrologerResponseModel.Result> astrologerTrendingListOriginal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        context = NewDashboardActivity.this;
        toolbar = findViewById(R.id.toolbar);
        AllStaticFields.userData = SharedPreferenceManager.getUserData(context);

//        toolbar.setTitle("Astro Express");
//        setSupportActionBar(toolbar);


//IN APP UPDATE
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        installStateUpdatedListener = installState -> {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            }
        };
        mAppUpdateManager.registerListener(installStateUpdatedListener);
        inAppUpdateType = AppUpdateType.IMMEDIATE;
        inAppUpdate();

//IN APP UPDATE

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        binding.btnAstrologerListCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CALL));
            }
        });
        binding.btnAstrologerListChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CHAT));
            }
        });
        binding.llFreeKundali.setOnClickListener(this);
        binding.llLiveCourses.setOnClickListener(this);
        binding.llKundaliMatching.setOnClickListener(this);
        binding.llDailyHoroscope.setOnClickListener(this);
        binding.llChatReturnToChat.setOnClickListener(this);

        callBannerApi("Banner");

        if (AllStaticFields.astrologerDataBoostedAstrologerList.isEmpty()
                && AllStaticFields.testimonialAllData.isEmpty()
                && AllStaticFields.blogAllData.isEmpty()) {
            callBoostedAstrologerListApi();
            callUserTestimonialApi();
            callBlogApi();

        } else {
            setIsBoostedList(AllStaticFields.astrologerDataBoostedAstrologerList);
            setUserTestimonialList(AllStaticFields.testimonialAllData);
            setBlogList(AllStaticFields.blogAllData);
        }

        callUserStatusApi();
        callWalletBalanceApi();
        callTestimonialApi();

        if (getIntent().getBooleanExtra("IsFromNotification", false)) {

            final String astrologerFollowerTopic = "FollowedAstrologerOnline_" + AllStaticFields.userData.getUserId();

            if (getIntent().getStringExtra("Topic").equals(astrologerFollowerTopic)) {
                startActivity(new Intent(context, AstrologerProfileActivity.class)
                        .putExtra("AstrologerId", AllStaticFields.firebaseNotificationForFollowedAstrologerResponseModel.getAstrologerId())
                        .putExtra("ValidForFree", "false"));
            }

            switch (getIntent().getStringExtra("Topic")) {
                case "UserAll":
                    if (AllStaticFields.firebaseNotificationForAllResponseModel.getDataType().equals("Chat")) {
                        startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CHAT));
                    } else if (AllStaticFields.firebaseNotificationForAllResponseModel.getDataType().equals("VoiceCall")) {
                        startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CALL));
                    }
                    break;
            }
        }

        binding.viewAllTrendingAstrologer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_LIST_TRENDING));
            }
        });

        if (AllStaticFields.userData != null) {
            View headerLayout = navigationView.getHeaderView(0);
            TextView headerUserName = (TextView) headerLayout.findViewById(R.id.txtUserFirstName);
            userId = AllStaticFields.userData.getUserId();
            headerUserName.setText(AllStaticFields.userData.getFirstName() == null || AllStaticFields.userData.getFirstName().equals("") ? "Guest" : AllStaticFields.userData.getFirstName());
//            binding.txtUserFirstName.setText((AllStaticFields.userData.getFirstName() == null || AllStaticFields.userData.getFirstName().equals("") ? "Guest" : AllStaticFields.userData.getFirstName()));
            callMyRecentAstrologerApi();
            FirebaseMessaging.getInstance().subscribeToTopic("FollowedAstrologerOnline_" + AllStaticFields.userData.getUserId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = "Subscribed";
                    if (!task.isSuccessful()) {
                        msg = "Subscribe failed";
                    }
                }
            });

        }

        binding.scrollDownUpdate.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                callBannerApi("Banner");
//                callBannerApi("Coupon");
//                callAstrologerOnlineForCallApi();
//                callAstrologerOnlineForChatApi();
//                callTestimonialApi();
                callUserStatusApi();
                callBoostedAstrologerListApi();
                callWalletBalanceApi();
                if (AllStaticFields.userData != null) {
                    checkChatSession();
                    callMyRecentAstrologerApi();
                    View headerLayout = navigationView.getHeaderView(0);
                    TextView headerUserName = (TextView) headerLayout.findViewById(R.id.txtUserFirstName);
                    headerUserName.setText(AllStaticFields.userData.getFirstName() == null || AllStaticFields.userData.getFirstName().equals("") ? "Guest" : AllStaticFields.userData.getFirstName());
//                    binding.txtUserFirstName.setText((AllStaticFields.userData.getFirstName() == null || AllStaticFields.userData.getFirstName().equals("") ? "Guest" : AllStaticFields.userData.getFirstName()));

                }
                binding.scrollDownUpdate.setRefreshing(false);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("UserAll").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "Subscribed";
                if (!task.isSuccessful()) {
                    msg = "Subscribe failed";
                }
            }
        });

        binding.toolbar.appWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, WalletActivity.class));
                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }
            }
        });

    }

    private void callUserTestimonialApi() {
        RetrofitAPIService.getApiClient().getAllUserTestimonial().enqueue(new Callback<UserTestimonialResponseModel>() {
            @Override
            public void onResponse(Call<UserTestimonialResponseModel> call, Response<UserTestimonialResponseModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            AllStaticFields.testimonialAllData.clear();
                            AllStaticFields.testimonialAllData.addAll(response.body().getResult());
                            setUserTestimonialList(response.body().getResult());

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
            public void onFailure(Call<UserTestimonialResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void callBlogApi() {

        RetrofitAPIService.getApiClient().getAllBlogs().enqueue(new Callback<BlogResponseModel>() {
            @Override
            public void onResponse(Call<BlogResponseModel> call, Response<BlogResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getCode().equals("200")) {

                            AllStaticFields.blogAllData.clear();
                            AllStaticFields.blogAllData.addAll(response.body().getResult());
                            setBlogList(response.body().getResult());

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
            public void onFailure(Call<BlogResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void callMyRecentAstrologerApi() {

        RetrofitAPIService.getApiClient().getChatHistory(AllStaticFields.userData.getUserId()).enqueue(new Callback<ChatHistoryResponseModel>() {
            @Override
            public void onResponse(Call<ChatHistoryResponseModel> call, Response<ChatHistoryResponseModel> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getCode().equals("200")) {

                            binding.llMyRecentAstrologer.setVisibility(View.VISIBLE);
                            setListMyRecentAstrologer(response.body().getResult());

                        } else {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<ChatHistoryResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkChatSession() {

        if (AllStaticFields.userData != null) {

            RetrofitAPIService.getApiClient().getChatRunningSession(AllStaticFields.userData.getUserId()).enqueue(new Callback<ChatSessionResponseModel>() {
                @Override
                public void onResponse(Call<ChatSessionResponseModel> call, Response<ChatSessionResponseModel> response) {

                    try {

                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getCode().equals("200")) {
                                AllStaticFields.sessionId = response.body().getResult().getSessionId();
                                astrologerId = response.body().getResult().getAstrologerId();
                                binding.llChatReturnToChat.setVisibility(View.VISIBLE);
                            } else {
                                AllStaticFields.sessionId = null;
                                binding.llChatReturnToChat.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        AllStaticMethods.saveException(e);
                    }
                }

                @Override
                public void onFailure(Call<ChatSessionResponseModel> call, Throwable t) {

                }
            });
        }
    }

    private void checkAgainChatSession() {

        if (AllStaticFields.userData != null) {

            RetrofitAPIService.getApiClient().getChatRunningSession(AllStaticFields.userData.getUserId()).enqueue(new Callback<ChatSessionResponseModel>() {
                @Override
                public void onResponse(Call<ChatSessionResponseModel> call, Response<ChatSessionResponseModel> response) {

                    try {

                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getCode().equals("200")) {
                                startActivity(new Intent(context, ChatActivity.class)
                                        .putExtra("AstrologerId", astrologerId)
                                        .putExtra("UserId", userId));
                            } else {
                                AllStaticFields.sessionId = null;
                                binding.llChatReturnToChat.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        AllStaticMethods.saveException(e);
                    }
                }

                @Override
                public void onFailure(Call<ChatSessionResponseModel> call, Throwable t) {

                }
            });
        }
    }

    private void callUserStatusApi() {
        if (AllStaticFields.userData != null) {
            RetrofitAPIService.getApiClient().userStatus(AllStaticFields.userData.getUserId()).enqueue(new Callback<UserStatusResponseModel>() {
                @Override
                public void onResponse(Call<UserStatusResponseModel> call, Response<UserStatusResponseModel> response) {

                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getCode().equals("200")) {
                                if (response.body().getResult().getIsDeleted() == true || response.body().getResult().getIsActive() == false) {
                                    SharedPreferenceManager.logout(context);
                                    AllStaticFields.userData = null;
                                    FirebaseAuth.getInstance().signOut();
                                }

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
                public void onFailure(Call<UserStatusResponseModel> call, Throwable t) {
                    Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void callBoostedAstrologerListApi() {

        RetrofitAPIService.getApiClient().getAstrologer("IsBoosted").enqueue(new Callback<AstrologerResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            AllStaticFields.astrologerDataBoostedAstrologerList.clear();
                            astrologerTrendingListOriginal.clear();
                            astrologerList.addAll(response.body().getResult());
                            updateAstrologerList(astrologerList);

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
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callTestimonialApi() {

        RetrofitAPIService.getApiClient().getTestimonial().enqueue(new Callback<TestimonialResponseModels>() {
            @Override
            public void onResponse(Call<TestimonialResponseModels> call, Response<TestimonialResponseModels> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            setTestimonialList(response.body().getResult());

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
            public void onFailure(Call<TestimonialResponseModels> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setIsBoostedList(List<AstrologerResponseModel.Result> result) {

        if (!result.isEmpty()) {
            binding.llTrendingAstrologer.setVisibility(View.VISIBLE);
            binding.loadTrendingAstrologerListDashboard.setVisibility(View.VISIBLE);

            LoadAstrologerListTrending1DashboardAdapter loadAstrologerListTrending1DashboardAdapter = new LoadAstrologerListTrending1DashboardAdapter(context, result);
            binding.loadTrendingAstrologerListDashboard.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            binding.loadTrendingAstrologerListDashboard.setAdapter(loadAstrologerListTrending1DashboardAdapter);
        }

//        binding.llListTrendingAstrologer.removeAllViews();
//        for (int i = 0; i < result.size(); i++) {
//
//            AstrologerResponseModel.Result itemData = result.get(i);
//
//            if (itemData.isBoosted()) {
//                binding.llTrendingAstrologer.setVisibility(View.VISIBLE);
//                View view = LayoutInflater.from(context).inflate(R.layout.item_list_dashboard_online_call_astrologer, null);
//
//                TextView txtName = view.findViewById(R.id.txtName);
//                TextView txtSpacelist = view.findViewById(R.id.txtSpacelist);
//                TextView txtLanguage = view.findViewById(R.id.txtLanguage);
//                TextView txtExperience = view.findViewById(R.id.txtExperience);
//                TextView txtCharge = view.findViewById(R.id.txtCharge);
//                ImageView imgProfile = view.findViewById(R.id.imgProfile);
//
//                txtName.setText(itemData.getFirstName() + " " + itemData.getLastName());
//                txtSpacelist.setText(itemData.getSpeciality());
//                txtLanguage.setText(itemData.getLanguage());
//                txtExperience.setText(itemData.getExperience() + " Yrs");
//                txtCharge.setText("\u20B9 " + itemData.getChargePerMinute() + " Per Minute");
//                if (itemData.getProfileThumbnail() != null) {
//                    imgProfile.setImageBitmap(ImageUtils.convert(itemData.getProfileThumbnail()));
//                }
//                if (itemData.getProfileImageUrl() != null) {
//
//                    Picasso.get().load(itemData.getProfileImageUrl())
//                            .resize(200, 200)
//                            .into(imgProfile);
////                new ImageDownloader(itemData.getProfileImageUrl(), new ImageDownloader.ImageDownloadListener() {
////                    @Override
////                    public void onImageDownload(Bitmap bitmap) {
////                        imgProfile.setImageBitmap(bitmap);
////                    }
////                }).execute();
//                }
//
//                view.setTag(itemData);
//
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        AllStaticFields.astrologerData = (AstrologerResponseModel.Result) view.getTag();
//                        startActivity(new Intent(context, AstrologerProfileActivity.class)
////                                .setAction(AppConstants.CHAT_TO_ASTROLOGER)
//                                        .putExtra("AstrologerId", itemData.getAstrologerId())
//                                        .putExtra("RemainingFreeSession", itemData.getRemaningFreeSession().toString())
//                                        .putExtra("ValidForFree", itemData.getValidForFree().toString())
//
//                        );
////                                .putExtra("UserId", userId));
//
//                    }
//                });
//
//                binding.llListTrendingAstrologer.addView(view);
//            }
//
//        }
    }

    private void setUserTestimonialList(List<UserTestimonialResponseModel.Result> result) {

        binding.txtUserTestimonial.setVisibility(View.VISIBLE);
        binding.loadNewDashboardUserTestimonial.setVisibility(View.VISIBLE);

        LoadUserTestimonialListAdapter loadUserTestimonialListAdapter = new LoadUserTestimonialListAdapter(context, result);
        binding.loadNewDashboardUserTestimonial.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.loadNewDashboardUserTestimonial.setAdapter(loadUserTestimonialListAdapter);

        binding.llLoadingScreen.setVisibility(View.GONE);
        binding.lottieProgressBar.setVisibility(View.GONE);
    }

    private void setBlogList(List<BlogResponseModel.Result> result) {

        binding.cardViewBlogListDashboard.setVisibility(View.VISIBLE);
        binding.loadBlogListDashboard.setVisibility(View.VISIBLE);

        LoadBlogListDashboardAdapter loadBlogListDashboardAdapter = new LoadBlogListDashboardAdapter(context, result);
        binding.loadBlogListDashboard.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.loadBlogListDashboard.setAdapter(loadBlogListDashboardAdapter);

        binding.llLoadingScreen.setVisibility(View.GONE);
        binding.lottieProgressBar.setVisibility(View.GONE);
    }

    private void setTestimonialList(List<TestimonialResponseModels.Result> result) {

        LoadTestimonialDashboardAdapter loadTestimonialDashboardAdapter = new LoadTestimonialDashboardAdapter(context, result);
        binding.loadDashboardTestimonial.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.loadDashboardTestimonial.setAdapter(loadTestimonialDashboardAdapter);
    }

    private void setListMyRecentAstrologer(List<ChatHistoryResponseModel.Result> result) {

        binding.llListMyRecentAstrologer.removeAllViews();
        for (int i = 0; i < result.size(); i++) {

            ChatHistoryResponseModel.Result itemData = result.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_dashboard_myrecent1_astrologer, null);

            TextView txtAstrologerName = view.findViewById(R.id.txtAstrologerName);
            ImageView imgProfile = view.findViewById(R.id.imgProfile);

            txtAstrologerName.setText(itemData.getFirstName() + " " + itemData.getLastName());

            if (itemData.getProfileThumbnail() != null) {
                Picasso.get().load(itemData.getProfileThumbnail())
                        .resize(250, 250)
                        .into(imgProfile);

            }

            if (itemData.getProfileImageUrl() != null) {

                Picasso.get().load(itemData.getProfileImageUrl())
                        .resize(250, 250)
                        .into(imgProfile);

//                new ImageDownloader(itemData.getProfileImageUrl(), new ImageDownloader.ImageDownloadListener() {
//                    @Override
//                    public void onImageDownload(Bitmap bitmap) {
//                        imgProfile.setImageBitmap(bitmap);
//                    }
//                }).execute();
            }

            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, AstrologerProfileActivity.class)
                            .putExtra("AstrologerId", itemData.getAstrologerId())
                    );

                }
            });

            view.setTag(itemData);

            binding.llListMyRecentAstrologer.addView(view);

        }
    }

    private void callBannerApi(String type) {

        RetrofitAPIService.getApiClient().banner(type).enqueue(new Callback<BannerResponseModel>() {
            @Override
            public void onResponse(Call<BannerResponseModel> call, Response<BannerResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            switch (type) {
                                case "Banner":
                                    binding.viewPagerImageSlider.setAdapter(new SliderAdapter(response.body().getResult(), binding.viewPagerImageSlider));
                                    binding.viewPagerImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                        @Override
                                        public void onPageSelected(int position) {
                                            super.onPageSelected(position);
                                            sliderHandler.removeCallbacks(sliderRunner);
                                            sliderHandler.postDelayed(sliderRunner, 5000);
                                        }
                                    });
                                    break;
                                case "Coupon":
                                    binding.viewPagerImageSlider.setAdapter(new SliderAdapter(response.body().getResult(), binding.viewPagerImageSlider));
                                    binding.viewPagerImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                        @Override
                                        public void onPageSelected(int position) {
                                            super.onPageSelected(position);
                                            sliderHandler.removeCallbacks(sliderRunner);
                                            sliderHandler.postDelayed(sliderRunner, 5000);
                                        }
                                    });
                                    break;
                            }

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
            public void onFailure(Call<BannerResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.navHome:
                Intent intent = new Intent(NewDashboardActivity.this, NewDashboardActivity.class);
                startActivity(intent);
                break;

            case R.id.navWallet:

                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, WalletActivity.class));

                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }

                break;

            case R.id.navCallHistory:

                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, CallHistoryActivity.class));
                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }

                break;

            case R.id.navHelpdesk:
                startActivity(new Intent(context, HelpdeskActivity.class));
                break;

            case R.id.navFollowing:
                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, UserFollowListActivity.class));
                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }
                break;

            case R.id.navProfile:

                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, MyProfileActivity.class));

                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));

                }
                break;

//            case R.id.navSavedProfile:
//
//                if (AllStaticFields.userData != null)
//                {
//
//                    Intent intent5 = new Intent(DashboardActivity.this, SavedProfileActivity.class);
//                    startActivity(intent5);
//                }
//                else
//                {
//                    Intent intent1 = new Intent(DashboardActivity.this, LoginSignupActivity.class);
//                    startActivity(intent1);
//                }
//
//                break;

            case R.id.navChatHistory:

                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, ChatHistoryActivity.class));
                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }

                break;
            case R.id.navMyTransation:

                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, MyWalletTransactionActivity.class));

                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));

                }

                break;

            case R.id.navTermAndCondition:

                startActivity(new Intent(context, TermAndConditionActivity.class));

                break;

//            case R.id.navReferAndEarn:
//
//                if (AllStaticFields.userData != null) {
//                    startActivity(new Intent(context,ReferAndEarnActivity.class));
//                } else {
//                    startActivity(new Intent(context,LoginSignupActivity.class));
//                }
//
//                break;

            case R.id.navShare:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Astro Express");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download this Application now:- https://play.google.com/store/apps/details?");
                startActivity(Intent.createChooser(shareIntent, "share via"));
                break;

            case R.id.navLogout:

                if (AllStaticFields.userData != null) {

                    SharedPreferenceManager.logout(context);
                    AllStaticFields.userData = null;
                    FirebaseAuth.getInstance().signOut();
                    intent = new Intent(context, LoginSignupActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent3 = new Intent(NewDashboardActivity.this, LoginSignupActivity.class);
                    startActivity(intent3);
                }

                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AllStaticFields.userData != null) {
            checkChatSession();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, intentFilter);
//        setTimeState();
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        databaseReference.setValue("offline");
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llLiveCourses:
            case R.id.llFreeKundali:
            case R.id.llKundaliMatching:
            case R.id.llDailyHoroscope:
                startActivity(new Intent(context, ComingSoonActivity.class));
                break;
            case R.id.cardWallet:
                if (AllStaticFields.userData != null) {
                    startActivity(new Intent(context, WalletActivity.class));
                } else {
                    startActivity(new Intent(context, LoginSignupActivity.class));
                }
                break;
            case R.id.llChatReturnToChat:
                checkAgainChatSession();
                break;
        }

    }

    //    private void setTimeState() {
//        Calendar c = Calendar.getInstance();
//        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
//
//        if (timeOfDay >= 0 && timeOfDay < 12) {
//            binding.timeState.setText("Good Morning");
//            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.time_morning, 0);
//        } else if (timeOfDay >= 12 && timeOfDay < 16) {
//            binding.timeState.setText("Good Afternoon");
//            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.time_noon, 0);
//        } else if (timeOfDay >= 16 && timeOfDay < 24) {
//            binding.timeState.setText("Good Evening");
//            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.time_evening, 0);
//        }
//    }
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

    private Runnable sliderRunner = new Runnable() {
        @Override
        public void run() {
            binding.viewPagerImageSlider.setCurrentItem(binding.viewPagerImageSlider.getCurrentItem() + 1);
        }
    };

    private void inAppUpdate() {

        try {
            // Checks that the platform will allow the specified type of update.
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            // For a flexible update, use AppUpdateType.FLEXIBLE
                            && appUpdateInfo.isUpdateTypeAllowed(inAppUpdateType)) {
                        // Request the update.

                        try {
                            mAppUpdateManager.startUpdateFlowForResult(
                                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                    appUpdateInfo,
                                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                    inAppUpdateType,
                                    // The current activity making the update request.
                                    NewDashboardActivity.this,
                                    // Include a request code to later monitor this update request.

                                    RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException ignored) {

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void popupSnackBarForCompleteUpdate() {
        try {
            Snackbar snackbar =
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            "An update has just been downloaded.\nRestart to update",
                            Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("INSTALL", view -> {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.completeUpdate();
                }
            });
            snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
            snackbar.show();

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    //    private void updateFreeValidList(List<AstrologerResponseModel.Result> result)  {
//
//        int x = 0;
//
//        for (int i = 0; i < result.size(); i++) {
//            if (x < 5) {
//                AllStaticFields.astrologerDataBoostedAstrologerList.get(i).setValidForFree(true);
//                x++;
//            } else {
//                break;
//            }
//        }
//
//    }
    private void updateAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList) {
        int x = 0;
        for (int i = 0; i < astrologerDataAllList.size(); i++) {
            if (astrologerDataAllList.get(i).getBoosted()) {
                astrologerTrendingListOriginal.add(astrologerDataAllList.get(i));
            }
        }

        binding.llLoadingScreen.setVisibility(View.GONE);
        binding.lottieProgressBar.setVisibility(View.GONE);
        setIsBoostedList(astrologerTrendingListOriginal);

    }

}