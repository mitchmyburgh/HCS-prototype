package com.hcs.prototype.hcs_prototype;

import android.content.Context;

/**
 * Created by mitch on 2015/08/13.
 */
public class CaseStudy {
    private String id;
    private String name;
    private String location;
    private String type = "local";
    private Context context = null;
    private static CaseStudyDatabase database = null;

    /**
     * Create a new CaseStudy object
     * @param id the id for finding the case study on disk
     */
    public CaseStudy (String id, Context context){
        this.id = id;
        this.context = context;
        this.load();
    }

    /**
     * create a new CaseStudy object
     * @param id the unique id for the case study
     * @param name the display name of the case study
     * @param type the type of the case study (local = loaded from inside apk, disk = loaded from teh internal storage of the device)
     * @param location the location on the disk of teh case study
     */
    public CaseStudy (String id, String name, String type, String location, Context context){
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.context = context;
        this.save();
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
     * @return boolean - save successful?
     */
    public boolean save(){
        return true;
    }

    /**
     * Load the case study data from the database
     * @return boolean - load successful?
     */
    public boolean load(){
        return true;
    }

    public static boolean createDatabase(Context context){
        database = new CaseStudyDatabase(context);
        if (database == null){
            return false;
        } else {
            return true;
        }
    }

    public static long addCaseStudy(String id, String name, String type, String location){
        if (database == null){
            return -1;
        } else {
            return database.writeRow(id, name, type, location);
        }
    }
    public static String getAllCaseString(){
        if (database == null){
            return "false";
        } else {
            return database.getRowsString();
        }
    }
}
