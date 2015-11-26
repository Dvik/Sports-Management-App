package com.example.divya.models;

/**
 * Created by Divya on 11/8/2015.
 */
public class Inventory {
    public String iid;
    public String sport;
    public String equipment;
    public String available;
    public String damaged;
    public String manager;



    public Inventory(String iid,String sport, String equipment,String available,String damaged, String manager)
    {
        this.iid= iid;
        this.sport=sport;
        this.equipment=equipment;
        this.available=available;
        this.damaged=damaged;
        this.manager = manager;

    }
}
