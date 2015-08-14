package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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
     * The case study description
     */
    private String description;
    /**
     * The context which is necessary for loading the database (acceptable to pass the current activity with this)
     */
    private Context context = null;
    /**
     * The databse reference for reading/writing data (static so can be accessed as a singleton from any activity) 
     */
    private static CaseStudyDatabase database = null;

    /**
     * Default Contructor to create a new case study object using the primary key
     */
    public CaseStudy (){
        this.PRIMARY_KEY = -1;
        this.id = "-1";
        this.name = "name";
        this.description = "description";
        this.location = "location";
        this.type = "type";
        //this.load();
    }
    /**
     * Create a new CaseStudy object
     * @param id the id for finding the case study on disk
     * @param context the context for accessing the database file
     */
    public CaseStudy (String id, Context context){
        this.id = id;
        this.context = context;
        //this.load(); //load data from the database
    }

    /**
     * create a new CaseStudy object
     * @param id the unique id for the case study
     * @param name the display name of the case study
     * @param type the type of the case study (local = loaded from inside apk, disk = loaded from teh internal storage of the device)
     * @param location the location on the disk of the case study
     */
    public CaseStudy (int pk, String id, String name, String description,  String location, String type){
        this.PRIMARY_KEY = pk;
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        //this.cacheJSON(); //Cache the JSON data
        //this.context = context;
        //this.save(); //save new case study to database
    }
    /**
     * create a new CaseStudy object
     * @param id the unique id for the case study
     * @param name the display name of the case study
     * @param type the type of the case study (local = loaded from inside apk, disk = loaded from teh internal storage of the device)
     * @param location the location on the disk of the case study
     * @param context trhe context for accesing files
     */
    public CaseStudy (int pk, String id, String name, String description,  String location, String type, Context context){
        this.PRIMARY_KEY = pk;
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.context = context;
        this.cacheJSON(); //Cache the JSON data
        //this.context = context;
        //this.save(); //save new case study to database
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
     * Get the type of the Case Study (local/disk)
     * @return String this.type teh type of the case study
     */
    public String getType(){
        return this.type;
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
    public static CaseStudy getCaseStudy(int pk, Context context){
        database = new CaseStudyDatabase(context);
        if (database == null){
            return new CaseStudy();
        } else {
            return database.getCaseStudy(pk);
        }
    }
    
    /**
     * Initialises the database
     * @param context the context used for accessing the database
     * @return boolean indicates whether initialisation was successful
     */
    public static boolean createDatabase(Context context){
        database = new CaseStudyDatabase(context);
        if (database == null){
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
    public static long addCaseStudy(String id, String name, String desc, String location, String type){
        if (database == null){
            return -1;
        } else {
            return database.writeRow(id, name, desc, location, type);
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

    /**
     * Get a list containg all teh case studies in the database
     * @return List<CaseStudy> containg all the case studies in the database
     */
    public static List<CaseStudy> getAllCaseStudy(){
        if (database == null){
            return new LinkedList<CaseStudy>();
        } else {
            return database.getAllCaseStudy();
        }
    }

    /**
     * Convert object to string representation
     * @return String The string representation of the object
     */
    @Override
    public String toString(){
        return this.id+", "+this.name+"\n"+this.description;
    }
    /**
     * Get the JSON data for the Case Study
     * @return boolean indicates whether the case study data is loaded
     */
    private boolean cacheJSON(){
        if (this.getType().equals("LOCAL")){
            this.loadJSONFromAsset(this.getLocation());
            return true;
        } else if (this.getType().equals("DISK")){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Load The JSON File as a String
     * @param fname The location of the assest
     * @return String the string representation of the JSON file
     */
    public String loadJSONFromAsset(String fname) {
        String json = null;
        try {
            final AssetManager assets = this.context.getAssets();
            final String[] names = assets.list("");
            for (int i = 0; i< names.length; i++){
                Log.v("HELP", names[i]);
            }
            InputStream is = this.context.getAssets().open(fname);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.v("HELP", "HELP");
            return null;
        }
        return json;
    }

    /**
     * Load the JSON file as a String
     * @param fname The filename
     * @return String the JSON represented as as a string
     */
    public String loadJSONFromStorage(String fname){
        File sdcard = Environment.getExternalStorageDirectory();
        Log.v("EXTFILE", sdcard.getPath());
        File file = new File(fname);
        //file = new File(fname);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e) {
            Log.v("EXTFILE", "FAILURE IS AN OPTION");
        }
        return text.toString();
    }

}
