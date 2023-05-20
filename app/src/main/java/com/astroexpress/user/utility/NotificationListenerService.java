package com.astroexpress.user.utility;

import static android.app.Service.START_STICKY;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationListenerService extends Service {

    private static final String CHANNEL_ID = "Notify";
    DatabaseReference databaseReference;
    DatabaseReference reference;
    String userId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotification();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Online for Chat").setPriority(Notification.PRIORITY_MIN).build();
        startForeground(001100, notification);

        return START_STICKY;
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Foreground Notification", NotificationManager.IMPORTANCE_NONE);
            channel.setDescription("Online for Chat");
            channel.setName("Online for Chat");
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }
}

