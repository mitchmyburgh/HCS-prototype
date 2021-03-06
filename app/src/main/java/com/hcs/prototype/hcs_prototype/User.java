package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.StringTokenizer;

/**
 * <h1>User Class Singleton</h1>
 * The user class implements authenticated login and register for users.
 * The user is authenticated against a list of users found in local storage.
 * The User class is a singleton for streamlined access across multiple activities. The object is accessed via User.getUser() and actions can be taken on this user object.
 * 
 * @author Mitch Myburgh
 * @version %I%, %G%
 * @since 0.1 //TODO: version numbers in github
 */
public class User{
    //Instance variables
    /**
     * <h2>String PREFS_NAME</h2>
     * The name of the shared Preferences fiel that the user data is stored in.
     */
    public static final String PREFS_NAME = "UserDatabase";
    /**
     * <h2>String username</h2>
     * The user's username. 
     */
    private String username;
    /**
     * <h2>String password</h2>
     * The user's password 
     */
    private String password;
    /**
     * <h2>Context context</h2>
     * The context which is necessary for loading the preferences file (acceptable to pass the current activity with this) 
     */
    private Context context;
    /**
     * <h2>boolean currentUser = false</h2>
     * Indicates whether the current user is authenticated (as it is possble to create a user without authentication - but further actions cannot be applied to this user without calling login() or register() first)  
     */
    private boolean currentUser = false;
    /**
     * <h2>User user = null<h2>
     * The user object that represents the currently stored user, the class stores its own object - necessary for being a singleton.  
     */
    private static User user = null;
    /**
     * <h2>int score</h2>
     * The user's current score
     */
    private int score = 0;
    /**
     * The case study database
     */
    private CaseStudyDatabase database = null;
    /**
     * The users name
     */
    private String name = "No Name";
    /**
     * the users telephoen number
     */
    private String tel = "083 555 6666";
    /**
     * Create a new user object
     */
    User (){
        this.username = "username";
        this.password = "password";
        this.context = null;
    }

    /**
     * Create a new user object
     * @param username the user's username
     * @param context the context which the data will be obtained from
     */
    User(String username, Context context){
        this.username = username;
        //this.password = password;
        this.context = context;
        database = new CaseStudyDatabase(context);
        this.password = database.getPassUser(username);
        this.score = database.getScoreUser(username);
        this.name = database.getNameUser(username);
    }

    /**
    * Create a new user object
    * @param username the user's username
    * @param password the user's password
    * @param context the context which the data will be obtained from
    */
    User(String username, String password, Context context){
        this.username = username;
        this.password = password;
        this.context = context;
        database = new CaseStudyDatabase(context);
        //this.password = database.getPassUser(username);
        this.score = database.getScoreUser(username);
        this.name = database.getNameUser(username);
        Log.v("NAME", this.name);
        //database.getRowsString();
    }

    /**
     * creates a user object within the singleton, else writes over the object
     * @param username the username of the user
     * @param password the password of the user
     * @param context the context for pref file access
     * @return user singleton
     *
     */
    public static User createUser(String username, String password, Context context){
        user = null; //reset user object
        user = new User(username, password, context);
        return user;
    }

    /**
     * creates a user object within the singleton, else writes over the object
     * @param username the username of the user
     * @param context the context for pref file access
     * @return user singleton
     *
     */
    public static User createUser(String username, Context context){
        user = null; //reset user object
        user = new User(username, context);
        return user;
    }

    /**
     * get the current user object
     * @return User user singleton
     * @see User
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
    public int login() {
        if (context != null) {
            SharedPreferences users = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor usersEdit = users.edit();
            Log.v("PASS2", password);
            if (database.checkPassUser(username, password) == -1) {
                currentUser = false;
                return -1;
            } else if (database.checkPassUser(username, password) == 1) {
                currentUser = true;
                usersEdit.putString("current_user", username);
                usersEdit.commit();
                score = database.getScoreUser(username);
                tel = database.getTelUser(username);
                name = database.getNameUser(username);
                return 1;
            } else {
                currentUser = false;
                return 0;
            }
        } else {
            return -2;
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
            if (database.checkPassUser(username, password) == -1) { //not in database
                database.writeRowUser(username, password, 0, this.name, this.tel);
                score = database.getScoreUser(username);
                usersEdit.putString("current_user", username);
                usersEdit.commit();
                currentUser = true;
                return currentUser;
            } else {
                currentUser = false;
                return currentUser;
            }
        } else {
            return currentUser;
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
     * @return boolean this.currentUser which indicates whether teh user has been authenticated
     */
    public boolean isCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the user's current score
     * @return int this.score the user's current score
     */
    public int getScore(){
        return score;
    }
    
    /**
     * Overwrite and set the user's current total score
     * @param score the new score
     * @return int this.score the new score.
     */
    public int setScore(int score){
        this.score = score;
        return this.score;
    }
    
    /**
     * Increment the user's current score
     * @param score the amount to increment the score by
     * @return int this.score the new score
     */
    public int incScore(int score){
        this.score += score;
        database.incScoreUser(username, score);
        return this.score;
    }

    /**
     * Get the currently logged in user (for reopening the app at later time)
     * @param context The context for accessing the preferences file
     * @return boolean whether there is a currently logged in user
     */
    public static boolean getCurrentUser(Context context){
        if (context != null) {
            SharedPreferences users = context.getSharedPreferences(PREFS_NAME, 0);
            if (users.getString("current_user", "no_name").equals("no_name")) {
                return false;
            } else  {
                User.createUser(users.getString("current_user", "no_name"), context);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Logout the user
     * @return boolean if the user is logged out
     */
    public boolean logout(){
        if (this.context != null){
            SharedPreferences users = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor usersEdit = users.edit();
            usersEdit.putString("current_user", "no_name");
            usersEdit.commit();
            user = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set teh user's phone number
     * @param num The user's phone number
     */
    public void setPhone(String num){
        this.tel = num;
    }

    /**
     * Set the user's name
     * @param name The user's name
     */
    public void setName(String name){
        this.name = name;
    }

    public String getTel(){
        return this.tel;
    }
    public String getName(){
        return this.name;
    }
}
