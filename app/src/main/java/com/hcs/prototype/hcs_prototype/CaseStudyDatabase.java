package com.hcs.prototype.hcs_prototype;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>Case Study Database Class<h1>
 * The CaseStudyDatabase class extends android.database.sqlite.SQLiteOpenHelper and provides methods for reading and writing to the case study table in the database
 */
public class CaseStudyDatabase extends SQLiteOpenHelper {
    /**
     * Current version of the database, android uses this to indicate when to call onUpgrade
     */
    private static final int DATABASE_VERSION = 37;
    /**
     * The name of the database 
     */
    private static final String DATABASE_NAME = "HCS";
    /**
     * The name of the table containing the Case Studies in the Database.
     */
    private static final String CASE_STUDY_TABLE_NAME = "caseStudy";
    /**
     * The id column header. 
     */
    private static final String KEY_ID = "id";
    /**
     * The case id column header 
     */
    private static final String KEY_CASE_ID = "case_id";
    /**
     * The name column header 
     */
    private static final String KEY_NAME = "name";
    /**
     * The Description column header
     */
    private static final String KEY_DESC = "description";
    /**
     * The location column header
     */
    private static final String KEY_LOCATION = "location";
    /**
     * the type column header
     */
    private static final String KEY_TYPE = "type";
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
    private static final String KEY_UN  = "staff_number";
    /**
     * The password column header
     */
    private static final String KEY_PASS = "password";
    /**
     * The score column header
     */
    private static final String KEY_SCORE = "Score";
    /**
     * The username column header.
     */
    private static final String KEY_STAFF_NAME  = "name";
    /**
     * The username column header.
     */
    private static final String KEY_NUMBER  = "number";

    private static final String HIST_TABLE_NAME = "history";
    private static final String HIST_KEY = "hist";

    /**
     * The context for acessing the databse
     */
    private static Context context = null;
    /**
     * The String for creating the table
     */
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    KEY_ID_USER + " INTEGER PRIMARY KEY, " +
                    KEY_UN + " TEXT UNIQUE, " +
                    KEY_PASS + " TEXT, " +
                    KEY_SCORE + " INTEGER, " +
                    KEY_STAFF_NAME+" TEXT, "+
                    KEY_NUMBER+" TEXT);";
    /**
     * The String for creating the table
     */
    private static final String CASE_STUDY_TABLE_CREATE =
            "CREATE TABLE " + CASE_STUDY_TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_CASE_ID + " TEXT UNIQUE, " +
                    KEY_NAME + " TEXT, " +
                    KEY_DESC + " TEXT, " +
                    KEY_LOCATION + " TEXT, " +
                    KEY_TYPE + " TEXT);";

    /**
     * The String for creating the table
     */
    private static final String HIST_TABLE_CREATE =
            "CREATE TABLE " + HIST_TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_CASE_ID + " INTEGER, " +
                    KEY_UN + " TEXT, " +
                    HIST_KEY + " TEXT, "+
                    "FOREIGN KEY ("+KEY_UN+") REFERENCES "+USER_TABLE_NAME+" ("+KEY_UN+"));";

    /**
     * Constructor
     * @param context the context for opening the database
     */
    public CaseStudyDatabase (Context context){
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
        //Create Databases
        try {
            db.execSQL(CASE_STUDY_TABLE_CREATE);
            db.execSQL(USER_TABLE_CREATE);
            db.execSQL(HIST_TABLE_CREATE);
        } catch (android.database.sqlite.SQLiteException e){

        }
        //put some examples in the database
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, "CASESTUDY1");
        values.put(KEY_NAME, "Test1");
        values.put(KEY_DESC, "Case Study will test your skills to the max");
        values.put(KEY_LOCATION, "CaseStudy01.hson");
        values.put(KEY_TYPE, "LOCAL");

        long newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);



        values = new ContentValues();
        values.put(KEY_CASE_ID, "CASESTUDY2");
        values.put(KEY_NAME, "Test2");
        values.put(KEY_DESC, "Case Study will test your skills to the max");
        values.put(KEY_LOCATION, "CaseStudy02.hson");
        values.put(KEY_TYPE, "LOCAL");

        db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
        values = new ContentValues();
        values.put(KEY_CASE_ID, "CASESTUDY3");
        values.put(KEY_NAME, "Test3");
        values.put(KEY_DESC, "Case Study will test your skills to the max");
        values.put(KEY_LOCATION, "CaseStudy03.hson");
        values.put(KEY_TYPE, "LOCAL");

        db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);


        values = new ContentValues();
        values.put(KEY_CASE_ID, "CASESTUDY5");
        values.put(KEY_NAME, "Test5");
        values.put(KEY_DESC, "Case Study will test your skills to the max");
        values.put(KEY_LOCATION, "CaseStudy05.hson");
        values.put(KEY_TYPE, "LOCAL");

        newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);

        //Put Example User
        values = new ContentValues();
        values.put(KEY_UN, "test");
        values.put(KEY_PASS, "test");
        values.put(KEY_SCORE, 123);
        values.put(KEY_STAFF_NAME, "BOB");
        values.put(KEY_NUMBER, "1234567890");
        Log.v("STUFF", USER_TABLE_CREATE);

        db.insert(
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
        Log.v("DATABASE", "onUPGRADE");
        Log.v("DATABASE", CASE_STUDY_TABLE_CREATE);
        Log.v("DATABASE", CASE_STUDY_TABLE_NAME);
        //drop the tables and recreate the database TODO: remove for production
        db.execSQL("DROP TABLE IF EXISTS " + CASE_STUDY_TABLE_NAME);
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
     * Writes a new case study to the databse
     * @param id the case study id
     * @param name the case study name
     * @param type the case study type (local/disk)
     * @param location the case study location
     * @return long the new row id (-1 for failure)
     */
    public long writeRow(String id, String name, String desc, String location, String type){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, id);
        values.put(KEY_NAME, name);
        values.put(KEY_DESC, desc);
        values.put(KEY_LOCATION, location);
        values.put(KEY_TYPE, type);

        long newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
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
        String query = "SELECT  * FROM " + CASE_STUDY_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String ls = CASE_STUDY_TABLE_CREATE+"\n";
        if (cursor.moveToFirst()) {
            do {
                ls += cursor.getInt(0)+" ";
                ls += (cursor.getString(1))+" ";
                ls += (cursor.getString(2))+" ";
                ls += (cursor.getString(3))+" ";
                ls += (cursor.getString(4))+" ";
                ls += (cursor.getString(5))+"\n";

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
    public List<CaseStudy> getAllCaseStudy(){
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
    }

    /**
     * Get the case study with a specific Primary Key
     * @param pk the primary key of the case study in the database
     * @return CaseStudy the Case study referenced in the database
     */
    public CaseStudy getCaseStudy(long pk){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + CASE_STUDY_TABLE_NAME+" WHERE "+KEY_ID+" = "+pk;
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
        if (cursor.moveToFirst()) {
            cs = new CaseStudy(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), this.context);
        }
        db.close();
        return cs;
    }
    /**
     * Writes a new row to the user database
     * @param name The username
     * @param pass the user's password
     * @param score the score
     * @return long the row id
     */
    public long writeRowUser(String name, String pass, int score, String staff_name, String number){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_UN, name);
        values.put(KEY_PASS, pass);
        values.put(KEY_SCORE, score);
        values.put(KEY_STAFF_NAME, staff_name);
        values.put(KEY_NUMBER, number);

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
                ls += (cursor.getString(3))+" ";
                ls += (cursor.getString(4))+" ";
                ls += (cursor.getInt(5))+"\n";

            } while (cursor.moveToNext());
        }
        db.close();
        return ls;
    }

    /**
     * Check the users password
     * @param uname The username
     * @param pass the user's password
     * @return int (-1,0,1) = (user not in the databse, password incorrect, password correct)
     */
    public int checkPassUser(String uname, String pass){
        Log.v("PASS", pass);
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            Log.v("PASS", pass+" "+cursor.getString(2));
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
     * Get the user's name
     * @param uname the user's username (Staff Number)
     * @return String the user's name
     */
    public String getNameUser(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getString(4);
        } else {
            db.close();
            return "false"; //user not in the database
        }
    }

    /**
     * Get the user's telephone number
     * @param uname the user's username
     * @return String the user's telephone number
     */
    public String getTelUser(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + USER_TABLE_NAME+" WHERE "+KEY_UN+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);
        CaseStudy cs = null;
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getString(5);
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
            db.update(USER_TABLE_NAME, values, KEY_UN+"=?", new String[] {uname});
            db.close();
            return true;
        } else {
            db.close();
            return false; //user not in the database
        }
    }

    public long writeRowHistory(int CSPK, String Hist, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, CSPK);
        values.put(HIST_KEY, Hist);
        values.put(KEY_UN, username);

        long newRowId = db.insert(
                HIST_TABLE_NAME,
                "null",
                values);
        Log.v("database", newRowId + "");
        db.close();
        return newRowId;
    }

    public List<History> getMyHist(String username){
        //return new LinkedList<History>();
        List<History> hl = new LinkedList<History>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + HIST_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        History hist = null;
        if (cursor.moveToFirst()) {
            do {
                hist = new History(cursor.getInt(1), cursor.getString(3));

                // Add case study to case study list
                hl.add(hist);
            } while (cursor.moveToNext());
        }
        db.close();
        return hl;
    }

}
