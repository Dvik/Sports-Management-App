package com.example.divya.sepm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.divya.helpers.AlarmReceiver;
import com.example.divya.helpers.MyReceiver;
import com.example.divya.models.Notify;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class NotifyDetails extends ActionBarActivity {

    private Toolbar mToolbar;
    private AlarmManager am;
    Calendar calendar;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationdetails);


        mToolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Notification Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();

        Notify n = b.getParcelable("getClickedNotification");

        TextView sp_tv = (TextView) findViewById(R.id.notf_detail_sport_tv);
        TextView de_tv = (TextView) findViewById(R.id.notf_detail_details_tv);
        TextView da_tv = (TextView) findViewById(R.id.notf_detail_date_tv);
        TextView ti_tv = (TextView) findViewById(R.id.notf_detail_time_tv);

        sp_tv.setText(n.sports);
        de_tv.setText(n.content);
        da_tv.setText(n.date);
        ti_tv.setText(n.time);
        Date today = new Date();
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("IST"));
        calendar.set(Calendar.DAY_OF_MONTH, 11);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 8);
        calendar.set(Calendar.SECOND, 0);

        Log.d("ddhhhhd", String.valueOf(calendar.getTimeInMillis()));

        Button bbb= (Button) findViewById(R.id.button_demo);
        bbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scheduleAlarm();
            }
        });
/*
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.DAY_OF_MONTH, 10);

        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 52);
        calendar.set(Calendar.SECOND, 0);

        Intent myIntent = new Intent(NotifyDetails.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(NotifyDetails.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);*/
    }


   private void scheduleAlarm() {
        /* Request the AlarmManager object */
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Create the PendingIntent that will launch the BroadcastReceiver */
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0);

        /* Schedule Alarm with and authorize to WakeUp the device during sleep */
/*
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, pending);
*/
       Log.d("ddd", String.valueOf(calendar.getTimeInMillis()));
       manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);


   }

    private void cancelAlarm() {
        /* Request the AlarmManager object */
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Create the PendingIntent that would have launched the BroadcastReceiver */
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0);

        /* Cancel the alarm associated with that PendingIntent */
        manager.cancel(pending);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_not_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                super.onBackPressed();
                return true;


            case R.id.action_delete:
                super.onBackPressed();
                return true;



            case R.id.action_edit:
                super.onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
