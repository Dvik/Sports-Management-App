package com.example.divya.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.divya.models.Fixtures;
import com.example.divya.sepm.R;

import java.util.List;

/**
 * Created by Divya on 11/9/2015.
 */
public class CustomFixturesAdapter extends ArrayAdapter<Fixtures>{

    List<Fixtures> items;
    public CustomFixturesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomFixturesAdapter(Context context, int resource, List<Fixtures> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.event_list_fixture_row, null);
        }

        if(items!=null)
        {
            TextView subject_tv = (TextView) v.findViewById(R.id.list_teams);
            TextView status = (TextView) v.findViewById(R.id.list_status);
            TextView priority_tv = (TextView) v.findViewById(R.id.list_date);



            subject_tv.setText((String) items.get(position).team1+"  v/s  "+(String) items.get(position).team2);
            status.setText((String) items.get(position).status);
            priority_tv.setText((String) items.get(position).date+"    "+(String) items.get(position).time);
        }
        return v;
    }

}

