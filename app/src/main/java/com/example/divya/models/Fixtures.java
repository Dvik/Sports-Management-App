package com.example.divya.models;

/**
 * Created by Divya on 11/8/2015.
 */
public class Fixtures {
    public String fid;
    public String eid;
    public String team1;
    public String team2;
    public String date;
    public String time;
    public String status;
    public String winner;

    public Fixtures(String fid,String eid, String team1,String team2,String date,String time,String status,String winner)
    {
        this.fid= fid;
        this.eid=eid;
        this.team1=team1;
        this.team2=team2;
        this.date=date;
        this.time=time;
        this.status=status;
        this.winner=winner;

    }
}
