package com.example.divya.helpers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.divya.models.Notify;
import com.example.divya.sepm.R;

import java.util.List;

/**
 * Created by Divya on 10/23/2015.
 */public class CustomNotificationAdapter extends ArrayAdapter<Notify> {
    List<Notify> items;
    public CustomNotificationAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomNotificationAdapter(Context context, int resource, List<Notify> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_bg, null);
        }
        if(items!=null)
        {
            TextView tt1 = (TextView) v.findViewById(R.id.list_notify_sports_tv);
            TextView tt2 = (TextView) v.findViewById(R.id.list_notify_details_tv);
            TextView tt3 = (TextView) v.findViewById(R.id.list_notify_date_tv);
            TextView tt4 = (TextView) v.findViewById(R.id.list_notify_time_tv);

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color1 = generator.getRandomColor();

            TextDrawable.IBuilder builder = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .round();

            String value = String.valueOf(items.get(position).sports.charAt(0));

            TextDrawable ic1 = builder.build(value, color1);

            ImageView image = (ImageView) v.findViewById(R.id.image_view);
            image.setImageDrawable(ic1);


            tt1.setText((String) items.get(position).sports);
            tt2.setText((String) items.get(position).content);
            tt3.setText((String) items.get(position).date);
            tt4.setText((String) items.get(position).time);
        }
        return v;
    }

}