package com.example.divya.sepm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
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
        ImageView img = (ImageView) findViewById(R.id.prof_img);

        sp_tv.setText(n.sports);
        de_tv.setText(n.content);
        da_tv.setText(n.date);
        ti_tv.setText(n.time);

        if(n.sports.equalsIgnoreCase("Football")) {
            img.setImageResource(R.drawable.football);
        }

        if(n.sports.equalsIgnoreCase("Cricket"))
        {
           img.setImageResource(R.drawable.basketball);
        }

        if(n.sports.equalsIgnoreCase("basketball"))
        {
            img.setImageResource(R.drawable.iconspo);
        }

        if(n.sports.equalsIgnoreCase("table tennis"))
        {
            img.setImageResource(R.drawable.tabletennis);
        }

        if(n.sports.equalsIgnoreCase("boxing"))
        {
            img.setImageResource(R.drawable.boxing);
        }

        Date today = new Date();
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("IST"));
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);

        Log.d("ddhhhhd", String.valueOf(calendar.getTimeInMillis()));

        Button bbb= (Button) findViewById(R.id.button_demo);
        bbb.setText("Set Reminder");
        bbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scheduleAlarm();

            }
        });

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
