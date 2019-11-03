package com.eazy.wegmansapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
public class DiscountNotification extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("CH1", "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            //channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel("CH1", "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            //channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

    public void createInstanceNoti(){

    }
}
