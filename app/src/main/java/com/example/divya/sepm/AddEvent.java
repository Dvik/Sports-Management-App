package com.example.divya.sepm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divya.helpers.AppConfig;
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

/**
 * Created by Divya on 11/8/2015.
 */
public class AddEvent extends ActionBarActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener
{
    private Toolbar mToolbar;
    ProgressDialog pDialog;
    EditText name,description,status,contactPerson,contactNo,winner;
    String selectedDate, selectedStartTime, selectedEndTime;
    Button datePicker,starttimePicker,endtimePicker;
    private static final String[] ITEMS = {"Football", "Cricket", "Basketball", "Badminton", "Table Tennis", "Volleyball", "Squash",
            "Chess", "Carrom", "Karate", "Kick Boxing", "Taekwondo"};

    private ArrayAdapter<String> adapter;
    Spinner spinner1;
    public static String subject,details,assigned,steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent);

        mToolbar = (Toolbar) findViewById(R.id.actionbar_notification);
        setSupportActionBar(mToolbar);
        setTitle("Add Event");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_row, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        initSpinnerHintAndFloatingLabel();

        name = (EditText) findViewById(R.id.add_event_name);
        description = (EditText) findViewById(R.id.add_event_description);
        status = (EditText) findViewById(R.id.add_event_status);
        contactPerson = (EditText) findViewById(R.id.add_event_contact_person);
        contactNo= (EditText) findViewById(R.id.add_event_contact_number);
        winner= (EditText) findViewById(R.id.add_event_winner);


        datePicker = (Button) findViewById(R.id.add_event_date);
        starttimePicker = (Button) findViewById(R.id.add_event_start_time);


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddEvent.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });



        starttimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddEvent.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

      /*  endtimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddEvent.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });*/


        Button btn = (Button) findViewById(R.id.add_event_place);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddEvent.this,AddEventPlace.class);
                startActivity(i);
            }
        });

        btn.setText("Change Place");

        Button btn1 = (Button) findViewById(R.id.submit);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SubmitEventDetails().execute();
            }
        });


    }

    private void initSpinnerHintAndFloatingLabel() {
        //spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
        spinner1 = (Spinner) findViewById(R.id.add_event_sports);

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
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {

        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        datePicker.setText(selectedDate);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {

        selectedStartTime = hourOfDay + ":" + minute;
        starttimePicker.setText(selectedStartTime);
    }



    class SubmitEventDetails extends AsyncTask<String, String, String> {
        JSONParser jParser = new JSONParser();



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddEvent.this);
            pDialog.setMessage("Adding Events");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            HashMap hms = new HashMap<String, String>();

            params.add(new BasicNameValuePair("addevent", " "));
            params.add(new BasicNameValuePair("name", name.getText().toString()));
            params.add(new BasicNameValuePair("date", selectedDate));
            params.add(new BasicNameValuePair("start_time", selectedStartTime));
            params.add(new BasicNameValuePair("end_time", "02:30"));
            params.add(new BasicNameValuePair("lat", String.valueOf(AppConfig.latitude)));
            params.add(new BasicNameValuePair("lng", String.valueOf(AppConfig.longitude)));
            params.add(new BasicNameValuePair("description", description.getText().toString()));
            params.add(new BasicNameValuePair("sport", spinner1.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("status", status.getText().toString()));
            params.add(new BasicNameValuePair("winner", winner.getText().toString()));
            params.add(new BasicNameValuePair("contactPerson", contactPerson.getText().toString()));
            params.add(new BasicNameValuePair("contactNo", contactNo.getText().toString()));



            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/addEvent.php", "POST", params);
            String s;
            try {
                s = json.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("Add Events : ", json.toString());

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Event added", Toast.LENGTH_SHORT).show();
                    //onBackPressed();
                }

                // }
            });
        }
    }
}
