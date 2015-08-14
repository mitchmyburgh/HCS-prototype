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
    private static final int DATABASE_VERSION = 15;
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
    private static Context context = null;
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
        db.execSQL(CASE_STUDY_TABLE_CREATE);
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, "id0");
        values.put(KEY_NAME, "name");
        values.put(KEY_DESC, "desc");
        values.put(KEY_LOCATION, "CaseStudy01.json");
        values.put(KEY_TYPE, "LOCAL");

        long newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
        values = new ContentValues();
        values.put(KEY_CASE_ID, "id1");
        values.put(KEY_NAME, "name");
        values.put(KEY_DESC, "desc");
        values.put(KEY_LOCATION, "CaseStudy02.json");
        values.put(KEY_TYPE, "LOCAL");

        db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
        values = new ContentValues();
        values.put(KEY_CASE_ID, "id2");
        values.put(KEY_NAME, "name");
        values.put(KEY_DESC, "desc");
        values.put(KEY_LOCATION, "/storage/emulated/0/CaseStudy01.json");
        values.put(KEY_TYPE, "DISK");

        db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
        //this.db = db;
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
        db.execSQL("DROP TABLE IF EXISTS " + CASE_STUDY_TABLE_NAME);
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
        Log.v("DATABASE", "onOPEN");
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, "id");
        values.put(KEY_NAME, "name");
        values.put(KEY_DESC, "desc");
        values.put(KEY_LOCATION, "CaseStudy01.json");
        values.put(KEY_TYPE, "LOCAL");

        long newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
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
    public CaseStudy getCaseStudy(int pk){
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

}
