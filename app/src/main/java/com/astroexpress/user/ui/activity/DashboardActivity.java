package com.astroexpress.user.ui.activity;

import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CALL;
import static com.astroexpress.user.utility.AppConstants.ASTROLOGERS_FOR_CHAT;
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

import com.astroexpress.user.adapter.LoadTestimonialDashboardAdapter;
import com.astroexpress.user.R;
import com.astroexpress.user.adapter.LoadAstrologerCallListDashboardAdapter;
import com.astroexpress.user.adapter.LoadAstrologerChatListDashboardAdapter;
import com.astroexpress.user.adapter.LoadAstrologerListTrendingDashboardAdapter;
import com.astroexpress.user.adapter.SliderAdapter;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityDashboardBinding;
import com.astroexpress.user.model.firebaseNotification.FirebaseNotificationForFollowedAstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.AstrologerResponseModel;
import com.astroexpress.user.model.responsemodel.BannerResponseModel;
import com.astroexpress.user.model.responsemodel.ChatHistoryResponseModel;
import com.astroexpress.user.model.responsemodel.ChatSessionResponseModel;
import com.astroexpress.user.model.responsemodel.TestimonialResponseModels;
import com.astroexpress.user.model.responsemodel.UserStatusResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.MyNotificationManager;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ActivityDashboardBinding binding;
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
    List<AstrologerResponseModel.Result> astrologerListOriginal = new ArrayList<>();
    List<AstrologerResponseModel.Result> astrologerTrendingListOriginal = new ArrayList<>();
    List<AstrologerResponseModel.Result> astrologerCallListOriginal = new ArrayList<>();
    List<AstrologerResponseModel.Result> astrologerChatListOriginal = new ArrayList<>();

    List<AstrologerResponseModel.Result> astrologerAllList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        context = DashboardActivity.this;
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

        binding.cardCall.setOnClickListener(this);
        binding.cardChat.setOnClickListener(this);
        binding.cardWallet.setOnClickListener(this);
        binding.cardLiveClass.setOnClickListener(this);
        binding.llChatReturnToChat.setOnClickListener(this);

        callBannerApi("Banner");
//        callBannerApi("Coupon");
//        if (AllStaticFields.astrologerDataOnlineCallList.isEmpty()){
//            callAstrologerOnlineForCallApi();
//        }else {
//            binding.llLoadingScreen.setVisibility(View.GONE);
//            binding.lottieProgressBar.setVisibility(View.GONE);
//            setListOnlineForCall(AllStaticFields.astrologerDataOnlineCallList);
//        }
//        if (AllStaticFields.astrologerDataOnlineChatList.isEmpty() ){
//            callAstrologerOnlineForChatApi();
//        }else {
//            binding.llLoadingScreen.setVisibility(View.GONE);
//            binding.lottieProgressBar.setVisibility(View.GONE);
//            setListOnlineForChat(AllStaticFields.astrologerDataOnlineChatList);
//        }
//        if (AllStaticFields.astrologerDataBoostedAstrologerList.isEmpty()){
//            callBoostedAstrologerListApi();
//        }else {
//            binding.llLoadingScreen.setVisibility(View.GONE);
//            binding.lottieProgressBar.setVisibility(View.GONE);
//            setIsBoostedList(AllStaticFields.astrologerDataBoostedAstrologerList);
//        }

        if (AllStaticFields.astrologerDataOnlineCallList.isEmpty()
                && AllStaticFields.astrologerDataOnlineChatList.isEmpty()
                && AllStaticFields.astrologerDataBoostedAstrologerList.isEmpty()) {
            callAstrologerAllApi();
        } else {
            binding.llLoadingScreen.setVisibility(View.GONE);
            binding.lottieProgressBar.setVisibility(View.GONE);
            setIsBoostedList(AllStaticFields.astrologerDataBoostedAstrologerList);
            setListOnlineForCall(AllStaticFields.astrologerDataOnlineCallList);
            setListOnlineForChat(AllStaticFields.astrologerDataOnlineChatList);
        }

        callTestimonialApi();
        callUserStatusApi();
        callWalletBalanceApi();

        binding.viewAllChatAstrologerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CHAT));

            }
        });

        binding.viewAllCallAstrologerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CALL));

            }
        });

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

        if (AllStaticFields.userData != null) {
            View headerLayout = navigationView.getHeaderView(0);
            TextView headerUserName = (TextView) headerLayout.findViewById(R.id.txtUserFirstName);
            userId = AllStaticFields.userData.getUserId();
            headerUserName.setText(AllStaticFields.userData.getFirstName() == null || AllStaticFields.userData.getFirstName().equals("") ? "Guest" : AllStaticFields.userData.getFirstName());
            binding.txtUserFirstName.setText((AllStaticFields.userData.getFirstName() == null || AllStaticFields.userData.getFirstName().equals("") ? "Guest" : AllStaticFields.userData.getFirstName()));
            callMyRecentAstrologerApi();
            FirebaseMessaging.getInstance().subscribeToTopic("FollowedAstrologerOnline_"+AllStaticFields.userData.getUserId()).addOnCompleteListener(new OnCompleteListener<Void>() {
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
//                callBoostedAstrologerListApi();
//                callTestimonialApi();
                callUserStatusApi();
                callAstrologerAllApi();
                callWalletBalanceApi();
                if (AllStaticFields.userData != null) {
                    checkChatSession();
//                    View headerLayout = navigationView.getHeaderView(0);
//                    TextView headerUserName = (TextView) headerLayout.findViewById(R.id.txtUserFirstName);
//                    headerUserName.setText(AllStaticFields.userData.getFirstName() == null || AllStaticFields.userData.getFirstName().equals("") ? "Guest" : AllStaticFields.userData.getFirstName());
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
//    private void callBoostedAstrologerListApi() {
//
//        RetrofitAPIService.getApiClient().getAstrologer("IsBoosted").enqueue(new Callback<AstrologerResponseModel>() {
//            @Override
//            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {
//                binding.llLoadingScreen.setVisibility(View.GONE);
//                binding.lottieProgressBar.setVisibility(View.GONE);
//                try {
//
//                    if (response.isSuccessful() && response.body() != null) {
//
//                        if (response.body().getCode().equals("200")) {
////                            astrologerListOriginal.clear();
////                            astrologerListOriginal.addAll(response.body().getResult());
//
//
//                            AllStaticFields.astrologerDataBoostedAstrologerList.clear();
//                            AllStaticFields.astrologerDataBoostedAstrologerList.addAll(response.body().getResult());
//                            updateFreeValidList("IsBoosted");
//                            setIsBoostedList(AllStaticFields.astrologerDataBoostedAstrologerList);
//                        } else {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    AllStaticMethods.saveException(e);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
//                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//                binding.llLoadingScreen.setVisibility(View.GONE);
//                binding.lottieProgressBar.setVisibility(View.GONE);
//            }
//        });
//
//    }
//
//    private void callAstrologerOnlineForChatApi() {
//
////        binding.lottieProgressBar.setVisibility(View.VISIBLE);
//        RetrofitAPIService.getApiClient().getAstrologer("OnlineForChat").enqueue(new Callback<AstrologerResponseModel>() {
//            @Override
//            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {
//
//                binding.llLoadingScreen.setVisibility(View.GONE);
//                binding.lottieProgressBar.setVisibility(View.GONE);
//                try {
//
//                    if (response.isSuccessful() && response.body() != null) {
//
//                        if (response.body().getCode().equals("200")) {
////                            astrologerListOriginal.clear();
////                            astrologerListOriginal.addAll(response.body().getResult());
//
//                            AllStaticFields.astrologerDataOnlineChatList.clear();
//                            AllStaticFields.astrologerDataOnlineChatList.addAll(response.body().getResult());
//                            updateFreeValidList("OnlineForChat");
//                            setListOnlineForChat(AllStaticFields.astrologerDataOnlineChatList);
//
//                        } else {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    AllStaticMethods.saveException(e);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
//                binding.llLoadingScreen.setVisibility(View.GONE);
//                binding.lottieProgressBar.setVisibility(View.GONE);
//                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//            }
//        });
//        //  setList(null);
//    }
//
//    private void callAstrologerOnlineForCallApi() {
//
////        binding.lottieProgressBar.setVisibility(View.VISIBLE);
//        RetrofitAPIService.getApiClient().getAstrologer("OnlineForCall").enqueue(new Callback<AstrologerResponseModel>() {
//            @Override
//            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {
//
//                binding.llLoadingScreen.setVisibility(View.GONE);
//                binding.lottieProgressBar.setVisibility(View.GONE);
//                try {
//
//                    if (response.isSuccessful() && response.body() != null) {
//
//                        if (response.body().getCode().equals("200")) {
////                            astrologerListOriginal.clear();
////                            astrologerListOriginal.addAll(response.body().getResult());
////                            AllStaticFields.astrologerDataOnlineCallList.clear();
//                            AllStaticFields.astrologerDataOnlineCallList.clear();
//                            AllStaticFields.astrologerDataOnlineCallList.addAll(response.body().getResult());
//                            updateFreeValidList("OnlineForCall");
//
//                            setListOnlineForCall(AllStaticFields.astrologerDataOnlineCallList);
//
//                        } else {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    AllStaticMethods.saveException(e);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AstrologerResponseModel> call, Throwable t) {
//                binding.llLoadingScreen.setVisibility(View.GONE);
//                binding.lottieProgressBar.setVisibility(View.GONE);
//                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
//            }
//        });
//        //  setList(null);
//    }

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
            LoadAstrologerListTrendingDashboardAdapter loadAstrologerListTrendingDashboardAdapter = new LoadAstrologerListTrendingDashboardAdapter(context, result);
            binding.loadTrendingAstrologerListDashboard.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            binding.loadTrendingAstrologerListDashboard.setAdapter(loadAstrologerListTrendingDashboardAdapter);
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

    private void setListOnlineForCall(List<AstrologerResponseModel.Result> result) {
        LoadAstrologerCallListDashboardAdapter loadAstrologerCallListDashboardAdapter = new LoadAstrologerCallListDashboardAdapter(context, result);
        binding.loadAstrologerDashboardCallList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.loadAstrologerDashboardCallList.setAdapter(loadAstrologerCallListDashboardAdapter);

//        binding.llList.removeAllViews();
//        for (int i = 0; i < result.size(); i++) {
//
//            AstrologerResponseModel.Result itemData = result.get(i);
//
//            View view = LayoutInflater.from(context).inflate(R.layout.item_list_dashboard_online_call_astrologer, null);
//            TextView txtName = view.findViewById(R.id.txtName);
//            TextView txtSpacelist = view.findViewById(R.id.txtSpacelist);
//            TextView txtLanguage = view.findViewById(R.id.txtLanguage);
//            TextView txtExperience = view.findViewById(R.id.txtExperience);
//            TextView txtCharge = view.findViewById(R.id.txtCharge);
//            ImageView imgProfile = view.findViewById(R.id.imgProfile);
//
//            txtName.setText(itemData.getFirstName() + " " + itemData.getLastName());
//            txtSpacelist.setText(itemData.getSpeciality());
//            txtLanguage.setText(itemData.getLanguage());
//            txtExperience.setText(itemData.getExperience() + " Yrs");
//            txtCharge.setText("\u20B9 " + itemData.getChargePerMinute() + " Per Minute");
//
//            if (itemData.getProfileThumbnail() != null) {
//                imgProfile.setImageBitmap(ImageUtils.convert(itemData.getProfileThumbnail()));
//            }
//
//            if (itemData.getProfileImageUrl() != null) {
//
////                Picasso.get().load(itemData.getProfileImageUrl())
////                        .resize(200, 200)
////                        .into(imgProfile);
//
//                Glide.with(view)
//                        .load(itemData.getProfileImageUrl())
////                        .thumbnail(Glide.with(context).load(R.drawable.image_place_holder))
//                        .apply(new RequestOptions().override(250, 250))
//                        .into(imgProfile);
////                new ImageDownloader(itemData.getProfileImageUrl(), new ImageDownloader.ImageDownloadListener() {
////                    @Override
////                    public void onImageDownload(Bitmap bitmap) {
////                        imgProfile.setImageBitmap(bitmap);
////                    }
////                }).execute();
//            }
//
//            view.setTag(itemData);
//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    AllStaticFields.astrologerData = (AstrologerResponseModel.Result) view.getTag();
//                    startActivity(new Intent(context, AstrologerProfileActivity.class)
////                            .setAction(AppConstants.CHAT_TO_ASTROLOGER)
//                                    .putExtra("AstrologerId", itemData.getAstrologerId())
//                                    .putExtra("ValidForFree", itemData.getValidForFree().toString())
//
//                    );
//                }
//            });
//
//            binding.llList.addView(view);
//        }
    }

    private void setListOnlineForChat(List<AstrologerResponseModel.Result> result) {

        LoadAstrologerChatListDashboardAdapter loadAstrologerChatListDashboardAdapter = new LoadAstrologerChatListDashboardAdapter(context, result);
        binding.loadAstrologerDashboardChatList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.loadAstrologerDashboardChatList.setAdapter(loadAstrologerChatListDashboardAdapter);

//        binding.llList1.removeAllViews();
//        for (int i = 0; i < result.size(); i++) {
//
//            AstrologerResponseModel.Result itemData = result.get(i);
//
//            View view = LayoutInflater.from(context).inflate(R.layout.item_list_dashboard_online_chat_astrologer, null);
//            TextView txtName = view.findViewById(R.id.txtName);
//            TextView txtSpacelist = view.findViewById(R.id.txtSpacelist);
//            ImageView imgProfile = view.findViewById(R.id.imgProfile);
//
//            txtName.setText(itemData.getFirstName() + " " + itemData.getLastName());
//            txtSpacelist.setText(itemData.getSpeciality());
//            if (itemData.getProfileThumbnail() != null) {
//                imgProfile.setImageBitmap(ImageUtils.convert(itemData.getProfileThumbnail()));
//            }
//            if (itemData.getProfileImageUrl() != null) {
//
////                Picasso.get().load(itemData.getProfileImageUrl())
////                        .resize(200, 200)
////                        .into(imgProfile);
//
//                Glide.with(view)
//                        .load(itemData.getProfileImageUrl())
////                        .thumbnail(Glide.with(context).load(R.drawable.image_place_holder))
//                        .apply(new RequestOptions().override(250, 250))
//                        .into(imgProfile);
////                new ImageDownloader(itemData.getProfileImageUrl(), new ImageDownloader.ImageDownloadListener() {
////                    @Override
////                    public void onImageDownload(Bitmap bitmap) {
////                        imgProfile.setImageBitmap(bitmap);
////                    }
////                }).execute();
//            }
//
//            view.setTag(itemData);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    AllStaticFields.astrologerData = (AstrologerResponseModel.Result) view.getTag();
//                    startActivity(new Intent(context, AstrologerProfileActivity.class)
////                            .setAction(AppConstants.CHAT_TO_ASTROLOGER)
//                                    .putExtra("AstrologerId", itemData.getAstrologerId())
//                                    .putExtra("ValidForFree", itemData.getValidForFree().toString())
//
//                    );
////                            .putExtra("UserId", userId));
//                }
//            });
//
//            binding.llList1.addView(view);
//
//        }
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

            View view = LayoutInflater.from(context).inflate(R.layout.item_list_dashboard_myrecent_astrologer, null);

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
                            .putExtra("ValidForFree", "false")
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
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
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

//            case R.id.navSavedAddress:
//
//                if (AllStaticFields.userData != null) {
//                    Intent intent5 = new Intent(DashboardActivity.this, UserAddressActivity.class);
//                    startActivity(intent5);
//                } else {
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
                    Intent intent3 = new Intent(DashboardActivity.this, LoginSignupActivity.class);
                    startActivity(intent3);
                }

                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.share:
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Astro Express");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Download this Application now:- https://play.google.com/store/apps/details?");
//                startActivity(Intent.createChooser(shareIntent, "share via"));
//                break;
//
//            case R.id.cart:
//                Intent intent = new Intent(AddNewAddress.this, CartActivity.class);
//                startActivity(intent);
//
//            case R.id.logout:
//                logoutUser();
//                break;
//
//            default:
//                break;
//        }
//        return true;
//    }

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
        setTimeState();
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
            case R.id.cardChat:
                startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CHAT));
                break;

            case R.id.cardCall:
                startActivity(new Intent(context, AstrologerListActivity.class).setAction(ASTROLOGERS_FOR_CALL));
                break;
            case R.id.cardLiveClass:
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
//                startActivity(new Intent(context, ChatActivity.class)
//                        .putExtra("AstrologerId", astrologerId)
//                        .putExtra("UserId", userId));
                break;
        }

    }

    private void setTimeState() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.timeState.setText("Good Morning");
            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.time_morning, 0);
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.timeState.setText("Good Afternoon");
            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.time_noon, 0);
        } else if (timeOfDay >= 16 && timeOfDay < 24) {
            binding.timeState.setText("Good Evening");
            binding.timeState.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.time_evening, 0);
        }
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
                                    DashboardActivity.this,
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

    private void updateFreeValidList(String type) {

        switch (type) {
            case "IsBoosted": {
                int x = 0;
                for (int i = 0; i < AllStaticFields.astrologerDataBoostedAstrologerList.size(); i++) {
                    if (AllStaticFields.astrologerDataBoostedAstrologerList.get(i).getBoosted()) {
                        if (x < 5) {
                            AllStaticFields.astrologerDataBoostedAstrologerList.get(i).setValidForFree(true);
                            x++;
                        } else {
                            break;
                        }
                    }
                }
            }
            case "OnlineForCall": {
                int x = 0;
                for (int i = 0; i < AllStaticFields.astrologerDataOnlineCallList.size(); i++) {
                    if (x < 5) {
                        AllStaticFields.astrologerDataOnlineCallList.get(i).setValidForFree(true);
                        x++;
                    } else {
                        break;
                    }
                }
            }
            case "OnlineForChat": {
                int x = 0;
                for (int i = 0; i < AllStaticFields.astrologerDataOnlineChatList.size(); i++) {
                    if (x < 5) {
                        AllStaticFields.astrologerDataOnlineChatList.get(i).setValidForFree(true);
                        x++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void callAstrologerAllApi() {

        RetrofitAPIService.getApiClient().getAstrologer("All").enqueue(new Callback<AstrologerResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerResponseModel> call, Response<AstrologerResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            AllStaticFields.astrologerDataBoostedAstrologerList.clear();
                            AllStaticFields.astrologerDataOnlineCallList.clear();
                            AllStaticFields.astrologerDataOnlineChatList.clear();

                            astrologerCallListOriginal.clear();
                            astrologerChatListOriginal.clear();
                            astrologerTrendingListOriginal.clear();

                            updateTrendingAstrologerList(response.body().getResult());
                            updateCallAstrologerList(response.body().getResult());
                            updateChatAstrologerList(response.body().getResult());

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
//                binding.lottieProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTrendingAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList) {
        int x = 0;
        for (int i = 0; i < astrologerDataAllList.size(); i++) {
            if (astrologerDataAllList.get(i).getBoosted()) {
                astrologerTrendingListOriginal.add(astrologerDataAllList.get(i));
            }
        }

//        for (int i = 0; i < astrologerTrendingListOriginal.size(); i++) {
//            if (x < 5) {
//                astrologerTrendingListOriginal.get(i).setValidForFree(true);
//                x++;
//            } else {
//                break;
//            }
//        }

        binding.llLoadingScreen.setVisibility(View.GONE);
        binding.lottieProgressBar.setVisibility(View.GONE);

        setIsBoostedList(astrologerTrendingListOriginal);

    }

    private void updateCallAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList) {
        int x = 0;
        for (int j = 0; j < astrologerDataAllList.size(); j++) {
            if (astrologerDataAllList.get(j).isOnlineForCall()) {
                astrologerCallListOriginal.add(astrologerDataAllList.get(j));
//                if (y < 5) {
//                    astrologerDataAllList.get(j).setValidForFree(true);
//                    astrologerCallListOriginal.add(astrologerDataAllList.get(j));
//                    y++;
//                } else {
//                    astrologerDataAllList.get(j).setValidForFree(false);
//                    astrologerCallListOriginal.add(astrologerDataAllList.get(j));
//                }
            }
        }

//        for (int j = 0; j < astrologerCallListOriginal.size(); j++) {
//            if (x < 5) {
//                astrologerCallListOriginal.get(j).setValidForFree(true);
//                x++;
//            } else {
//                break;
//            }
//        }


        binding.llLoadingScreen.setVisibility(View.GONE);
        binding.lottieProgressBar.setVisibility(View.GONE);

        setListOnlineForCall(astrologerCallListOriginal);

    }

    private void updateChatAstrologerList(List<AstrologerResponseModel.Result> astrologerDataAllList) {
        int x = 0;
        for (int k = 0; k < astrologerDataAllList.size(); k++) {
            if (astrologerDataAllList.get(k).getOnlineForChat()) {
                astrologerChatListOriginal.add(astrologerDataAllList.get(k));
//            if (astrologerDataAllList.get(k).getOnlineForChat()) {
//                if (z < 5) {
//                    astrologerDataAllList.get(k).setValidForFree(true);
//                    astrologerChatListOriginal.add(astrologerDataAllList.get(k));
//                    z++;
//                } else {
//                    astrologerDataAllList.get(k).setValidForFree(false);
//                    astrologerChatListOriginal.add(astrologerDataAllList.get(k));
//                }
//            }
        }
        }

//        for (int y = 0; y < astrologerChatListOriginal.size(); y++) {
//            if (x < 5) {
//                astrologerChatListOriginal.get(y).setValidForFree(true);
//                x++;
//            } else {
//                break;
//            }
//        }

        binding.llLoadingScreen.setVisibility(View.GONE);
        binding.lottieProgressBar.setVisibility(View.GONE);
        setListOnlineForChat(astrologerChatListOriginal);

    }

}