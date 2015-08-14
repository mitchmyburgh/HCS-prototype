package com.hcs.prototype.hcs_prototype;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * <h1>Case Study Database Class<h1>
 * The CaseStudyDatabase class extends android.database.sqlite.SQLiteOpenHelper and provides methods for reading and writing to the case study table in the database
 */
public class CaseStudyDatabase extends SQLiteOpenHelper {
    /**
     * Current version of the database, android uses this to indicate when to call onUpgrade
     */
    private static final int DATABASE_VERSION = 7;
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
    private static final String KEY_LOCATION = "location";
    private static final String KEY_TYPE = "type";
    private static final String CASE_STUDY_TABLE_CREATE =
            "CREATE TABLE " + CASE_STUDY_TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_CASE_ID + " TEXT UNIQUE, " +
                    KEY_NAME + " TEXT, " +
                    KEY_LOCATION + " TEXT, " +
                    KEY_TYPE + " TEXT);";
    //private SQLiteDatabase db;
    public CaseStudyDatabase (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("DATABASE", "onCREATE");
        db.execSQL(CASE_STUDY_TABLE_CREATE);
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, "id");
        values.put(KEY_NAME, "name");
        values.put(KEY_LOCATION, "location");
        values.put(KEY_TYPE, "type");

        long newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
        //this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int n, int i){
        Log.v("DATABASE", "onUPGRADE");
        Log.v("DATABASE",  CASE_STUDY_TABLE_CREATE);
        Log.v("DATABASE", CASE_STUDY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CASE_STUDY_TABLE_NAME);
        this.onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        //db.execSQL(CASE_STUDY_TABLE_CREATE);
        //this.db = db;
        Log.v("DATABASE", "onOPEN");
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, "id");
        values.put(KEY_NAME, "name");
        values.put(KEY_LOCATION, "location");
        values.put(KEY_TYPE, "type");

        long newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
    }
    public long writeRow(String id, String name, String type, String location){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_CASE_ID, id);
        values.put(KEY_NAME, name);
        values.put(KEY_LOCATION, location);
        values.put(KEY_TYPE, type);

        long newRowId = db.insert(
                CASE_STUDY_TABLE_NAME,
                "null",
                values);
        Log.v("database", newRowId+"");
        db.close();
        return newRowId;
    }
    public String getRowsString(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + CASE_STUDY_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String ls = "";
        if (cursor.moveToFirst()) {
            do {
                ls += cursor.getInt(0)+" ";
                ls += (cursor.getString(1))+" ";
                ls += (cursor.getString(2))+" ";
                ls += (cursor.getString(3))+" ";
                ls += (cursor.getString(4))+"\n";

            } while (cursor.moveToNext());
        }
        db.close();
        return ls;
    }

}
