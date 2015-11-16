package com.example.divya.sepm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.divya.helpers.SessionManager;

public class HomeActivity extends Activity
{
	Button b1,b2;
	private SessionManager session;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    setContentView(R.layout.first);

		    session = new SessionManager(getApplicationContext());
		 
		    // Check if user is already logged in or not
	        if (session.isLoggedIn()) {
	            // User is already logged in. Take him to main activity
	            Intent intent = new Intent(HomeActivity.this,
	                    MainActivity.class);
	            startActivity(intent);
	            finish();
	        }
		    b1=(Button)findViewById(R.id.register_home);
	        b1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				      Intent i = new Intent(HomeActivity.this,RegisterActivity.class);
				      startActivity(i);
				      finish();
					
				}
			});
	        b2=(Button)findViewById(R.id.login_home);
	        
	        b2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				      Intent i = new Intent(HomeActivity.this,LogInActivity.class);
				      startActivity(i);
				      finish();
					
				}
			});
		    
	}

}
