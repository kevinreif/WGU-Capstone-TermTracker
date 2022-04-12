package com.c196.termtracker.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.c196.termtracker.R;

public class MyReceiver extends BroadcastReceiver {
    static int notificationID = 0;
    String channelID = "test";
    private int importance;


    @Override
    public void onReceive(Context context, Intent intent) {
        notificationID++;
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channelID);

        /*Notification notification = new Notification.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(channelID)
                .setContentTitle("Test notification with an id of: " + Integer.toString(notificationID))
                .setContentText("This is a test").build();*/

        Notification notification = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("key")).build();




        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);


        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createNotificationChannel(Context context, String channelID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService((NotificationManager.class));
            notificationManager.createNotificationChannel(channel);
        }
    }
}