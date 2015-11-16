package com.example.divya.sepm;

import android.app.ActionBar;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divya.helpers.JSONParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Divya on 10/24/2015.
 */
public class AddNotification extends ActionBarActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {

    private Toolbar mToolbar;
    private String selectedDate, selectedTime;
    private Button datePicker, timePicker;
    private EditText edt;
    private ProgressDialog pDialog;
    private static final String ERROR_MSG = "Very very very long error message to get scrolling or multiline animation when the error button is clicked";
    private static final String[] ITEMS = {"Football", "Cricket", "Basketball", "Badminton", "Table Tennis", "Volleyball", "Squash",
            "Chess", "Carrom", "Karate", "Kick Boxing", "Taekwondo"};

    private ArrayAdapter<String> adapter;
    private boolean shown = false;

    //MaterialSpinner spinner1;
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnotification);


        mToolbar = (Toolbar) findViewById(R.id.actionbar_notification);
        setSupportActionBar(mToolbar);
        setTitle("Add notification");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt = (EditText) findViewById(R.id.add_notify_details);

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_row, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        initSpinnerHintAndFloatingLabel();

        datePicker = (Button) findViewById(R.id.date_pick);
        timePicker = (Button) findViewById(R.id.time_pick);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddNotification.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddNotification.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });


        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDate!= null && selectedTime!= null && spinner1.getSelectedItem() != null
                        && edt.getText() != null) {
                    new SubmitDetails(selectedDate.toString(), selectedTime.toString(), spinner1.getSelectedItem().toString(),
                            edt.getText().toString()).execute();
                }

            }
        });

    }

    private void initSpinnerHintAndFloatingLabel() {
        //spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
       // spinner1.setPaddingSafe(0, 0, 0, 0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                super.onBackPressed();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        selectedTime = hourOfDay + ":" + minute;
        timePicker.setText(selectedTime +" Tap to change");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        datePicker.setText(selectedDate +" Tap to change");

    }


    class SubmitDetails extends AsyncTask<String, String, String> {
        JSONParser jParser = new JSONParser();

        String date, time, sports, details;
        String status = null;
        SubmitDetails(String date, String time, String sports, String details) {
            this.date = date;
            this.time = time;
            this.sports = sports;
            this.details = details;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNotification.this);
            pDialog.setMessage("Adding Notifications");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            HashMap hms = new HashMap<String, String>();

            params.add(new BasicNameValuePair("addnotification", " "));
            params.add(new BasicNameValuePair("sport", sports));
            params.add(new BasicNameValuePair("details", details));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("time", time));


            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/addnotification.php", "POST", params);

            try {
                status = json.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("Add Notifications : ", json.toString());

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                     Toast.makeText(getApplicationContext(), "Notification added", Toast.LENGTH_SHORT).show();
                     //onBackPressed();
                    }

               // }
            });
        }
    }
}