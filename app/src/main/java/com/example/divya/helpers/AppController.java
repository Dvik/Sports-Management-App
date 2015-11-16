package com.example.divya.helpers;

import java.util.UUID;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class AppController extends Application {
	 
    public static final String TAG = AppController.class.getSimpleName();
 
    private RequestQueue mRequestQueue;
 
    private static AppController mInstance;
    
    private TelephonyManager tm;
    
    public static String deviceId;
    
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

    	final String tmDevice, tmSerial, androidId;
    	tmDevice = "" + tm.getDeviceId();
    	tmSerial = "" + tm.getSimSerialNumber();
    	androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

    	UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
    	deviceId = deviceUuid.toString();
    }
 
    public static synchronized AppController getInstance() {
        return mInstance;
    }
 
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
 
        return mRequestQueue;
    }
 
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}