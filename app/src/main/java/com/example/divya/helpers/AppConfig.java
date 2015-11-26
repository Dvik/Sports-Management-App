package com.example.divya.helpers;


import com.example.divya.models.Event;

import java.util.List;

public class AppConfig {
    // Server user login url
	//public static String ip="http://dreamanimators.cloudapp.net/Blood/api/";
	public static String ip="http://divyav.esy.es/";
	public static String URL_LOGIN = ip+"login.php";

    public static String URL_REGISTER = ip+"register.php";

    public static List<Event> listOfEvents;

    public static Double latitude,longitude;
    
}