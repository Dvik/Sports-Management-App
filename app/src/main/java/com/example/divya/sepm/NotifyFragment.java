package com.example.divya.sepm;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.divya.helpers.AppConfig;
import com.example.divya.helpers.CustomNotificationAdapter;
import com.example.divya.helpers.JSONParser;
import com.example.divya.helpers.SQLiteHandler;
import com.example.divya.models.Notify;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirections;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Divya on 10/22/2015.
 */

public class NotifyFragment extends Fragment {

    protected SwipeActionAdapter mAdapter;
    private ListView lv;
    ProgressDialog pDialog;
    private FloatingActionButton fab;
    private JSONParser jParser;
    private JSONArray notifications;
    SQLiteHandler db;
    private CustomNotificationAdapter stringAdapter;

    //private SQLiteHandler db;
    List<Notify> notificationslist = new ArrayList<Notify>();

    public NotifyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.notify, container, false);
        db = new SQLiteHandler(getActivity().getApplicationContext());
        lv = (ListView) v.findViewById(R.id.listView);
        //db = new SQLiteHandler(getActivity().getApplicationContext());
        jParser = new JSONParser();

        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        HashMap<String,String> mp = db.getUserDetails();
        if(!mp.get("roll").equals("y13uc094"))
            fab.setVisibility(View.INVISIBLE);
        else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(getActivity(), AddNotification.class);
                    getActivity().startActivity(myIntent);
                }
            });
        }

       // if(db.getNotificationRowCount() == 0) {
            new LoadAllNotifications().execute();
       /* }
        else
        {
         notificationslist = db.getNotifyDetails();

         workOnListView();
        }
*/

        return v;
    }


    class LoadAllNotifications extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Notifications");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/getAllNotifications.php", "GET", params);


            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {

                    notifications = json.getJSONArray("notifications");

                    for (int i = 0; i < notifications.length(); i++)
                    {
                        JSONObject c = notifications.getJSONObject(i);

                        Integer nid = c.getInt("nid");
                        String sport = c.getString("sport");
                        String details = c.getString("details");
                        String date = c.getString("date");
                        String time = c.getString("time");

                        notificationslist.add(new Notify(String.valueOf(nid),sport,details,date,time,true));
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

            getActivity().runOnUiThread(new Runnable(){
                public void run() {

                    workOnListView();

                }
            });

 }
 }

    public void workOnListView()
    {
        stringAdapter = new CustomNotificationAdapter(getActivity(), R.layout.row_bg, notificationslist );
        /*mAdapter = new SwipeActionAdapter(stringAdapter);
        mAdapter.setListView(lv);*/
        lv.setAdapter(stringAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),"clicked",Toast.LENGTH_SHORT);
                Notify n = stringAdapter.getItem(position);
                Intent i = new Intent(getActivity(),NotifyDetails.class);
                i.putExtra("getClickedNotification",n);
                startActivity(i);

            }
        });


       /* mAdapter.addBackground(SwipeDirections.DIRECTION_FAR_LEFT,R.layout.row_bg_left_far)
                .addBackground(SwipeDirections.DIRECTION_NORMAL_LEFT,R.layout.row_bg_left)
                .addBackground(SwipeDirections.DIRECTION_FAR_RIGHT,R.layout.row_bg_right_far)
                .addBackground(SwipeDirections.DIRECTION_NORMAL_RIGHT,R.layout.row_bg_right);


        mAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {

            @Override
            public boolean hasActions(int position)
            {
                return true;
            }

            @Override
            public boolean shouldDismiss(int position, int direction) {
                if(direction == SwipeDirections.DIRECTION_FAR_LEFT)
                {
                    db.deleteThisNotification(stringAdapter.getItem(position).id);
                    stringAdapter.remove(stringAdapter.getItem(position));

                    return true;
                }
                else
                    return false;

            }

            @Override
            public void onSwipe(int[] positionList, int[] directionList)
            {
                for (int i = 0; i < positionList.length; i++)
                {
                    int direction = directionList[i];
                    int position = positionList[i];
                    String dir = "";

                    switch (direction)
                    {

                        case SwipeDirections.DIRECTION_FAR_LEFT:
                            dir = "Far Left";
                            break;
                        case SwipeDirections.DIRECTION_FAR_RIGHT:
                            dir = "Far right";
                            break;

                    }

                    mAdapter.notifyDataSetChanged();
                }
            }
        });
*/

    }


}
