package com.example.divya.sepm;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Divya on 11/25/2015.
 */
public class ContactFragment extends Fragment {

    Button b1,b2,b3,b4;
    public ContactFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.contact, container, false);

        b1 = (Button) rootView.findViewById(R.id.btn_biyani);
        b2 = (Button) rootView.findViewById(R.id.btn_vikash);
        b3 = (Button) rootView.findViewById(R.id.btn_badaya);
        b4 = (Button) rootView.findViewById(R.id.btn_enzup);


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Uri uri= Uri.parse("https://www.facebook.com");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Uri uri= Uri.parse("https://www.facebook.com/dvikash.champ");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Uri uri= Uri.parse("http://www.lnmiit.ac.in/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
