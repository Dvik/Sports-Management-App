package com.example.divya.sepm;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.divya.helpers.AppConfig;
import com.example.divya.helpers.CustomEventAdapter;
import com.example.divya.helpers.CustomNotificationAdapter;
import com.example.divya.helpers.JSONParser;
import com.example.divya.helpers.RecyclerItemClickListener;
import com.example.divya.helpers.SQLiteHandler;
import com.example.divya.models.Event;
import com.example.divya.models.Notify;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Divya on 10/22/2015.
 */
public class EventFragment extends Fragment {

    FloatingActionButton fab1;
    CustomEventAdapter adapter;
    RecyclerView rv;
    JSONParser jParser;
    SQLiteHandler db;
    ProgressDialog pDialog;
    ArrayList<Event> eventslist;
    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.event, container, false);

        db = new SQLiteHandler(getActivity().getApplicationContext());

        rv = (RecyclerView)rootView.findViewById(R.id.rv_event);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);

        jParser = new JSONParser();

        eventslist = new ArrayList<Event>();
        fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_event);

        HashMap<String,String> mp = db.getUserDetails();
        if(!mp.get("roll").equals("y13uc094"))
            fab1.setVisibility(View.INVISIBLE);
        else {
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(getActivity(), AddEvent.class);
                    getActivity().startActivity(myIntent);
                }
            });
        }
        new LoadAllEvents().execute();

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Event n = eventslist.get(position);
                        Intent i = new Intent(getActivity(),EventDetails.class);
                        i.putExtra("getEventDetails",n);
                        Log.d("ddd", n.eid + " " + n.name);

                        startActivity(i);
                    }
                })
        );


        return rootView;

    }
    class LoadAllEvents extends AsyncTask<String, String, String> {
        JSONArray events;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Events");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/getAllEvents.php", "GET", params);


            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {

                    events = json.getJSONArray("event");

                    for (int i = 0; i < events.length(); i++)
                    {
                        JSONObject c = events.getJSONObject(i);

                        Integer eid = c.getInt("eid");
                        String name = c.getString("name");
                        String date = c.getString("date");
                        String start_time = c.getString("start_time");
                        String end_time = c.getString("end_time");
                        String lat = c.getString("lat");
                        String lng = c.getString("lng");
                        String description = c.getString("description");
                        String sport = c.getString("sport");
                        String status = c.getString("status");
                        String winner = c.getString("winner");
                        String contactPerson = c.getString("contactPerson");
                        String contactNo = c.getString("contactNo");

                        eventslist.add(new Event(String.valueOf(eid),name,date,start_time,end_time,lat,lng,description,sport,status,winner,contactPerson,contactNo));

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

                    AppConfig.listOfEvents = eventslist;
                    workOnListView();


                }
            });

        }
    }

    public void workOnListView()
    {
        adapter = new CustomEventAdapter(eventslist);
        rv.setAdapter(adapter);
    }


   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       inflater.inflate(R.menu.menu_event , menu);


       super.onCreateOptionsMenu(menu, inflater);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case R.id.menuSortNewest:
                Collections.sort(eventslist, SortByNew);

                adapter = new CustomEventAdapter(eventslist);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.action_map:
                Intent i = new Intent(getActivity(),EventOnMapActivity.class);

                startActivity(i);

                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Comparator<Event> SortByNew = new Comparator<Event>() {

        @Override
        public int compare(Event event1, Event event2) {
            try{

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");


                String str1 = event1.date+" "+event1.start_time;
                Date date1 = formatter.parse(str1);

                String str2 = event2.date+" "+event2.start_time;
                Date date2 = formatter.parse(str2);

                if (date1.compareTo(date2)<0)
                {
                    return 1;
                }

            }catch (ParseException e1){
                e1.printStackTrace();
            }

            return 0;
        }
    };
}