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

import java.util.LinkedList;
import java.util.List;

public class UserDatabase extends SQLiteOpenHelper {
    /**
     * Current version of the database, android uses this to indicate when to call onUpgrade
     */
    private static final int DATABASE_VERSION = 21;
    /**
     * The name of the database
     */
    private static final String DATABASE_NAME = "HCS";
    /**
     * The name of the table containing the Case Studies in the Database.
     */
    private static final String USER_TABLE_NAME = "users";
    /**
     * The user id column header.
     */
    private static final String KEY_ID  = "id";
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
                    KEY_ID + " INTEGER PRIMARY KEY, " +
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
     * @param name
     * @param pass
     * @param score
     * @return
     */
    public long writeRow(String name, String pass, int score){
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
    public String getRowsString(){
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
     * Get All the Case Studies as a linked list of CaseStudy Objects
     * @return List of CaseStudy objects
     * @see CaseStudy
     */
    /*public List<CaseStudy> getAllCaseStudy(){
        List<CaseStudy> csl = new LinkedList<CaseStudy>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + CASE_STUDY_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
        if (cursor.moveToFirst()) {
            do {
                cs = new CaseStudy(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                // Add case study to case study list
                csl.add(cs);
            } while (cursor.moveToNext());
        }
        db.close();
        return csl;
    }*/

    /**
     * Get the case study with a specific Primary Key
     * @param pk the primary key of the case study in the database
     * @return CaseStudy the Case study referenced in the database
     */
   public boolean checkPass(String uname, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
       //db.close();
       if (cursor.moveToFirst()) {
            return cursor.getString(2).equals(pass);
            //cs = new CaseStudy(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), this.context);
        } else {
            return false;
        }

        //return cs;
    }

}
