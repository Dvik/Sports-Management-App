package com.example.divya.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Divya on 11/8/2015.
 */

public class Approval implements Parcelable {

    public String aid;
    public String subject;
    public String assigned;
    public String deadline;
    public String sid;

    public Approval(String aid,String subject,String assigned,String deadline,String sid)
    {
        this.aid=aid;
        this.subject=subject;
        this.assigned=assigned;
        this.deadline=deadline;
        this.aid=aid;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(aid);
        parcel.writeString(subject);
        parcel.writeString(assigned);
        parcel.writeString(deadline);
        parcel.writeString(sid);

    }
    private Approval(Parcel in){
        this.aid = in.readString();
        this.subject = in.readString();
        this.assigned = in.readString();
        this.deadline = in.readString();
        this.sid = in.readString();

    }

    public static final Parcelable.Creator<Approval> CREATOR = new Parcelable.Creator<Approval>() {

        @Override
        public Approval createFromParcel(Parcel source) {
            return new Approval(source);
        }

        @Override
        public Approval[] newArray(int size) {
            return new Approval[size];
        }
    };
}
