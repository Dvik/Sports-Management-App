package com.example.divya.helpers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divya.models.Inventory;
import com.example.divya.sepm.R;

import java.util.List;

/**
 * Created by Divya on 11/8/2015.
 */
public class CustomInventoryAdapter extends RecyclerView.Adapter<CustomInventoryAdapter.InventoryViewHolder> {

    List<Inventory> inventorys;

    public CustomInventoryAdapter(List<Inventory> inventorys)
    {

        this.inventorys = inventorys;

    }



    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tt1,tt2,tt3,tt4,tt5;
        TextView personAge;
        ImageView img;
        InventoryViewHolder(View itemView) {
            super(itemView);


            tt2 = (TextView) itemView.findViewById(R.id.inventory_equipment_tv);

            tt3 = (TextView) itemView.findViewById(R.id.inventory_manager_tv);
            tt4 = (TextView) itemView.findViewById(R.id.inventory_available_tv);
            tt5 = (TextView) itemView.findViewById(R.id.inventory_damaged_tv);

            img = (ImageView) itemView.findViewById(R.id.inventory_image);
        }
    }

    @Override
    public int getItemCount()
    {
        return inventorys.size();
    }
    @Override
    public void onBindViewHolder(InventoryViewHolder appViewHolder, int position) {

        appViewHolder.tt2.setText((String) inventorys.get(position).equipment);
        appViewHolder.tt3.setText((String) inventorys.get(position).manager);
        appViewHolder.tt4.setText("Available "+(String) inventorys.get(position).available);
        appViewHolder.tt5.setText("Damaged "+(String) inventorys.get(position).damaged);


        String sport =  (String) inventorys.get(position).sport;

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
    public InventoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventoryrow, viewGroup, false);
        InventoryViewHolder pvh = new InventoryViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}