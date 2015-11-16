package com.example.divya.sepm;

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
import com.example.divya.helpers.SQLiteHandler;
import com.example.divya.helpers.SessionManager;

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

public class LogInActivity extends Activity {
    // LogCat tag
    //private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private TextView btnLinkToRegister;
    private EditText roll;
    private EditText password;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        roll = (EditText) findViewById(R.id.roll_login);
        password = (EditText) findViewById(R.id.password_login);
        btnLogin = (Button) findViewById(R.id.login_btn);
        btnLinkToRegister = (TextView) findViewById(R.id.tv_login);
 
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
 
        // Session manager
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        // Check if user is already logged in or not
        db.getWritableDatabase();
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = roll.getText().toString();
                String pass = password.getText().toString();

                // Check for empty data in the form
                if (email.trim().length() > 0 && pass.trim().length() > 0) {
                    // login user
                    checkLogin(email, pass);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
 
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
 
    }
 
    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String roll, final String password) {
    	
    	
    	
    		
        // Tag used to cancel the request
        String tag_string_req = "req_login";
 
        pDialog.setMessage("Logging in ...");
        showDialog();
 
        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
 
                    @Override
                    public void onResponse(String response) {
          //              Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();
 
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");
 
                            // Check for error node in json
                            if (success==1) {
                                // user successfully logged in
                                // Create login session
                                session.setLogin(true);
                                
                                String name = jObj.getString("name");
                                String roll = jObj.getString("roll");
                                String contact = jObj.getString("contact");
                                String year = jObj.getString("year");
                                String sid = jObj.getString("sid");
                                String designation = jObj.getString("designation");


                                db.addUser(name,roll,contact,year,sid,designation);
                                // Launch main activity
                                Intent intent = new Intent(LogInActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                Toast.makeText(getApplicationContext(),
                                        "Error in logging in", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
 
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
             //           Log.e(TAG, "Login Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {
 
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", "login");
                params.put("roll", roll);
                params.put("password", password);
                return params;
            }
 
        };
 
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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