package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mitch on 2015/08/10.
 * Class for logging in and registering users.
 * Connects to internal storage to validate the user.
 * is a singleton for use across multiple activities
 */
public class User{
    public static final String PREFS_NAME = "UserDatabase";
    private String username;
    private String password;
    private Context context;
    private boolean currentUser = false;
    private static User user = null;
    private int score = 100;
    /**
     * Create a new user object
     */
    private User (){
        this.username = "username";
        this.password = "password";
        this.context = null;
    }
    /**
    * Create a new user object
    * @param username the user's username
    * @param password the user's password
    * @param context the context which the data will be obtained from
    */
    private User(String username, String password, Context context){
        this.username = username;
        this.password = password;
        this.context = context;
    }

    /**
     * creates a user object within teh singleton, else writes ove the object
     * @param username the username of the user
     * @param password the password of the user
     * @param context the context for pref file access
     * @return user singleton
     */
    public static User createUser(String username, String password, Context context){
        user = null;
        user = new User(username, password, context);
        return user;
    }

    /**
     * get the current user singleton
     * @return user singleton
     */
    public static User getUser() {
        if (user == null){
            user = new User();
        }
        return user;
    }
    /**
     * Login the current user
     * @return boolean whether the user is logged in
     */
    public boolean login() {
        if (context != null) {
            SharedPreferences users = context.getSharedPreferences(PREFS_NAME, 0);
            //SharedPreferences.Editor usersEdit = users.edit();
            //return users.getString(username, "no_name");
            if (users.getString(username, "no_name").equals("no_name")) {
                currentUser = false;
                return false;
            } else if (users.getString(username, "no_name").equals(password)) {
                currentUser = true;
                return true;
            } else {
                currentUser = false;
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     * Register the current user
     * @return boolean whether the user is registered and logged in
     */
    public boolean register() {
        if (context != null){
            SharedPreferences users = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor usersEdit = users.edit();
            if (users.getString(username, "no_name") == "no_name") {
                usersEdit.putString(username, password);
                usersEdit.commit();
                currentUser = true;
                return true;
            } else {
                currentUser = false;
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     * Returns the current user's username
     * @return String this.username
     */
    public String getUsername(){
        return username;
    }
    /**
     * Returns whether the user is the current user (i.e. is the current user logged in and validated)
     * @return boolean this.currentUser
     */
    public boolean isCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the user's current score
     * @return score - the user's current score
     */
    public int getScore(){
        return score;
    }
}
