package com.example.divya.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.divya.models.Steps;
import com.example.divya.sepm.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 11/9/2015.
 */
public class CustomStepsAdapter extends ArrayAdapter<Steps>{

        List<Steps> items;
    CheckBox chb;
    Context context;
    ProgressDialog pDialog;
    JSONParser jParser;
        public CustomStepsAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public CustomStepsAdapter(Context context, int resource, List<Steps> items) {
            super(context, resource, items);
            this.items = items;
            this.context = context;
            jParser = new JSONParser();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.approval_list_steps_row, null);
            }

            if(items!=null)
            {
                TextView subject_tv = (TextView) v.findViewById(R.id.list_desc_subject);
                TextView place_tv = (TextView) v.findViewById(R.id.list_desc_place);
                TextView priority_tv = (TextView) v.findViewById(R.id.list_desc_priority);
                chb = (CheckBox) v.findViewById(R.id.check_steps_approval);


                subject_tv.setText((String) items.get(position).place);
                place_tv.setText((String) items.get(position).place);
                priority_tv.setText((String) items.get(position).priority);

                if(position < Integer.parseInt(items.get(position).nsteps))
                    chb.setChecked(true);

                chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                        if (isChecked) {
                            new UpdateCheck((String) items.get(position).aid,(String) items.get(position).nsteps).execute();
                            chb.setEnabled(false); // disable checkbox
                        }
                    }
                });


            }
            return v;
        }



    class UpdateCheck extends AsyncTask<String, String, String> {

        String aid,nsteps;
        UpdateCheck(String aid, String nsteps)
        {
            this.aid = aid;
            this.nsteps = nsteps;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Updating....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("modifystep"," "));

            params.add(new BasicNameValuePair("aid",String.valueOf(aid)));
            params.add(new BasicNameValuePair("nsteps",String.valueOf(nsteps)));

            JSONObject json = jParser.makeHttpRequest("http://divyav.esy.es/modifyStep.php", "POST", params);

            Log.d("sss", json.toString());


            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

        }
    }


}

