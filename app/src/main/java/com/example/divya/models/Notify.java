package com.example.divya.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Divya on 10/23/2015.
 */

public class Notify implements Parcelable{

    public String id;
    public String sports;
    public String content;
    public String date;
    public String time;
    public boolean important;

    public Notify(String id, String sports, String content, String date, String time, boolean important)
    {
        this.id = id;
        this.sports = sports;
        this.content = content;
        this.date = date;
        this.time = time;
        this.important = important;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(sports);
        parcel.writeString(content);
        parcel.writeString(date);
        parcel.writeString(time);

    }
    private Notify(Parcel in){
        this.id = in.readString();
        this.sports = in.readString();
        this.content = in.readString();
        this.date = in.readString();
        this.time = in.readString();

    }

    public static final Parcelable.Creator<Notify> CREATOR = new Parcelable.Creator<Notify>() {

        @Override
        public Notify createFromParcel(Parcel source) {
            return new Notify(source);
        }

        @Override
        public Notify[] newArray(int size) {
            return new Notify[size];
        }
    };
}
