package com.example.divya.sepm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Divya on 11/8/2015.
 */
public class AddApproval extends ActionBarActivity {

    private Toolbar mToolbar;
    EditText edt1,edt2,edt3,edt4;

    public static String subject,details,assigned,steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addapproval1);


        mToolbar = (Toolbar) findViewById(R.id.actionbar_approval);
        setSupportActionBar(mToolbar);
        setTitle("Add notification");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt1 = (EditText) findViewById(R.id.add_subject_approval_edt);
        edt2 = (EditText) findViewById(R.id.add_details_approval_edt);
        edt3 = (EditText) findViewById(R.id.add_assigned_to_approval_edt);
        edt4 = (EditText) findViewById(R.id.add_steps_approval_edt);



        Button btn = (Button) findViewById(R.id.next);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddApproval.this,EventOnMapActivity.class);
                subject = edt1.getText().toString();
                details = edt2.getText().toString();
                assigned = edt3.getText().toString();
                steps = edt4.getText().toString();
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                super.onBackPressed();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
