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
import com.example.divya.models.Steps;
import com.example.divya.sepm.R;

import java.util.List;

/**
 * Created by Divya on 11/9/2015.
 */
public class CustomStepsAdapter extends ArrayAdapter<Steps>{

        List<Steps> items;
        public CustomStepsAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public CustomStepsAdapter(Context context, int resource, List<Steps> items) {
            super(context, resource, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

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



                subject_tv.setText((String) items.get(position).place);
                place_tv.setText((String) items.get(position).place);
                priority_tv.setText((String) items.get(position).priority);
            }
            return v;
        }

    }

