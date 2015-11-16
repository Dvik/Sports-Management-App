package com.example.divya.sepm;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.divya.helpers.AppConfig;
import com.example.divya.helpers.NavigationDrawerCallbacks;
import com.example.divya.helpers.SQLiteHandler;
import com.example.divya.helpers.SessionManager;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {
    private SQLiteHandler db;
    public SessionManager session;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer

        db.getReadableDatabase();
        HashMap hms=new HashMap<String, String>();
        hms = db.getUserDetails();
        System.out.println(hms.get("name").toString()+" "+hms.get("roll").toString());
        mNavigationDrawerFragment.setUserData(hms.get("name").toString(), hms.get("designation").toString(), BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment=null;

        switch(position)
        {
            case 0:
                if (mToolbar != null)
                    mToolbar.setTitle("Event");
                fragment = new EventFragment();
                break;
            case 1:
                if (mToolbar != null)
                    mToolbar.setTitle("Approval");
                fragment = new ApprovalFragment();
                break;
            case 2:
                if (mToolbar != null)
                    mToolbar.setTitle("Inventory");
                fragment = new InventoryFragment();
                break;
            case 3:
                if (mToolbar != null)
                    mToolbar.setTitle("Notifications");
                fragment = new NotifyFragment();

                break;
            case 4:
                logoutUser();

            default:
                break;
        }

            if (fragment != null)
            {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }

      }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();
       // db.deleteNotifications();


        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

}
