package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mitch on 2015/08/10.
 */
public class User {
    public static final String PREFS_NAME = "UserDatabase";
    private String username;
    private String password;
    private Context context;

    public User(String username, String password, Context context){
        this.username = username;
        this.password = password;
        this.context = context;
    }
    public boolean login() {
        SharedPreferences users = context.getSharedPreferences(PREFS_NAME, 0);
        //SharedPreferences.Editor usersEdit = users.edit();
        //return users.getString(username, "no_name");
        if (users.getString(username, "no_name").equals("no_name")){
            return false;
            //return "1";
        } else if (users.getString(username, "no_name").equals(password)){
            return true;
            //return "2";
        } else {
            return false;
            //return "3";
        }

    }
    public boolean register() {
        SharedPreferences users = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor usersEdit = users.edit();
        if (users.getString(username, "no_name") == "no_name"){
            usersEdit.putString(username, password);
            usersEdit.commit();
            return true;
        } else {
            return false;
        }
        //return true;
    }
    public String getUsername(){
        return username;
    }
}
