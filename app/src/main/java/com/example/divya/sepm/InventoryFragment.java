package com.example.divya.sepm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Divya on 10/22/2015.
 */
public class InventoryFragment extends Fragment {

    public InventoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.inventory, container, false);
        return rootView;

    }
}