package com.example.divya.sepm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divya.helpers.CustomNotificationAdapter;
import com.example.divya.helpers.CustomFixturesAdapter;
import com.example.divya.helpers.JSONParser;
import com.example.divya.models.Event;
import com.example.divya.models.Fixtures;
import com.example.divya.models.Notify;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Divya on 11/9/2015.
 */
public class EventDetails extends ActionBarActivity {

    private Toolbar mToolbar;
    ListView lv;
    Integer selectedEid;
    ProgressDialog pDialog;
    JSONArray fixtures;
    CustomFixturesAdapter fixturesAdapter;


    ArrayList<Fixtures> fixtureslist;
    JSONParser jParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdetails);


        mToolbar = (Toolbar) findViewById(R.id.event_details_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Event Details");
        lv = (ListView) findViewById(R.id.listview_fixtures);
        selectedEid = new Integer('0');
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fixtureslist = new ArrayList<Fixtures>();

        jParser = new JSONParser();
        Bundle b = getIntent().getExtras();

        Event n = b.getParcelable("getEventDetails");

        TextView name_tv = (TextView) findViewById(R.id.event_detail_name);
        TextView descr_tv = (TextView) findViewById(R.id.event_detail_descr);
        TextView status_tv = (TextView) findViewById(R.id.event_detail_status);
        TextView date_tv = (TextView) findViewById(R.id.event_detail_date);


        name_tv.setText(n.name);
        descr_tv.setText(n.description);
        status_tv.setText(n.status);
        date_tv.setText(n.date);


       selectedEid = Integer.parseInt(n.eid);


        Log.d("ddd",n.name+" "+n.description);

        new LoadAllFixtures().execute();

    }



    class LoadAllFixtures extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EventDetails.this);
            pDialog.setMessage("Loading Event Description");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("fixture"," "));

            params.add(new BasicNameValuePair("eid",String.valueOf(selectedEid)));

            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/getFixtureOfAnEvent.php", "POST", params);

            Log.d("sss", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {

                    fixtures = json.getJSONArray("fixtures");

                    for (int i = 0; i < fixtures.length(); i++)
                    {
                        JSONObject c = fixtures.getJSONObject(i);

                        Integer fid = c.getInt("fid");
                        Integer eid = c.getInt("eid");
                        String team1 = c.getString("team1");
                        String team2 = c.getString("team2");
                        String date = c.getString("date");
                        String time = c.getString("time");
                        String status = c.getString("status");
                        String winner = c.getString("winner");

                        fixtureslist.add(new Fixtures(String.valueOf(fid),String.valueOf(eid),team1,team2,date,time,status,winner));
                        //    db.addNotification(String.valueOf(nid),sport,details,date,time,true);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {

                    workOnListView();

                }
            });

        }
    }

    public void workOnListView()
    {
        fixturesAdapter = new CustomFixturesAdapter(EventDetails.this, R.layout.event_list_fixture_row, fixtureslist );

        lv.setAdapter(fixturesAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
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
