package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    private long PRIMARY_KEY;
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
     * JSON Object describing the case study
     */
    private JSONObject JSONobj = null;
    /**
     * History object - stores the progress on the case study
     */
    private History hist = null;
    /**
     * <h2>Case Study cs_ = null<h2>
     * The CaseStudy object that represents the currently stored user, the class stores its own object - necessary for being a singleton.
     */
    private static CaseStudy cs_ = null;
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
     * @param loc the location of the case study
     * @param context the context for accessing the database file
     */
    public CaseStudy (String loc, Context context){
        this.location = loc;
        this.hist = new History();
        this.context = context;
        this.type = "DISK";
        this.cacheJSON();
        this.PRIMARY_KEY = CaseStudy.addCaseStudy(this.id, this.getJSONName(), this.getJSONDesc(), loc, this.type);
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
        this.hist = new History(pk);
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
        this.hist = new History(pk, id, context);
        this.cacheJSON(); //Cache the JSON data
        //this.context = context;
        //this.save(); //save new case study to database
    }
    /**
     * Returns the case study's key in the database
     * @return int this.PRIMARY_KEY the primary key of the case study in the database
     */
    public long getPrimaryKey(){
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
     * Get the case study description
     * @return String the description
     */
    public String getDesc(){ return this.description; }

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
    public static CaseStudy getCaseStudy(long pk, Context context){
        database = new CaseStudyDatabase(context);
        if (database == null){
            cs_ = new CaseStudy();
            return cs_;
        } else {
            cs_ = database.getCaseStudy(pk);
            return cs_;
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
        return "Name: "+this.name+"\n"+"ID: "+this.id+"\nDescription: "+this.description;
    }
    /**
     * Get the JSON data for the Case Study
     * @return boolean indicates whether the case study data is loaded
     */
    private boolean cacheJSON(){
        if (this.getType().equals("LOCAL")){
            try {
                JSONobj = (new JSONObject(this.loadJSONFromAsset(this.getLocation())));
                Iterator<String> iter = JSONobj.keys();
                this.id = iter.next();

                JSONobj = JSONobj.getJSONObject(this.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        } else if (this.getType().equals("DISK")){
            try {
                JSONobj = (new JSONObject(this.loadJSONFromStorage(this.getLocation())));
                Iterator<String> iter = JSONobj.keys();
                this.id = iter.next();

                JSONobj = JSONobj.getJSONObject(this.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    /**
     * Get the name of Case Study from the JSON file
     * @return String the name of the Case study
     */
    public String getJSONName(){
      if (JSONobj != null){
          try {
              return JSONobj.getJSONObject("metadata").getString("name");
          } catch (JSONException e) {
              e.printStackTrace();
              return "false";
          }

      } else {
          return "false";
      }
    }
    /**
     * Get the description of Case Study from the JSON file
     * @return String the description of the Case study
     */
    public String getJSONDesc(){
        if (JSONobj != null){
            try {
                return JSONobj.getJSONObject("metadata").getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
                return "false";
            }

        } else {
            return "false";
        }
    };

    /**
     * Get the start string of Case Study from the JSON file
     * @return String the start of the Case study
     */
    public String getStart(){
        if (JSONobj != null){
            try {
                return JSONobj.getJSONObject("casestudy").getString("start");
            } catch (JSONException e) {
                e.printStackTrace();
                return "false";
            }

        } else {
            return "false";
        }
    };

    /**
     * Get 4 random questions to ask
     * @return String[][] array of 4 questions and array of their keys
     */
    public String[][] getNextButtons(){
        String[][] failure = {{"false"}};
        String[][] success;
        JSONObject used = new JSONObject();
        Random randomGenerator = new Random();
        if (JSONobj != null){
            try {
                JSONArray qs = JSONobj.getJSONObject("casestudy").getJSONArray("questions");
                success = new String[2][Math.min(qs.length()-hist.length(), 4)];
                int n = 0;
                for (int i = 0; i < Math.min(qs.length()-hist.length(), 4); i++){
                    int randomInt = randomGenerator.nextInt(qs.length()-hist.length());
                    try{
                        while (used.getBoolean(randomInt+"")){
                            randomInt = randomGenerator.nextInt(qs.length()-hist.length());
                        }
                    } catch (JSONException e){
                        used.put(randomInt+"", true);
                    }
                    for (int j = 0; j< qs.length(); j++){
                        if (!hist.inHist(j+"")){
                            if (randomInt == 0){
                                Log.v("QUESTION", qs.getJSONObject(j).getString("question"));
                                success[0][n] = qs.getJSONObject(j).getString("question");
                                success[1][n] = j+"";
                                n++;
                                break;
                            } else {
                                randomInt--;
                            }
                        }
                    }
                }
                return success;
            } catch (JSONException e) {
                e.printStackTrace();
                return failure;
            }

        } else {
            return failure;
        }
    }

    /**
     * Get answer to a specific question
     * @param key the key of the question
     * @return String array contains the type of answer, the text answer and the string representation of the array of image links
     */
    public String[] getAnswer(String key){
        hist.addStep(key);
        String[] ans = new String[6];
        try {
            if (JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getString("type").equals("text")){
                ans[0] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getString("type");
                ans[1] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getString("answer");
                return ans;
            } else if (JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getString("type").equals("img")) {
                ans[0] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getString("type");
                ans[1] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getJSONObject("answer").getString("desc");
                ans[2] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getJSONObject("answer").getJSONArray("img").toString();
                ans[3] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getString("quiz");
                ans[4] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getString("quiz_answer");
                ans[5] = JSONobj.getJSONObject("casestudy").getJSONArray("questions").getJSONObject(Integer.valueOf(key)).getJSONArray("quiz_possible").toString();
                return ans;
            } else {
                return ans;
            }
        } catch (JSONException e){
            return ans;
        }
    }

    /**
     * get the possible diagnosis //TODO: randomly order diags
     * @return String[] The list of diagnosis
     */
    public String[][] getDiags(){
        String[][] diag = {{"false"}};
        try {
            //diag = new String[1];
            diag = new String[1+JSONobj.getJSONObject("casestudy").getJSONObject("diagnosis").getJSONArray("possible").length()][1+JSONobj.getJSONObject("casestudy").getJSONObject("diagnosis").getJSONArray("possible").length()];
            diag[0][0] = JSONobj.getJSONObject("casestudy").getJSONObject("diagnosis").getString("correct");
            diag[1][0] = "0";
            for (int i = 1; i<= JSONobj.getJSONObject("casestudy").getJSONObject("diagnosis").getJSONArray("possible").length(); i++){
                diag[0][i] = JSONobj.getJSONObject("casestudy").getJSONObject("diagnosis").getJSONArray("possible").getString(i-1);
                diag[1][i] = i+"";
            }
            return diag;
        } catch (JSONException e){
            return diag;
        }
    }

    /**
     * Check that the diagnosis is correct //TODO: Make this general for randomly ordered diags
     * @param key the key to the diagnosis
     * @return
     */
    public boolean checkDiag (String key){
        if (key.equals("0")){
            hist.addAnswer(key, true);
            hist.save();
            return true;
        } else {
            hist.addAnswer(key, false);
            return false;
        }
    }

    public void addQuizAns(String key, String ans){
        if (ans.equals("0")){
            hist.addImageAnswer(key, ans, true);
        } else {
            hist.addImageAnswer(key, ans, false);
        }

    }

    /**
     * get the current Case Study object
     * @return CaseStudy Case study singleton
     * @see User
     */
    public static CaseStudy getCS() {
        if (cs_ == null){
            return null;
        }
        return cs_;
    }

    /**
     * Set the case study singleton to null
     */
    public static void clearCS() {
        cs_ = null;
    }

    /**
     * Return the score for this case study
     * @return the score for the case study
     */
    public int getScore(){
        return hist.getScore();
    }

    public JSONArray getTips(){
        try{
            return JSONobj.getJSONArray("tips");
        } catch (JSONException e){
            return new JSONArray();
        }
    }

    /**
     * Get all the questions for the history summary
     * @return JSONArray containing all the questions
     */
    public JSONArray getAllQs(){
        try {
            return JSONobj.getJSONObject("casestudy").getJSONArray("questions");
        } catch (JSONException e){
            return new JSONArray();
        }
    }
}
