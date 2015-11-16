package com.example.divya.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.divya.sepm.AddApproval;
import com.example.divya.sepm.R;

/**
 * Created by Divya on 11/10/2015.
 */public class AlarmReceiver extends BroadcastReceiver {
    /* Receives scheduled Alarm intents */
    public void onReceive(Context context, Intent intent) {
        /* Show a success toast*/
        Log.d("jjj", "entered");
/*
        Toast.makeText(context, "Howdy partner", Toast.LENGTH_SHORT).show();
*/



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        Intent resultIntent = new Intent(context, AddApproval.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }


}