package com.astroexpress.user.ui.activity;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astroexpress.user.R;
import com.astroexpress.user.api.RetrofitAPIService;
import com.astroexpress.user.databinding.ActivityChatBinding;
import com.astroexpress.user.databinding.ActivityChatHistoryBinding;
import com.astroexpress.user.databinding.ActivityChatHistoryDetailBinding;
import com.astroexpress.user.interfaces.OnSubmitListeners;
import com.astroexpress.user.model.request.ChatRequestModel;
import com.astroexpress.user.model.responsemodel.ChatEndResponseModel;
import com.astroexpress.user.model.responsemodel.ChatListResponseModel;
import com.astroexpress.user.ui.dialog.ChatImageDialog;
import com.astroexpress.user.ui.dialog.RatingReviewDialog;
import com.astroexpress.user.utility.AllStaticMethods;
import com.astroexpress.user.utility.AppConstants;
import com.astroexpress.user.utility.ImageUtils;
import com.astroexpress.user.utility.NetworkChange;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHistoryDetailActivity extends AppCompatActivity

{
    final int SEEN_STATUS_PROCESS = -1;
    final int SEEN_STATUS_SENT = 0;
    final int SEEN_STATUS_DELIVERED = 1;
    final int SEEN_STATUS_READ = 2;
    final String TEXT_MESSAGE = "TextMessage";
    final String TYPING_STATUS = "TypingStatus";
    final String FILE_MESSAGE = "FileMessage";
    final String IMAGE_MESSAGE = "ImageMessage";
    final String TERMINATE_STATUS = "TerminateStatus";
    private Chronometer chronometerTimer;
    private static final int PICK_IMAGE = 5;
    private String picturePathIMG, fileUrl = null;
    List<String> attachments;
    String currentDate;
    ChatImageDialog chatImageDialog;
    ActivityChatHistoryDetailBinding binding;
    Toolbar toolbar;
    Context context;
    String action;
    private String userId;
    private String astrologerId;
    NetworkChange networkChange = new NetworkChange();
    String date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatHistoryDetailBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        toolbar = binding.layoutToolbar.toolbar;
        toolbar.setTitle("Chat History");
        setSupportActionBar(toolbar);

        context = ChatHistoryDetailActivity.this;
        astrologerId = getIntent().getExtras().getString("astrologerId");
        userId = getIntent().getExtras().getString("userId");
        callGetChatApi();

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
                                    setSendMessageUI(Integer.parseInt(result.getSeenStatus()), result);
                                } else {
                                    setReceivedMessageUI(result);

                                }
                            }
                            scrollToBottom();
                            binding.lottieProgressBar.setVisibility(View.GONE);

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
            public void onFailure(Call<ChatListResponseModel> call, Throwable t) {
                Toast.makeText(context, AppConstants.TOAST_MESSAGES, Toast.LENGTH_SHORT).show();

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

    private View setSendMessageUI(int seen_status, ChatRequestModel chatRequestModel) {

        View view = null;

        switch (chatRequestModel.getChatType()) {
            case TEXT_MESSAGE:
                view = loadTextChatSent(seen_status, chatRequestModel);
                break;
            case IMAGE_MESSAGE:
                view = loadChatImageSent(seen_status, chatRequestModel);
                break;
        }

        return view;
    }

    private View loadChatImageSent(int seen_status, ChatRequestModel chatRequestModel){

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_img_sender, null);
        ImageView imgChat = view.findViewById(R.id.imageChat);
        TextView txtTime = view.findViewById(R.id.txtTime);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatImageDialog = new ChatImageDialog(context, chatRequestModel , new OnSubmitListeners() {
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

        Picasso.get().load(chatRequestModel.getFileUrl()).noFade().into(imgChat);
        try {

            Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

        } catch (ParseException e) {
            e.printStackTrace();
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

    private View loadChatImageReceived(int seen_status, ChatRequestModel chatRequestModel) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_img_receiver, null);
        ImageView imgChat = view.findViewById(R.id.imgChat);
        TextView txtTime = view.findViewById(R.id.txtTime);
        Picasso.get().load(chatRequestModel.getFileUrl()).noFade().into(imgChat);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatImageDialog = new ChatImageDialog(context, chatRequestModel , new OnSubmitListeners() {
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

        try {

            Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //handle seen status UI
        switch (seen_status) {

        }

        binding.llList.addView(view);

        return view;
    }

    private View loadTextChatReceived(int seen_status, ChatRequestModel chatRequestModel) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_receiver, null);
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtTime = view.findViewById(R.id.txtTime);

        txtMessage.setText(chatRequestModel.getChatMessage());
        try {

            Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //handle seen status UI
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
        try {

            Date chatTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").parse(chatRequestModel.getCreatedOn());
            txtTime.setText(new SimpleDateFormat("hh:mm a").format(chatTime));

        } catch (ParseException e) {
            e.printStackTrace();
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

            default:
                break;
        }

        return true;
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

    private void scrollToBottom() {
        binding.scrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
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