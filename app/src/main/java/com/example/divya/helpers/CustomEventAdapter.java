package com.example.divya.helpers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divya.models.Event;
import com.example.divya.sepm.R;

import java.util.List;

/**
 * Created by Divya on 11/8/2015.
 */
public class CustomEventAdapter extends RecyclerView.Adapter<CustomEventAdapter.EventViewHolder> {

    List<Event> events;

    public CustomEventAdapter(List<Event> events)
    {

        this.events = events;

    }



    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tt1,tt2,tt3,tt4,tt5;
        TextView personAge;
        ImageView img;
        EventViewHolder(View itemView) {
            super(itemView);


            tt1 = (TextView) itemView.findViewById(R.id.event_subject_tv);
            tt2 = (TextView) itemView.findViewById(R.id.event_date);

            tt3 = (TextView) itemView.findViewById(R.id.event_time);
            tt4 = (TextView) itemView.findViewById(R.id.event_place);

            img = (ImageView) itemView.findViewById(R.id.event_image);
        }
    }

    @Override
    public int getItemCount()
    {
        return events.size();
    }
    @Override
    public void onBindViewHolder(EventViewHolder appViewHolder, int position) {

        appViewHolder.tt1.setText((String) events.get(position).name);
        appViewHolder.tt2.setText((String) events.get(position).date);
        appViewHolder.tt3.setText((String) events.get(position).start_time+"   "+(String) events.get(position).end_time);
        appViewHolder.tt2.setText((String) events.get(position).lat+"   "+(String) events.get(position).lng);

        String sport =  (String) events.get(position).sport;

        if(sport.equalsIgnoreCase("Football")) {
            appViewHolder.img.setImageResource(R.drawable.football);
        }

        if(sport.equalsIgnoreCase("Cricket")) {
            appViewHolder.img.setImageResource(R.drawable.basketball);
        }

        if(sport.equalsIgnoreCase("basketball")) {
            appViewHolder.img.setImageResource(R.drawable.iconspo);
        }

        if(sport.equalsIgnoreCase("table tennis")) {
            appViewHolder.img.setImageResource(R.drawable.tabletennis);
        }


        if(sport.equalsIgnoreCase("boxing")) {
            appViewHolder.img.setImageResource(R.drawable.boxing);
        }


    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.eventrow, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}