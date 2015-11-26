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
import com.example.divya.helpers.CustomInventoryAdapter;
import com.example.divya.helpers.CustomNotificationAdapter;
import com.example.divya.helpers.JSONParser;
import com.example.divya.helpers.RecyclerItemClickListener;
import com.example.divya.helpers.SQLiteHandler;
import com.example.divya.models.Inventory;
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
public class InventoryFragment extends Fragment {

    FloatingActionButton fab1;
    CustomInventoryAdapter adapter;
    RecyclerView rv;
    JSONParser jParser;
    ProgressDialog pDialog;
    SQLiteHandler db;
    ArrayList<Inventory> Inventoryslist;
    public InventoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.inventory, container, false);

        db = new SQLiteHandler(getActivity().getApplicationContext());
        rv = (RecyclerView)rootView.findViewById(R.id.rv_inventory);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);

        jParser = new JSONParser();

        Inventoryslist = new ArrayList<Inventory>();
        fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_inventory);

        HashMap<String,String> mp = db.getUserDetails();
        if(!mp.get("roll").equals("y13uc094"))
            fab1.setVisibility(View.INVISIBLE);
        else {
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(getActivity(), AddInventory.class);
                    getActivity().startActivity(myIntent);
                }
            });
        }
        new LoadAllInventorys().execute();



        return rootView;

    }
    class LoadAllInventorys extends AsyncTask<String, String, String> {
        JSONArray Inventorys;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Inventorys");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/getAllInventory.php", "GET", params);


            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {

                    Inventorys = json.getJSONArray("inventory");

                    for (int i = 0; i < Inventorys.length(); i++)
                    {
                        JSONObject c = Inventorys.getJSONObject(i);

                        Integer iid = c.getInt("iid");
                        String sport = c.getString("sport");
                        String equipment = c.getString("equipment");
                        String available = c.getString("available");
                        String damaged = c.getString("damaged");
                        String manager = c.getString("manager");

                        Inventoryslist.add(new Inventory(String.valueOf(iid),sport,equipment,available,damaged,manager));

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
        adapter = new CustomInventoryAdapter(Inventoryslist);
        rv.setAdapter(adapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main , menu);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

                return super.onOptionsItemSelected(item);

    }


}