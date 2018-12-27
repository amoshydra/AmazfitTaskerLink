package com.amoshydra.app.AmazfitTaskerLink;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

/**
 * Created by amosh on 12/28/2018.
 */

public class TransporterService extends Service {
    private TaskerTransporter taskerTransporter;
    private IBinder mBinder = new TransporterServiceBinder();

    public class TransporterServiceBinder extends Binder {
        TransporterService getService() {
            // Return this instance of LocalService so clients can call public methods
            return TransporterService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int notificationId = 1;
        String channelId = "foreground_service";
        CharSequence name = getString(R.string.channel_name);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notif))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setChannelId(channelId)
                .build();
        notificationManager.notify(notificationId, notification);
        startForeground(notificationId, notification);

        taskerTransporter = new TaskerTransporter(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void sendAction(String actionName) {
        taskerTransporter.sendAction(actionName);
    }
}
