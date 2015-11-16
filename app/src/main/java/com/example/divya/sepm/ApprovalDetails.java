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
import com.example.divya.helpers.CustomStepsAdapter;
import com.example.divya.helpers.JSONParser;
import com.example.divya.models.Approval;
import com.example.divya.models.Notify;
import com.example.divya.models.Steps;

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
public class ApprovalDetails extends ActionBarActivity {

    private Toolbar mToolbar;
    ListView lv;
    Integer selectedAid;
    ProgressDialog pDialog;
    JSONArray steps;
    CustomStepsAdapter stepsAdapter;


    ArrayList<Steps> stepslist;
    JSONParser jParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approvaldetails);


        mToolbar = (Toolbar) findViewById(R.id.appr_details_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Approval Details");
        lv = (ListView) findViewById(R.id.listview_steps);
        selectedAid = new Integer('0');
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stepslist = new ArrayList<Steps>();

        jParser = new JSONParser();
        Bundle b = getIntent().getExtras();

        Approval n = b.getParcelable("getApprovalDetails");

        TextView subject_tv = (TextView) findViewById(R.id.appr_detail_subject);
        TextView assigned_tv = (TextView) findViewById(R.id.appr_detail_assigned);
        TextView date_tv = (TextView) findViewById(R.id.appr_detail_date);

        subject_tv.setText(n.subject);
        assigned_tv.setText("Assigned to: "+n.assigned);
        date_tv.setText(n.deadline);
        selectedAid = Integer.parseInt(n.aid);

        new LoadAllSteps().execute();

    }



    class LoadAllSteps extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ApprovalDetails.this);
            pDialog.setMessage("Loading Approval Description");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("step"," "));

            params.add(new BasicNameValuePair("aid",String.valueOf(selectedAid)));

            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/getAllSteps.php", "POST", params);

            Log.d("sss", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {

                    steps = json.getJSONArray("steps");

                    for (int i = 0; i < steps.length(); i++)
                    {
                        JSONObject c = steps.getJSONObject(i);

                        Integer aid = c.getInt("aid");
                        Integer sid = c.getInt("sid");
                        String priority = c.getString("priority");
                        String place = c.getString("place");

                        stepslist.add(new Steps(String.valueOf(aid),String.valueOf(sid),priority,place));
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
        stepsAdapter = new CustomStepsAdapter(ApprovalDetails.this, R.layout.approval_list_steps_row, stepslist );

        lv.setAdapter(stepsAdapter);

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
