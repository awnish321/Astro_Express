package com.astroexpress.user.model.firebaseNotification;

import androidx.annotation.NonNull;

import com.astroexpress.user.utility.AllStaticFields;
import com.astroexpress.user.utility.MyNotificationManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        final String astrologerFollowerTopic = "/topics/FollowedAstrologerOnline_" + AllStaticFields.userData.getUserId();

        if (message.getFrom().equals(astrologerFollowerTopic)) {
            String response1 = new Gson().toJson(message.getData());
            FirebaseNotificationForFollowedAstrologerResponseModel firebaseNotificationForFollowedAstrologerResponseModel = new Gson().fromJson(response1, FirebaseNotificationForFollowedAstrologerResponseModel.class);
            MyNotificationManager.sendUserFollowedAstrologerNotification(getApplicationContext(), 101, firebaseNotificationForFollowedAstrologerResponseModel, message.getFrom().replace("/topics/", ""));

        }
        switch (message.getFrom()) {

            case "/topics/UserAll":
                String response = new Gson().toJson(message.getData());
                FirebaseNotificationForAllResponseModel firebaseNotificationForAllResponseModel = new Gson().fromJson(response, FirebaseNotificationForAllResponseModel.class);
                MyNotificationManager.sendUserAllNotification(getApplicationContext(), 100, firebaseNotificationForAllResponseModel, message.getFrom().replace("/topics/", ""));
                break;


        }

    }

}
