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
public class AddInventory extends ActionBarActivity {
    private Toolbar mToolbar;
    ProgressDialog pDialog;
    EditText equipment,manager,available,damaged;
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
        setContentView(R.layout.addinventory);

        mToolbar = (Toolbar) findViewById(R.id.actionbar_notification);
        setSupportActionBar(mToolbar);
        setTitle("Add Inventory");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_row, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        initSpinnerHintAndFloatingLabel();

        equipment = (EditText) findViewById(R.id.add_inventory_equipment);
        manager = (EditText) findViewById(R.id.add_inventory_manager);
        available = (EditText) findViewById(R.id.add_inventory_available);
        damaged= (EditText) findViewById(R.id.add_inventory_damaged);


        Button btn1 = (Button) findViewById(R.id.submit_inventory);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SubmitInventoryDetails(spinner1.getSelectedItem().toString(),equipment.getText().toString(),
                manager.getText().toString(),available.getText().toString(),damaged.getText().toString()).execute();
            }
        });


    }

    private void initSpinnerHintAndFloatingLabel() {
        //spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
        spinner1 = (Spinner) findViewById(R.id.add_inventory_sports);

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




    class SubmitInventoryDetails extends AsyncTask<String, String, String> {
        JSONParser jParser = new JSONParser();
        String sports;String equipment;String manager;String available;String damaged;

        SubmitInventoryDetails(String sports, String equipment, String manager, String available, String damaged)
        {
            this.sports = sports;
            this.equipment = equipment;
            this.manager = manager;
            this.available = available;
            this.damaged = damaged;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddInventory.this);
            pDialog.setMessage("Adding Inventorys");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            HashMap hms = new HashMap<String, String>();

            params.add(new BasicNameValuePair("addinventory", " "));
            params.add(new BasicNameValuePair("sport", sports));
            params.add(new BasicNameValuePair("equipment", equipment));
            params.add(new BasicNameValuePair("available", available));
            params.add(new BasicNameValuePair("damaged", damaged));
            params.add(new BasicNameValuePair("manager", manager));



            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/addinventory.php", "POST", params);
            String s;
            try {
                s = json.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("Add Inventorys : ", json.toString());

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Inventory added", Toast.LENGTH_SHORT).show();
                    //onBackPressed();
                }

                // }
            });
        }
    }
}
