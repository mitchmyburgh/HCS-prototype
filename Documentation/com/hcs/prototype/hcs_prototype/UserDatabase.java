package com.hcs.prototype.hcs_prototype;

/**
 * Created by mitch on 2015/08/23.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabase extends SQLiteOpenHelper {
    /**
     * Current version of the database, android uses this to indicate when to call onUpgrade
     */
    private static final int DATABASE_VERSION = 21;
    /**
     * The name of the database
     */
    private static final String DATABASE_NAME = "HCS2";
    /**
     * The name of the table containing the Users in the Database.
     */
    private static final String USER_TABLE_NAME = "users";
    /**
     * The user id column header.
     */
    private static final String KEY_ID_USER = "id";
    /**
     * The username column header.
     */
    private static final String KEY_UN  = "username";
    /**
     * The password column header
     */
    private static final String KEY_PASS = "password";
    /**
     * The score column header
     */
    private static final String KEY_SCORE = "Score";

    private static Context context = null;
    /**
     * The String for creating the table
     */
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    KEY_ID_USER + " INTEGER PRIMARY KEY, " +
                    KEY_UN + " TEXT UNIQUE, " +
                    KEY_PASS + " TEXT, " +
                    KEY_SCORE + " INTEGER);";

    /**
     * Constructor
     * @param context the context for opening the database
     */
    public UserDatabase (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * {@inheritDoc}
     * Creates the database
     * @param db the database to be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("DATABASE", "onCREATE");
        db.execSQL(USER_TABLE_CREATE);
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_UN, "name");
        values.put(KEY_PASS, "name");
        values.put(KEY_SCORE, 123);

        long newRowId = db.insert(
                USER_TABLE_NAME,
                "null",
                values);

    }

    /**
     * {@inheritDoc}
     * Upgrades the database
     * @param db the database to be updated
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int n, int i){
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        this.onCreate(db);
    }

    /**
     * {@inheritDoc}
     * Opens the database
     * @param db the database to be Opened
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        //db.execSQL(CASE_STUDY_TABLE_CREATE);
        //this.db = db;
    }

    /**
     * Writes a new row to the user database
     * @param name The username
     * @param pass the user's password
     * @param score the score
     * @return long the row id
     */
    public long writeRowUser(String name, String pass, int score){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_UN, name);
        values.put(KEY_PASS, pass);
        values.put(KEY_SCORE, score);

        long newRowId = db.insert(
                USER_TABLE_NAME,
                "null",
                values);
        Log.v("database", newRowId + "");
        db.close();
        return newRowId;
    }

    /**
     * Returns the database as a string
     * @return String the database represented as a string
     */
    public String getRowsStringUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String ls = USER_TABLE_CREATE+"\n";
        if (cursor.moveToFirst()) {
            do {
                ls += cursor.getInt(0)+" ";
                ls += (cursor.getString(1))+" ";
                ls += (cursor.getString(2))+" ";
                ls += (cursor.getInt(3))+"\n";

            } while (cursor.moveToNext());
        }
        db.close();
        return ls;
    }

    /**
     * Check the users password
     * @param uname The username
     * @param pass the user's password
     * @return int (-1,0,1) = (user not in the database, password incorrect, password correct)
     */
   public int checkPassUser(String uname, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
       if (cursor.moveToFirst()) {
            if (cursor.getString(2).equals(pass)){
                db.close();
                return 1; //password is found in the databse
            } else {
                db.close();
                return 0; //password is incorrect
            }
        } else {
           db.close();
           return -1; //user not in the database
        }

        //return cs;
    }

    /**
     * Get the user's score from the databse
     * @param uname the username
     * @return in the users score
     */
    public int getScoreUser(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getInt(3);
        } else {
            db.close();
            return -1; //user not in the database
        }
    }

    /**
     * Get the user's password
     * @param uname the user's username
     * @return String the user's password
     */
    public String getPassUser(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getString(2);
        } else {
            db.close();
            return "false"; //user not in the database
        }
    }

    /**
     * Increment the user's score by n
     * @param uname the user's username
     * @param n the number to increment the score by
     * @return Boolean the success of incrementing the score
     */
    public boolean incScoreUser(String uname, int n){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
        if (cursor.moveToFirst()) {
            values.put(KEY_SCORE, n+cursor.getInt(3));
            db.update(USER_TABLE_NAME, values, "username=?", new String[] {uname});
            db.close();
            return true;
        } else {
            db.close();
            return false; //user not in the database
        }
    }

}
