package com.example.divya.models;

/**
 * Created by Divya on 10/23/2015.
 */

public class Event {

    private String id;
    private String sports;
    private String content;
    private String date;
    private String time;


    public Event(String id, String sports, String content, String date, String time)
    {
        this.id = id;
        this.sports = sports;
        this.content = content;
        this.date = date;
        this.time = time;

    }
}
