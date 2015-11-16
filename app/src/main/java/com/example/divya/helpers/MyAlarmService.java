package com.example.divya.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.divya.sepm.AddApproval;
import com.example.divya.sepm.NotifyDetails;
import com.example.divya.sepm.R;

/**
 * Created by Divya on 11/10/2015.
 */

public class MyAlarmService extends Service
{

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

/*
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NotifyDetails.this)
                        .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(NotifyDetails.this, AddApproval.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotifyDetails.this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NotifyDetails.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }
});*/

/*
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),NotifyDetails.class);

        //Notification notification = new Notification(R.drawable.ic_autorenew_black_24dp,"This is a test message!", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(MyAlarmService.this);
        builder.setSmallIcon(R.drawable. notification_template_icon_bg)
                .setContentTitle("ContentTitle").setContentIntent(pendingNotificationIntent);
        Notification notification = builder.getNotification();
        mManager.notify(R.drawable.notification_template_icon_bg, notification);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        mManager.notify(R.drawable.notification_template_icon_bg, notification);*/
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}