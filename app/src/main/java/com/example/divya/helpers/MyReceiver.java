package com.example.divya.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Divya on 11/10/2015.
 */
    public class MyReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Intent service1 = new Intent(context, MyAlarmService.class);
            context.startService(service1);

        }
    }
