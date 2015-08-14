package com.hcs.prototype.hcs_prototype;

import android.content.Context;

/**
 * <h1>Case Study Class</h1>
 * The CaseStudy class implements case study related functions.
 * Static methods provide access to the case study database and allow the user to write/read from the database.
 * The case study object allows the user to manipulate the case study objects and manipulate their data
 * 
 * @author Mitch Myburgh
 * @version %I%, %G%
 * @since 0.2 //TODO: version numbers in github
 */
public class CaseStudy {
    /**
     * The primary key of teh case study in the database
     */
    private int PRIMARY_KEY;
    /**
     * The id of the Case Study, this must be unique and produced by the case study creator //TODO: generator code? 
     */
    private String id;
    /**
     * The name of the case study for user's to easily identify which case study they are using  
     */
    private String name;
    /**
     * The location on device or in the apk of the casestudy.json file  
     */
    private String location;
    /**
     * The type of case study location (local for case studies within the apk and disk for files loaded from device storage)
     */
    private String type = "local";
    /**
     * The context which is necessary for loading the database (acceptable to pass the current activity with this)
     */
    private Context context = null;
    /**
     * The databse reference for reading/writing data (static so can be accessed as a singleton from any activity) 
     */
    private static CaseStudyDatabase database = null;

    /**
     * Create a new CaseStudy object
     * @param id the id for finding the case study on disk
     * @param context the context for accessing the database file
     */
    public CaseStudy (String id, Context context){
        this.id = id;
        this.context = context;
        this.load(); //load data from the database
    }

    /**
     * create a new CaseStudy object
     * @param id the unique id for the case study
     * @param name the display name of the case study
     * @param type the type of the case study (local = loaded from inside apk, disk = loaded from teh internal storage of the device)
     * @param location the location on the disk of the case study
     * @param context the context for accessing the database file
     */
    public CaseStudy (String id, String name, String type, String location, Context context){
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.context = context;
        this.save(); //save new case study to database
    }
    /**
     * Returns the case study's key in the database
     * @return int this.PRIMARY_KEY the primary key of the case study in the database
     */
    public int getPrimaryKey(){
        return this.PRIMARY_KEY;    
    }
    /**
     * Get the CaseStudy id
     * @return String this.id the id of the CaseStudy
     */
    public String getId(){
        return this.id;
    }

    /**
     * Get the name of the case study
     * @return String this.name the name of the case study
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the location of the case study
     * @return String this.location the location on disk of the case study (json file and images in /img/)
     */
    public String getLocation(){
        return this.location;
    }

    /**
     * Save the Case study data to the database
     * @return boolean indicates whether the save was successful
     */
    public boolean save(){
        return true;
    }

    /**
     * Load the case study data from the database
     * @return boolean indicates whether the load was succesful
     */
    public boolean load(){
        return true;
    }
    
    /**
     * Initialises the database
     * @param context the context used for accessing the database
     * @return boolean indicates whether initialisation was successful
     */
    public static boolean createDatabase(Context context){
        this.database = new CaseStudyDatabase(context);
        if (this.database == null){
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Add a case study to the database
     * @param id the id of the case study
     * @param name the human readable case study name
     * @param type the type of case study (local/disk) //TODO: make this an int and store values in array 
     * @param location teh loacation of the casestudy.json on the disk
     * @return long indicates the row id (-1 indicates a failure)
     */
    public static long addCaseStudy(String id, String name, String type, String location){
        if (database == null){
            return -1;
        } else {
            return database.writeRow(id, name, type, location);
        }
    }
    
    /**
     * Get all the case study data as one long string (for debugging)
     * @return String teh string representing the data in the database ("false" indicates load failure)
     */
    public static String getAllCaseString(){
        if (database == null){
            return "false";
        } else {
            return database.getRowsString();
        }
    }
}
