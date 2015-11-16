package com.example.divya.sepm;

/**
 * Created by Divya on 10/19/2015.
 */

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.divya.helpers.AppConfig;
import com.example.divya.helpers.AppController;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity{
    public EditText name,roll,password,contact,year,sid;
    public TextView linktologin;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.register);

        name= (EditText)findViewById(R.id.name_register);
        roll= (EditText)findViewById(R.id.roll_register);
        password= (EditText)findViewById(R.id.password_register);
        contact= (EditText)findViewById(R.id.contact_register);
        year= (EditText)findViewById(R.id.year_register);
        linktologin = (TextView) findViewById(R.id.tv_register);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Button b=(Button)findViewById(R.id.register);

        Intent i = getIntent();

        linktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LogInActivity.class);
                startActivity(i);
                finish();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Tag used to cancel the request

                String tag_string_req = "req_register";

                pDialog.setMessage("Registering ...");
                showDialog();

                StringRequest strReq = new StringRequest(Method.POST,
                        AppConfig.URL_REGISTER, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        System.out.println(response);
                        try {
                            JSONObject jObj = new JSONObject(response);

                            int success = jObj.getInt("success");
                            if (success==1) {
                                // User successfully stored in MySQL
                                // Now store the user in sqlite

                                System.out.println("inside no error");
                                //JSONObject user = jObj.getJSONObject("user");

                                String roll = jObj.getString("roll");


                                // Launch login activity
                                Intent intent = new Intent(
                                        RegisterActivity.this,
                                        LogInActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                // Error occurred in registration. Get the error
                                // message
                                //String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        "Error in registering", Toast.LENGTH_LONG).show();
                                                     }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("register", "register");
                        params.put("name", name.getText().toString());
                        params.put("roll",roll.getText().toString());
                        params.put("password", password.getText().toString());
                        params.put("contact", contact.getText().toString());
                        params.put("year", year.getText().toString());
                        params.put("sid", "sid");
                        params.put("designation", "student");

                        return params;
                    }

                };
                System.out.println(strReq);
                System.out.println(tag_string_req);
                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                    }
        });

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}


