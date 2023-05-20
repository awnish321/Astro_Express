package com.astroexpress.user.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityChatBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;
import com.astroexpress.user.model.request.ChatRequestModel;
import com.astroexpress.user.model.request.RequestForChatRequestModel;
import com.astroexpress.user.model.responsemodel.AstrologerDetailResponseModel;
import com.astroexpress.user.model.responsemodel.ChatEndResponseModel;
import com.astroexpress.user.model.responsemodel.ChatListResponseModel;
import com.astroexpress.user.model.responsemodel.ChatSessionResponseModel;
import com.astroexpress.user.model.responsemodel.RequestForChatResponseModel;
import com.astroexpress.user.model.responsemodel.SaveChatImageResponseModel;
import com.astroexpress.user.model.responsemodel.SaveChatResponseModel;
import com.astroexpress.user.model.responsemodel.WalletResponseModel;
import com.astroexpress.user.ui.dialog.AstrologerNotRespondingChatDialog;
import com.astroexpress.user.ui.dialog.ChatImageDialog;
import com.astroexpress.user.ui.dialog.MyProgressDialog;
import com.astroexpress.user.ui.dialog.RatingReviewDialog;
import com.astroexpress.user.ui.dialog.WalletBalanceCheckDialog;
import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.ImageUtils;
import com.astroexpress.user.utility.NetworkChange;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    final int SEEN_STATUS_PROCESS = -1;
    final int SEEN_STATUS_SENT = 0;
    final int SEEN_STATUS_DELIVERED = 1;
    final int SEEN_STATUS_READ = 2;
    final String TEXT_MESSAGE = "TextMessage";
    final String FILE_MESSAGE = "FileMessage";
    final String IMAGE_MESSAGE = "ImageMessage";
    final String TYPING_STATUS = "TypingStatus";
    final String TERMINATE_STATUS = "TerminateStatus";
    private Chronometer chronometerTimer;
    private static final int PICK_IMAGE = 5;
    private String picturePathIMG, fileUrl = null;
    List<String> attachments;
    String date = "";
    private static final int REQ_CODE_SPEECH_INPUT = 11;
    ChatImageDialog chatImageDialog;
    ActivityChatBinding binding;
    Toolbar toolbar;
    Context context;
    String action;
    DatabaseReference dbRefPresenceSystem;
    DatabaseReference dbRefConversations;
    DatabaseReference dbRefStatus;
    DatabaseReference dbCallingRefStatus;
    RatingReviewDialog ratingReviewDialog;
    AstrologerNotRespondingChatDialog astrologerNotRespondingChatDialog;
    private String userId;
    private String astrologerId;
    String RemainingFreeSession = null;
    NetworkChange networkChange = new NetworkChange();
    WalletBalanceCheckDialog walletBalanceCheckDialog;
    private CountDownTimer countDownTimer;
    private ChildEventListener conversationListener;
    private ValueEventListener requestListener;
    Uri Selected_Image_Uri = null;
    long chargePerMinute = 0;
    long remainingSecond = 0;
    boolean isChatRunning = true;
    //    private String sessionId = "";
    long START_TIME_IN_MILLIS = 0;
    long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private final int STORAGE_PERMISSION_CODE = 7;
    final int SELECT_IMAGE = 100;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    Boolean ValidForFree;
    CountDownTimer astrologerNotRespondingTimer = null;
    CountDownTimer userLowBalanceTimer = null;
    CountDownTimer remainingChatTime = null;
    MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        toolbar = binding.layoutToolbar.chatToolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        context = ChatActivity.this;
        action = getIntent().getAction();
        userId = getIntent().getStringExtra("UserId");
        astrologerId = getIntent().getStringExtra("AstrologerId");
        RemainingFreeSession = getIntent().getStringExtra("RemainingFreeSession");
        ValidForFree = Boolean.valueOf(getIntent().getStringExtra("ValidForFree"));

        AllStaticFields.astrologerId = astrologerId;

        callAstrologerDetailApi();

        myProgressDialog = new MyProgressDialog(context);

        binding.imgBtnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(ChatActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    getImageFromGallery();

                } else {
                    requestStoragePermission();
                }

            }
        });

//        binding.imgSpeak.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                promptSpeechInput();
//            }
//        });

//-----------------------------------------------------------------------------------------------------------------------------------//

        if (walletBalanceCheckDialog != null && walletBalanceCheckDialog.isShowing()) {
            walletBalanceCheckDialog.dismiss();
            walletBalanceCheckDialog = null;
        }

        walletBalanceCheckDialog = new WalletBalanceCheckDialog(context, new OnSubmitListeners() {
            @Override
            public void onSubmit(String value) {
                startActivity(new Intent(context, WalletActivity.class));
                walletBalanceCheckDialog.dismiss();
                finish();
            }

            @Override
            public void onCancel(String value) {
                finish();
            }
        });

        dbRefPresenceSystem = FirebaseDatabase.getInstance().getReference("Chats")
                .child("Astrologer")
                .child(astrologerId);

        dbRefConversations = FirebaseDatabase.getInstance().getReference("Chats")
                .child("User::Astrologer")
                .child(userId + "::" + astrologerId)
                .child("Conversations");

        dbRefStatus = FirebaseDatabase.getInstance().getReference("Chats")
                .child("User::Astrologer")
                .child(userId + "::" + astrologerId)
                .child("Status");

        dbCallingRefStatus = FirebaseDatabase.getInstance().getReference("Chats")
                .child("Astrologer")
                .child(astrologerId)
                .child("Status");

        dbRefConversations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    String res = snapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        conversationListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                binding.llTypingStatus.setVisibility(View.GONE);
                ChatRequestModel chatRequestModelResult = snapshot.getValue(ChatRequestModel.class);
                if (!chatRequestModelResult.getIsSentByUser()) {
                    chatRequestModelResult.setSeenStatus("" + SEEN_STATUS_READ);
                    setReceivedMessageUI(chatRequestModelResult);
                    dbRefConversations.child(chatRequestModelResult.getFirebaseChatId()).setValue(chatRequestModelResult).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }
                        }
                    });
                }
//               else{
//                   setSendMessageUI(Integer.parseInt(chatRequestModel.getSeenStatus()),chatRequestModel);
//               }
                scrollToBottom();
//                Toast.makeText(context, "Added " + snapshot.getKey(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.getValue() == null)
                    return;
                ChatRequestModel chatRequestModelResult = snapshot.getValue(ChatRequestModel.class);
                if (chatRequestModelResult.getIsSentByUser()) {
                    if (Selected_Image_Uri != null) {
                        fileUrl = String.valueOf(Selected_Image_Uri);
                    }
                    List<ChatRequestModel> chatRequestModelList = new ArrayList<>();
                    ChatRequestModel chatRequestModel = new ChatRequestModel(
                            userId,
                            astrologerId,
                            null,
                            null,
                            chatRequestModelResult.getSeenStatus(),
                            null,
                            chatRequestModelResult.getFirebaseChatId(),
                            fileUrl,
                            null,
                            AllStaticFields.sessionId
                    );
                    chatRequestModelList.add(chatRequestModel);
                    callUpdateChatApi(chatRequestModelList);
//                    dbRefConversations.child(chatRequestModelResult.getFirebaseChatId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                        }
//                    });
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        requestListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null)
                    return;

                if (snapshot.getValue() != null && snapshot.getValue().toString().equals("Accepted")) {
                    if (astrologerNotRespondingTimer != null) {
                        astrologerNotRespondingTimer.cancel();
                    }
                    RetrofitAPIService.getApiClient().getSessionIdForChat(userId, astrologerId).enqueue(new Callback<ChatSessionResponseModel>() {
                        @Override
                        public void onResponse(Call<ChatSessionResponseModel> call, Response<ChatSessionResponseModel> response) {

                            try {

                                if (response.isSuccessful() && response.body() != null) {

                                    if (response.body().getCode().equals("200")) {
                                        AllStaticFields.sessionId = response.body().getResult().getSessionId();
                                        binding.llConnect.setVisibility(View.GONE);
                                        binding.layoutToolbar.getRoot().setVisibility(View.VISIBLE);
                                        dbCallingRefStatus.removeValue();
                                        remainingSecond = Long.parseLong(response.body().getResult().getRemainingTime());
                                        binding.getRoot().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                callUserDataToAstrologer();
                                            }
                                        });
                                        startRemainingChatTimeTimer(remainingSecond);
//                                        if (AllStaticFields.minuteTime==0) {
//                                            startLowBalanceNotificationTimer(remainingSecond);
//                                        }
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
                        public void onFailure(Call<ChatSessionResponseModel> call, Throwable t) {

                        }
                    });

                } else if (snapshot.getValue() != null && snapshot.getValue().toString().equals("Rejected")) {
                    if (astrologerNotRespondingTimer != null) {
                        astrologerNotRespondingTimer.cancel();
                    }
                    dbCallingRefStatus.child("RequestToAstrologer").removeEventListener(requestListener);
                    dbCallingRefStatus.removeValue();
                    binding.CorrWrong.setVisibility(View.GONE);
                    astrologerNotRespondingChatDialog = new AstrologerNotRespondingChatDialog(context, new OnSubmitListeners() {
                        @Override
                        public void onSubmit(String value) {
                            startActivity(new Intent(context, NewDashboardActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancel(String value) {
                            startActivity(new Intent(context, NewDashboardActivity.class));
                            finish();
                        }
                    });
                    astrologerNotRespondingChatDialog.setCanceledOnTouchOutside(false);
                    astrologerNotRespondingChatDialog.show();

                } else if (snapshot.getValue() != null && snapshot.getValue().toString().equals("Terminated By Astrologer")) {
                    AllStaticFields.minuteTime = null;
                    Toast.makeText(context, "Your chat session was terminated by astrologer.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dbCallingRefStatus.child("RequestToAstrologer").addValueEventListener(requestListener);

        dbRefStatus.child(TYPING_STATUS).child("astrologer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(context, "hh", Toast.LENGTH_SHORT).show();
                if (snapshot.getValue() == null)
                    return;

                if ((Boolean) snapshot.getValue()) {
                    binding.llTypingStatus.setVisibility(View.VISIBLE);
                    scrollToBottom();
                } else {
                    binding.llTypingStatus.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.imgBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!binding.editMessage.getText().toString().trim().equals("")) {

                    String chatId = dbRefConversations.push().getKey();

                    ChatRequestModel chatRequestModel = new ChatRequestModel(
                            userId,
                            astrologerId,
                            binding.editMessage.getText().toString(),
                            true,
                            "0",
                            TEXT_MESSAGE,
                            chatId,
                            null,
                            null,
                            AllStaticFields.sessionId
                    );

                    View textMessageView = setSendMessageUI(SEEN_STATUS_PROCESS, chatRequestModel, null);

                    dbRefConversations.child(chatId).setValue(chatRequestModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                scrollToBottom();
                                callSaveChatApi(chatRequestModel, textMessageView);
                                //handle seen status UI
                                //call to save chat from api
                            }
                        }
                    });

                    binding.editMessage.setText("");

                }

            }
        });

        scrollToBottom();

        binding.editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!binding.editMessage.getText().toString().trim().equals(""))
                    updateTypingStatus();
            }
        });

        callGetChatApi();

        binding.layoutToolbar.btnChatEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEndChatSession();
                myProgressDialog.show();
            }
        });

    }

    private void getImageFromGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.setAction(Intent.ACTION_PICK);

        //  startActivity(Intent.createChooser(intent,"Select Picture"));

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

    }

    private void callUserDataToAstrologer() {
        String chatId = dbRefConversations.push().getKey();
        ChatRequestModel chatRequestModel = new ChatRequestModel(
                userId,
                astrologerId,
                "" + "User Name :  " + AllStaticFields.userData.getFirstName() + " "
                        + AllStaticFields.userData.getLastName() + "\n" +
                        "Gender : " + AllStaticMethods.getGenderNameByGenderId(AllStaticFields.userData.getGender()) + "\n" +
                        "Place Of Birth :  " + AllStaticFields.userData.getPlaceOfBirth() + "\n" +
                        "Date Of Birth :  " + AllStaticFields.userData.getDateOfBirth() + "\n" +
                        "Time Of Birth :  " + AllStaticFields.userData.getTimeOfBirth() + "\n" +
                        "Current City :  " + AllStaticFields.userData.getCurrentCityName() + "\n" +
                        "Problem Area :  " + AllStaticFields.userData.getProblemArea(),
                true,
                "0",
                TEXT_MESSAGE,
                chatId,
                null,
                null,
                AllStaticFields.sessionId
        );

        View textMessageView = setSendMessageUI(SEEN_STATUS_PROCESS, chatRequestModel, null);

        dbRefConversations.child(chatId).setValue(chatRequestModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (isChatRunning) {
                        isChatRunning = false;
                        callSaveChatApi(chatRequestModel, textMessageView);
                        scrollToBottom();
                    }
                }
            }
        });

        binding.editMessage.setText("");

    }

    private void callAstrologerDetailApi() {

        RetrofitAPIService.getApiClient().getAstrologerDetail(astrologerId).enqueue(new Callback<AstrologerDetailResponseModel>() {
            @Override
            public void onResponse(Call<AstrologerDetailResponseModel> call, Response<AstrologerDetailResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            binding.layoutToolbar.txtAstrologerName.setText(response.body().getResult().getFirstName() + " " + response.body().getResult().getLastName());
                            chargePerMinute = Integer.parseInt(response.body().getResult().getChargePerMinute());

                        }

                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                }

            }

            @Override
            public void onFailure(Call<AstrologerDetailResponseModel> call, Throwable t) {

            }
        });

    }

    private void checkChatSession() {

        RetrofitAPIService.getApiClient().getSessionIdForChat(userId, astrologerId).enqueue(new Callback<ChatSessionResponseModel>() {
            @Override
            public void onResponse(Call<ChatSessionResponseModel> call, Response<ChatSessionResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            AllStaticFields.sessionId = response.body().getResult().getSessionId();
                            remainingSecond = Long.parseLong(response.body().getResult().getRemainingTime());
                            AllStaticFields.astrologerId = astrologerId;
                            binding.llConnect.setVisibility(View.GONE);
                            binding.layoutToolbar.getRoot().setVisibility(View.VISIBLE);
                            scrollToBottom();
                            startRemainingChatTimeTimer(remainingSecond);
//                            if (AllStaticFields.minuteTime==0) {
//                                if (userLowBalanceTimer != null) {
//                                    userLowBalanceTimer.cancel();
//                                }
//                                startLowBalanceNotificationTimer(remainingSecond);
//                            }
                        }
                        if (response.body().getCode().equals("404")) {
                            checkUserBalance();
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

    @Override
    protected void onPause() {
        super.onPause();
        dbRefConversations.removeEventListener(conversationListener);
        if (AllStaticFields.sessionId == null) {
            if (astrologerNotRespondingTimer != null) {
                astrologerNotRespondingTimer.cancel();
            }
//            if (userLowBalanceTimer != null) {
//                userLowBalanceTimer.cancel();
//            }
            RequestForChatRequestModel requestForChatRequestModel = new RequestForChatRequestModel(AllStaticFields.userData.getUserId(), AllStaticFields.userData.getFirstName(), astrologerId, AppConstants.NOTIFICATION_TYPE_REQUEST_CANCEL_CHAT, true);
            requestForChat(requestForChatRequestModel);
            dbCallingRefStatus.child("RequestToAstrologer").removeEventListener(requestListener);
            dbCallingRefStatus.removeValue();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbRefConversations.addChildEventListener(conversationListener);
        checkChatSession();
    }

    private void callEndChatSession() {
        if (userLowBalanceTimer != null) {
            userLowBalanceTimer.cancel();
        }
        AllStaticFields.minuteTime = null;
        RetrofitAPIService.getApiClient().getFinishChatDetail(userId, astrologerId, AllStaticFields.sessionId).enqueue(new Callback<ChatEndResponseModel>() {
            @Override
            public void onResponse(Call<ChatEndResponseModel> call, Response<ChatEndResponseModel> response) {

                myProgressDialog.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            binding.llConnect.setVisibility(View.VISIBLE);
                            binding.layoutToolbar.getRoot().setVisibility(View.GONE);
                            binding.CorrWrong.setVisibility(View.GONE);
                            binding.imgWhiteScreen.setVisibility(View.VISIBLE);
                            AllStaticFields.sessionId = null;
                            ratingReviewDialog = new RatingReviewDialog(context, new OnSubmitListeners() {
                                @Override
                                public void onSubmit(String value) {
                                    startActivity(new Intent(context, NewDashboardActivity.class));
                                    finish();
                                }

                                @Override
                                public void onCancel(String value) {
                                    startActivity(new Intent(context, NewDashboardActivity.class));
                                    finish();
                                }
                            });
                            ratingReviewDialog.setCanceledOnTouchOutside(false);
                            ratingReviewDialog.show();

                        } else if (response.body().getCode().equals("400")) {
                            binding.llConnect.setVisibility(View.VISIBLE);
                            binding.layoutToolbar.getRoot().setVisibility(View.GONE);
                            binding.CorrWrong.setVisibility(View.GONE);
                            binding.imgWhiteScreen.setVisibility(View.VISIBLE);
                            AllStaticFields.sessionId = null;
                            ratingReviewDialog = new RatingReviewDialog(context, new OnSubmitListeners() {
                                @Override
                                public void onSubmit(String value) {

                                    startActivity(new Intent(context, NewDashboardActivity.class));
                                    finish();

                                }

                                @Override
                                public void onCancel(String value) {
                                    startActivity(new Intent(context, NewDashboardActivity.class));
                                    finish();
                                }
                            });
                            ratingReviewDialog.setCanceledOnTouchOutside(false);
                            ratingReviewDialog.show();

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
            public void onFailure(Call<ChatEndResponseModel> call, Throwable t) {
                myProgressDialog.dismiss();
            }
        });
    }

    private void callUpdateChatApi(List<ChatRequestModel> chatRequestModelList) {

        RetrofitAPIService.getApiClient().updateChat(chatRequestModelList).enqueue(new Callback<SaveChatResponseModel>() {
            @Override
            public void onResponse(Call<SaveChatResponseModel> call, Response<SaveChatResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {


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
            public void onFailure(Call<SaveChatResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callSaveChatApi(ChatRequestModel chatRequestModel, View textMessageView) {

        chatRequestModel.setSeenStatus("" + SEEN_STATUS_SENT);

        RetrofitAPIService.getApiClient().saveChat(chatRequestModel).enqueue(new Callback<SaveChatResponseModel>() {
            @Override
            public void onResponse(Call<SaveChatResponseModel> call, Response<SaveChatResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            ImageView imgSentStatus = textMessageView.findViewById(R.id.imgSentStatus);
                            imgSentStatus.setImageResource(R.drawable.ic_done);

                            dbRefConversations.child(chatRequestModel.getFirebaseChatId()).child("SeenStatus").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.getValue() != null) {
                                        ImageView imgSentStatus = textMessageView.findViewById(R.id.imgSentStatus);
                                        if (snapshot.getValue().toString().equals("" + SEEN_STATUS_READ)) {
                                            imgSentStatus.setImageResource(R.drawable.ic_seen);
                                        } else if (snapshot.getValue().toString().equals("" + SEEN_STATUS_DELIVERED)) {
                                            imgSentStatus.setImageResource(R.drawable.ic_delivered);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            dbRefConversations.child(chatRequestModel.getFirebaseChatId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                            ProgressBar progressBar = textMessageView.findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.GONE);
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
            public void onFailure(Call<SaveChatResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void callGetChatApi() {
        RetrofitAPIService.getApiClient().getChatList(userId, astrologerId).enqueue(new Callback<ChatListResponseModel>() {
            @Override
            public void onResponse(Call<ChatListResponseModel> call, Response<ChatListResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {

                            List<ChatRequestModel> chatRequestModelList = new ArrayList<>();
                            for (ChatRequestModel result : response.body().getResult()) {
                                if (!date.equals(result.getCreatedOn().substring(0, 10))) {
                                    date = result.getCreatedOn().substring(0, 10);
                                    chatGroupDivider(result.getCreatedOn());
                                }
                                if (result.getIsSentByUser()) {
                                    setSendMessageUI(Integer.parseInt(result.getSeenStatus()), result, null);
                                } else {
                                    setReceivedMessageUI(result);
                                }
                            }
                            scrollToBottom();

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
            public void onFailure(Call<ChatListResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkUserBalance() {
        RetrofitAPIService.getApiClient().getWallet(userId).enqueue(new Callback<WalletResponseModel>() {
            @Override
            public void onResponse(Call<WalletResponseModel> call, Response<WalletResponseModel> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            long totalAvailableMinute = 0, walletBalance = 0, finalTimeForChat = 0;
                            WalletResponseModel.Result result = response.body().getResult();

                            AllStaticFields.minuteTime = Long.parseLong(result.getMinuteAmount());

                            if (result.getMinuteAmount() != null && Long.parseLong(result.getMinuteAmount()) > 0) {
                                totalAvailableMinute = Long.parseLong(result.getMinuteAmount());
                            }
                            if (result.getRechargeAmount() != null && Double.parseDouble(result.getRechargeAmount()) > 0) {
                                double value = Double.parseDouble(result.getRechargeAmount());
                                walletBalance = (long) value;
                            }
                            if (totalAvailableMinute == 10) {
                                if (ValidForFree && Long.parseLong(RemainingFreeSession) > 0) {
                                    binding.llConnect.setVisibility(View.VISIBLE);
                                    RequestForChatRequestModel requestForChatRequestModel = new RequestForChatRequestModel(AllStaticFields.userData.getUserId(), AllStaticFields.userData.getFirstName(), astrologerId, AppConstants.NOTIFICATION_TYPE_REQUEST_CHAT, true);
                                    requestForChat(requestForChatRequestModel);
                                } else {
                                    finalTimeForChat = (walletBalance / chargePerMinute);
                                    if (finalTimeForChat >= 3) {
                                        binding.llConnect.setVisibility(View.VISIBLE);
                                        RequestForChatRequestModel requestForChatRequestModel = new RequestForChatRequestModel(AllStaticFields.userData.getUserId(), AllStaticFields.userData.getFirstName(), astrologerId, AppConstants.NOTIFICATION_TYPE_REQUEST_CHAT, false);
                                        requestForChat(requestForChatRequestModel);
                                    } else {
                                        binding.llConnect.setVisibility(View.VISIBLE);
                                        binding.layoutToolbar.getRoot().setVisibility(View.GONE);
                                        binding.CorrWrong.setVisibility(View.GONE);
                                        binding.imgWhiteScreen.setVisibility(View.VISIBLE);
                                        walletBalanceCheckDialog.setTitle("Wallet Balance is low");
                                        walletBalanceCheckDialog.setBody("Minimum 3 min balance is required");
                                        walletBalanceCheckDialog.setCancelable(false);
                                        walletBalanceCheckDialog.setCanceledOnTouchOutside(false);
                                        walletBalanceCheckDialog.show();
                                    }
                                }
                            } else {
                                finalTimeForChat = (walletBalance / chargePerMinute);
                                if (finalTimeForChat >= 3) {
                                    binding.llConnect.setVisibility(View.VISIBLE);
                                    RequestForChatRequestModel requestForChatRequestModel = new RequestForChatRequestModel(AllStaticFields.userData.getUserId(), AllStaticFields.userData.getFirstName(), astrologerId, AppConstants.NOTIFICATION_TYPE_REQUEST_CHAT, false);
                                    requestForChat(requestForChatRequestModel);
                                } else {
                                    binding.llConnect.setVisibility(View.VISIBLE);
                                    binding.layoutToolbar.getRoot().setVisibility(View.GONE);
                                    binding.CorrWrong.setVisibility(View.GONE);
                                    binding.imgWhiteScreen.setVisibility(View.VISIBLE);
                                    walletBalanceCheckDialog.setTitle("Wallet Balance is low");
                                    walletBalanceCheckDialog.setBody("Minimum 3 min balance is required");
                                    walletBalanceCheckDialog.setCancelable(false);
                                    walletBalanceCheckDialog.setCanceledOnTouchOutside(false);
                                    walletBalanceCheckDialog.show();
                                }
                            }
                        } else if (response.body().getCode().equals("404")) {
                            binding.llConnect.setVisibility(View.VISIBLE);
                            binding.layoutToolbar.getRoot().setVisibility(View.GONE);
                            binding.CorrWrong.setVisibility(View.GONE);
                            binding.imgWhiteScreen.setVisibility(View.VISIBLE);
                            walletBalanceCheckDialog.setTitle("Wallet Balance is low");
                            walletBalanceCheckDialog.setBody("Minimum 3 min balance is required");
                            walletBalanceCheckDialog.setCancelable(false);
                            walletBalanceCheckDialog.setCanceledOnTouchOutside(false);
                            walletBalanceCheckDialog.show();
                        }
                    } else {
                        Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (Exception e) {
                    AllStaticMethods.saveException(e);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<WalletResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void requestForChat(RequestForChatRequestModel requestForChatRequestModel) {

        RetrofitAPIService.getApiClient().requestForChat(requestForChatRequestModel).enqueue(new Callback<RequestForChatResponseModel>() {
            @Override
            public void onResponse(Call<RequestForChatResponseModel> call, Response<RequestForChatResponseModel> response) {

                try {
                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getCode().equals("200")) {
                            if (requestForChatRequestModel.getNotificationType().matches("RequestChat")) {
                                startAstrologerNotRespondingTimer();
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
            public void onFailure(Call<RequestForChatResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateTypingStatus() {

        if (countDownTimer != null)
            countDownTimer.cancel();

//        TypingStatusModel typingStatusModel = new TypingStatusModel(true, true);
        dbRefStatus.child(TYPING_STATUS).child("user").setValue(true);

        countDownTimer = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                countDownTimer = null;
                dbRefStatus.child(TYPING_STATUS).child("user").setValue(false);
            }

        };
        countDownTimer.start();
    }

    private void scrollToBottom() {
        binding.scrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private View setReceivedMessageUI(ChatRequestModel chatRequestModel) {

        View view = null;

        switch (chatRequestModel.getChatType()) {
            case TEXT_MESSAGE:
                view = loadTextChatReceived(Integer.parseInt(chatRequestModel.getSeenStatus()), chatRequestModel);
                break;
            case IMAGE_MESSAGE:
                view = loadChatImageReceived(Integer.parseInt(chatRequestModel.getSeenStatus()), chatRequestModel);
                break;
        }

        return view;
    }

    private View setSendMessageUI(int seen_status, ChatRequestModel chatRequestModel, String picturePathIMGLocal) {

        View view = null;

        switch (chatRequestModel.getChatType()) {
            case TEXT_MESSAGE:
                view = loadTextChatSent(seen_status, chatRequestModel);
                break;
            case IMAGE_MESSAGE:
                view = loadChatImageSent(seen_status, chatRequestModel, picturePathIMGLocal);
                break;
        }

        return view;
    }

    private View loadTextChatReceived(int seen_status, ChatRequestModel chatRequestModel) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_receiver, null);
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtTime = view.findViewById(R.id.txtTime);
//        ImageView imgSentStatus = view.findViewById(R.id.imgSentStatus);
        txtMessage.setText(chatRequestModel.getChatMessage());

        if (chatRequestModel.getCreatedOn() == null) {
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
        } else {
            try {

                Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
                txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //handle seen status UI
        switch (seen_status) {

        }

        binding.llList.addView(view);

        return view;
    }

    private View loadChatImageReceived(int seen_status, ChatRequestModel chatRequestModel) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_img_receiver, null);
        ImageView imgChat = view.findViewById(R.id.imgChat);
        TextView txtTime = view.findViewById(R.id.txtTime);
        Picasso.get().load(chatRequestModel.getFileUrl()).noFade().into(imgChat);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatImageDialog = new ChatImageDialog(context, chatRequestModel, new OnSubmitListeners() {
                    @Override
                    public void onSubmit(String value) {
                        finish();
                    }

                    @Override
                    public void onCancel(String value) {
                        finish();
                    }
                });
                chatImageDialog.show();
            }
        });

        if (chatRequestModel.getCreatedOn() == null) {
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
        } else {
            try {

                Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
                txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //hndle seen status UI
        switch (seen_status) {

        }

        binding.llList.addView(view);

        return view;
    }

    private View loadTextChatSent(int seen_status, ChatRequestModel chatRequestModel) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_sender, null);
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtTime = view.findViewById(R.id.txtTime);
        txtMessage.setText(chatRequestModel.getChatMessage());
        ImageView imgSentStatus = view.findViewById(R.id.imgSentStatus);

        if (chatRequestModel.getCreatedOn() == null) {
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
        } else {
            try {
                Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
                txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //handle seen status UI
        switch (seen_status) {
            case SEEN_STATUS_READ:
                imgSentStatus.setImageResource(R.drawable.ic_seen);
                break;
            case SEEN_STATUS_DELIVERED:
                imgSentStatus.setImageResource(R.drawable.ic_delivered);
                break;
            case SEEN_STATUS_SENT:
                imgSentStatus.setImageResource(R.drawable.ic_done);
                break;
        }

        binding.llList.addView(view);

        return view;
    }

    private View loadChatImageSent(int seen_status, ChatRequestModel chatRequestModel, String picturePathIMGLocal) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_img_sender, null);
        ImageView imgChat = view.findViewById(R.id.imageChat);
        TextView txtTime = view.findViewById(R.id.txtTime);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        if (picturePathIMGLocal != null) {
            progressBar.setVisibility(View.VISIBLE);
            Picasso.get().load(new File(picturePathIMGLocal)).noFade().into(imgChat);
        } else {
            Picasso.get().load(chatRequestModel.getFileUrl()).into(imgChat);
        }
        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatImageDialog = new ChatImageDialog(context, chatRequestModel, new OnSubmitListeners() {
                    @Override
                    public void onSubmit(String value) {
                        finish();
                    }

                    @Override
                    public void onCancel(String value) {
                        finish();
                    }
                });
                chatImageDialog.show();
            }
        });


        if (chatRequestModel.getCreatedOn() == null) {
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
        } else {
            try {
                Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
                txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ImageView imgSentStatus = view.findViewById(R.id.imgSentStatus);

        //handle seen status UI
        switch (seen_status) {
            case SEEN_STATUS_READ:
                imgSentStatus.setImageResource(R.drawable.ic_seen);
                break;
            case SEEN_STATUS_DELIVERED:
                imgSentStatus.setImageResource(R.drawable.ic_delivered);
                break;
            case SEEN_STATUS_SENT:
                imgSentStatus.setImageResource(R.drawable.ic_done);
                break;
        }


        binding.llList.addView(view);

        return view;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dbRefConversations.removeEventListener(conversationListener);
        if (AllStaticFields.sessionId == null) {
            if (astrologerNotRespondingTimer != null) {
                astrologerNotRespondingTimer.cancel();
            }
            if (userLowBalanceTimer != null) {
                userLowBalanceTimer.cancel();
            }
            RequestForChatRequestModel requestForChatRequestModel = new RequestForChatRequestModel(AllStaticFields.userData.getUserId(), AllStaticFields.userData.getFirstName(), astrologerId, AppConstants.NOTIFICATION_TYPE_REQUEST_CANCEL_CHAT, true);
            requestForChat(requestForChatRequestModel);
            dbCallingRefStatus.child("RequestToAstrologer").removeEventListener(requestListener);
            dbCallingRefStatus.removeValue();
        }
        finish();
    }

    public void startRemainingChatTimeTimer(long remainingSecond) {

        remainingChatTime = new CountDownTimer(remainingSecond * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished / 1000;
                long minute = (int) (second / 60);
                second = second % 60;
                String mntSec = minute + "Min" + ":" + second + "sec";
                binding.layoutToolbar.txtRemainingTime.setText(mntSec);
            }

            public void onFinish() {
                callEndChatSession();
            }

        };
        remainingChatTime.start();
    }

    public void startAstrologerNotRespondingTimer() {
        astrologerNotRespondingTimer = new CountDownTimer(95000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                binding.CorrWrong.setVisibility(View.GONE);
                dbCallingRefStatus.removeValue();
                RequestForChatRequestModel requestForChatRequestModel = new RequestForChatRequestModel(AllStaticFields.userData.getUserId(), AllStaticFields.userData.getFirstName(), astrologerId, AppConstants.NOTIFICATION_TYPE_REQUEST_CANCEL_CHAT, true);
                requestForChat(requestForChatRequestModel);
                astrologerNotRespondingChatDialog = new AstrologerNotRespondingChatDialog(context, new OnSubmitListeners() {
                    @Override
                    public void onSubmit(String value) {
                        startActivity(new Intent(context, NewDashboardActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancel(String value) {
                        startActivity(new Intent(context, NewDashboardActivity.class));
                        finish();
                    }
                });
                astrologerNotRespondingChatDialog.setCanceledOnTouchOutside(false);
                astrologerNotRespondingChatDialog.show();
            }
        };
        astrologerNotRespondingTimer.start();
    }

    public void startLowBalanceNotificationTimer(long remainingSecond) {
        remainingSecond = remainingSecond - 100;
        userLowBalanceTimer = new CountDownTimer(remainingSecond * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                walletBalanceCheckDialog = new WalletBalanceCheckDialog(context, new OnSubmitListeners() {
                    @Override
                    public void onSubmit(String value) {
                        startActivity(new Intent(context, WalletActivity.class));
                        walletBalanceCheckDialog.dismiss();
                        if (remainingChatTime != null) {
                            remainingChatTime.cancel();
                        }
                        finish();
                    }

                    @Override
                    public void onCancel(String value) {
                        walletBalanceCheckDialog.dismiss();
                    }
                });
                walletBalanceCheckDialog.setTitle("Wallet Balance is low");
                walletBalanceCheckDialog.setBody("Please Recharge to continue your chat");
                walletBalanceCheckDialog.setCanceledOnTouchOutside(false);
                walletBalanceCheckDialog.show();
            }
        };
        userLowBalanceTimer.start();
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Grant permission To access Gallery")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }

                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getImageFromGallery();

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private View chatGroupDivider(String date) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_calender, null);
        TextView txtDate = view.findViewById(R.id.txtDate);
        try {
            Date chatDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(date);
            txtDate.setText(new SimpleDateFormat("dd MMM yyyy").format(chatDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.llList.addView(view);
        return view;
    }

//    private void promptSpeechInput() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                getString(R.string.speech_prompt));
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getApplicationContext(),
//                    getString(R.string.speech_not_supported),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE)
        {

            if (resultCode == Activity.RESULT_OK)
            {
                if (data == null) {
                    Toast.makeText(this, "No image selected", Toast.LENGTH_LONG).show();
                    //Display an error
                    return;
                } else {

                    //Image Url Fetched
                    try {

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            ArrayList<Uri> mArrayUri = new ArrayList<>();
                            for (int i = 0; i < mClipData.getItemCount(); i++)
                            {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();
                                mArrayUri.add(uri);
                                // Get the cursor
                                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                                // Move to first row
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String imageEncoded = cursor.getString(columnIndex);
                                picturePathIMG = imageEncoded;

                                Bitmap bitmap = BitmapFactory.decodeFile(new File(picturePathIMG).getAbsolutePath());
                                if (bitmap != null) {
                                    if (bitmap.getHeight() > 1000 || bitmap.getWidth() > 1000) {
                                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getHeight() / 4, bitmap.getWidth() / 4, false);
                                    }
                                    bitmap = ImageUtils.checkImageRotation(picturePathIMG, bitmap);

//                                imgReceipt.setImageBitmap(bitmap);
                                }
                                cursor.close();

                            }
                        } else {
                            if (data.getData() != null)
                            {
                                picturePathIMG = AllStaticMethods.getRealPathFromURI(data.getData(), context);

                                Bitmap bitmap = BitmapFactory.decodeFile(new File(picturePathIMG).getAbsolutePath());
                                if (bitmap != null)
                                {

                                    if (bitmap.getHeight() > 1000 || bitmap.getWidth() > 1000) {
                                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getHeight() / 4, bitmap.getWidth() / 4, false);
                                    }
                                    bitmap = ImageUtils.checkImageRotation(picturePathIMG, bitmap);

//                                imgReceipt.setImageBitmap(bitmap);
                                }

                            }
                        }

                        String chatId = dbRefConversations.push().getKey();

                        ChatRequestModel chatRequestModel = new ChatRequestModel(
                                userId,
                                astrologerId,
                                "",
                                true,
                                "0",
                                IMAGE_MESSAGE,
                                chatId,
                                "",
                                null,
                                AllStaticFields.sessionId
                        );

                        View textMessageView = setSendMessageUI(SEEN_STATUS_PROCESS, chatRequestModel, picturePathIMG);

                        File file = new File(picturePathIMG);

                        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart("UserId", AllStaticFields.userData.getUserId())
                                .addFormDataPart("SessionId", AllStaticFields.sessionId)
                                .addFormDataPart("AstrologerId", astrologerId);

                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        builder.addFormDataPart("Attachments[]", file.getName(), requestFile);
                        RequestBody requestBody = builder.build();

                        RetrofitAPIService.getApiClient().saveChatImage(requestBody).enqueue(new Callback<SaveChatImageResponseModel>() {
                            @Override
                            public void onResponse(Call<SaveChatImageResponseModel> call, Response<SaveChatImageResponseModel> response) {

                                try {

                                    if (response.isSuccessful() && response.body() != null) {

                                        if (response.body().getCode().equals("200")) {

                                            attachments = response.body().getResult().getAttachments();
//                                        Toast.makeText(context, "Upload Success", Toast.LENGTH_SHORT).show();

                                            if (attachments != null && attachments.size() > 0) {

                                                chatRequestModel.setFileUrl(attachments.get(0));

                                                dbRefConversations.child(chatId).setValue(chatRequestModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    scrollToBottom();
                                                                    callSaveChatApi(chatRequestModel, textMessageView);
                                                                }
                                                            }
                                                        });
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
                            public void onFailure(Call<SaveChatImageResponseModel> call, Throwable t) {
//                                        binding.lottieProgressBar.setVisibility(View.GONE);
                                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(context, "unable to upload image", Toast.LENGTH_LONG).show();
            }

        }

//        if (requestCode == REQ_CODE_SPEECH_INPUT) {
//
//            if (resultCode == RESULT_OK) {
//
//                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                if (result.size() > 0) {
//                    binding.editMessage.append(result.get(0)); // set the input data to the editText alongside if want to.
//                }
//            }
//
//        }

    }

    @Override
    protected void onDestroy() {
//        FirebaseDatabase.getInstance().goOffline();
//        databaseReference.setValue("offline");
        super.onDestroy();
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