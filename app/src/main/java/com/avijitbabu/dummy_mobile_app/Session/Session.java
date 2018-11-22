package com.avijitbabu.dummy_mobile_app.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public void setCustomerId(String id) {
        prefs.edit().putString("customer_id", id).commit();
    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }

    public String getCustomerId() {
        String CustomerId = prefs.getString("customer_id","");
        return CustomerId;
    }

    public  void logout(){
        prefs.edit().clear().commit();
    }

}
