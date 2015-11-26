package com.example.divya.sepm;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.divya.helpers.CustomApprovalAdapter2;
import com.example.divya.helpers.CustomNotificationAdapter;
import com.example.divya.helpers.JSONParser;
import com.example.divya.helpers.RecyclerItemClickListener;
import com.example.divya.helpers.SQLiteHandler;
import com.example.divya.models.Approval;
import com.example.divya.models.Notify;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Divya on 10/22/2015.
 */
public class ApprovalFragment extends Fragment {

    FloatingActionButton fab1;
    CustomApprovalAdapter2 adapter;
    RecyclerView rv;
    JSONParser jParser;
    SQLiteHandler db;
    ProgressDialog pDialog;
    ArrayList<Approval> approvalslist;
    public ApprovalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.approval, container, false);


        db = new SQLiteHandler(getActivity().getApplicationContext());
        rv = (RecyclerView)rootView.findViewById(R.id.rv_approval);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);

        jParser = new JSONParser();

        approvalslist = new ArrayList<Approval>();


        fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_approval);

        HashMap<String,String> mp = db.getUserDetails();
        if(!mp.get("roll").equals("y13uc094"))
            fab1.setVisibility(View.INVISIBLE);
        else {
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(getActivity(), AddApproval.class);
                    getActivity().startActivity(myIntent);
                }
            });
        }
        new LoadAllApprovals().execute();

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Approval n = approvalslist.get(position);
                        Intent i = new Intent(getActivity(),ApprovalDetails.class);
                        i.putExtra("getApprovalDetails",n);
                        startActivity(i);
                    }
                })
        );

        return rootView;

    }
    class LoadAllApprovals extends AsyncTask<String, String, String> {
        JSONArray approvals;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Approvals");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/getApprovalDetails.php", "GET", params);


            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
/*
                    aid: "1"
                    subject: "confirm from director sir blah blah blah"
                    assigned: "Enzup Garg"
                    deadline: "24/01/2015"
                    nsteps: "4"
                            */
                    approvals = json.getJSONArray("approval");

                    for (int i = 0; i < approvals.length(); i++)
                    {
                        JSONObject c = approvals.getJSONObject(i);

                        Integer aid = c.getInt("aid");
                        String subject = c.getString("subject");
                        String assigned = c.getString("assigned");
                        String deadline = c.getString("deadline");
                        String nsteps = c.getString("nsteps");

                        approvalslist.add(new Approval(String.valueOf(aid),subject,assigned,deadline,nsteps));
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
        adapter = new CustomApprovalAdapter2(approvalslist);
        rv.setAdapter(adapter);


/*
        rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               *//* Notify n = stringAdapter.getItem(position);
                Intent i = new Intent(getActivity(),NotifyDetails.class);
                i.putExtra("getClickedNotification",n);
                startActivity(i);*//*

            }
        });*/


    }

}