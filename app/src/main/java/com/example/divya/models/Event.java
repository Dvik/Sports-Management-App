package com.example.divya.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Divya on 10/23/2015.
 */

public class Event implements Parcelable {


    public String eid;
    public String name;
    public String date;
    public String start_time;
    public String end_time;
    public String lat;
    public String lng;
    public String description;
    public String sport;
    public String status;
    public String winner;
    public String contactPerson;
    public String contactNo;




    public Event(String eid, String name, String date, String start_time, String end_time, String lat,String lng, String description
                 ,String sport, String status, String winner, String contactPerson, String contactNo)

    {
        this.eid = eid;
        this.name = name;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
        this.sport = sport;
        this.status = status;
        this.winner = winner;
        this.contactPerson = contactPerson;
        this.contactNo = contactNo;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eid);
        parcel.writeString(name);
        parcel.writeString(date);
        parcel.writeString(start_time);
        parcel.writeString(end_time);
        parcel.writeString(lat);
        parcel.writeString(lng);
        parcel.writeString(description);
        parcel.writeString(sport);
        parcel.writeString(status);
        parcel.writeString(winner);

        parcel.writeString(contactPerson);
        parcel.writeString(contactNo);




    }
    public Event(Parcel in){
        this.eid = in.readString();
        this.name = in.readString();
        this.date = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.description = in.readString();
        this.sport = in.readString();
        this.status = in.readString();
        this.winner = in.readString();
        this.contactPerson = in.readString();
        this.contactNo = in.readString();

    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
