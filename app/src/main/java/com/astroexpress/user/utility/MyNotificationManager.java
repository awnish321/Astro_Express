package com.astroexpress.user.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.astroexpress.user.R;
import com.astroexpress.user.model.firebaseNotification.FirebaseNotificationForAllResponseModel;
import com.astroexpress.user.model.firebaseNotification.FirebaseNotificationForFollowedAstrologerResponseModel;
import com.astroexpress.user.ui.activity.NewDashboardActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MyNotificationManager {


    public static void sendUserAllNotification(Context context, int channelId, FirebaseNotificationForAllResponseModel firebaseNotificationForAllResponseModel, String topic) {

        AllStaticFields.firebaseNotificationForAllResponseModel = firebaseNotificationForAllResponseModel;

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel("My notification","My notification",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager =context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Bitmap myBitmap=null;
        if (!firebaseNotificationForAllResponseModel.getImageUrl().equals(""))
        {
        InputStream input = null;
        try {
            input = new URL(firebaseNotificationForAllResponseModel.getImageUrl()).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myBitmap = BitmapFactory.decodeStream(input);
        }

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intentAccept = new Intent(context, NewDashboardActivity.class);
        intentAccept.putExtra("IsFromNotification", true);
        intentAccept.putExtra("Topic", topic);
        intentAccept.putExtra("channelId", channelId);
        PendingIntent pendingIntentAccept = PendingIntent.getActivity(context, 0, intentAccept, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context,"My notification");

        b.setContentIntent(pendingIntentAccept);
        b.setContentTitle(firebaseNotificationForAllResponseModel.getTitle());
        b.setContentText(firebaseNotificationForAllResponseModel.getDescription());
        b.setSmallIcon(R.drawable.logo);
        b.setAutoCancel(true);
        b.setSound(alarmSound);
        if (myBitmap!=null)
        {
        b.setLargeIcon(myBitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap)
                        .bigLargeIcon(null));
        }
        b.setPriority(Notification.PRIORITY_MAX);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(channelId, b.build());


    }
    public static void sendUserFollowedAstrologerNotification(Context context, int channelId, FirebaseNotificationForFollowedAstrologerResponseModel firebaseNotificationForFollowedAstrologerResponseModel, String topic) {

        AllStaticFields.firebaseNotificationForFollowedAstrologerResponseModel = firebaseNotificationForFollowedAstrologerResponseModel;

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel("My notification for Followed Astrologer","My notification for Followed Astrologer",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager =context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Bitmap myBitmap=null;
        if (!firebaseNotificationForFollowedAstrologerResponseModel.getImageUrl().equals(""))
        {
        InputStream input = null;
        try {
            input = new URL(firebaseNotificationForFollowedAstrologerResponseModel.getImageUrl()).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myBitmap = BitmapFactory.decodeStream(input);
        }

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intentAccept = new Intent(context, NewDashboardActivity.class);
        intentAccept.putExtra("IsFromNotification", true);
        intentAccept.putExtra("Topic", topic);
        intentAccept.putExtra("channelId", channelId);
        PendingIntent pendingIntentAccept = PendingIntent.getActivity(context, 0, intentAccept, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context,"My notification for Followed Astrologer");

        b.setContentIntent(pendingIntentAccept);
        b.setContentTitle(firebaseNotificationForFollowedAstrologerResponseModel.getTitle());
        b.setContentText(firebaseNotificationForFollowedAstrologerResponseModel.getDescription());
        b.setSmallIcon(R.drawable.logo);
        b.setAutoCancel(true);
        b.setSound(alarmSound);
        if (myBitmap!=null)
        {
        b.setLargeIcon(myBitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap)
                        .bigLargeIcon(null));
        }
        b.setPriority(Notification.PRIORITY_MAX);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(channelId, b.build());

    }

    public static void cancelNotification(Context context,int id){

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.cancel(id);

    }

}
