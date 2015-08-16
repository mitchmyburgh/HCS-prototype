package com.hcs.prototype.hcs_prototype;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mitch on 2015/08/15.
 */
public class History {
    /**
     * The JSON object holding the users history
     */
    private JSONObject hist = null;
    /**
     * The Primary Key of the case study
     */
    private int CSPK;
    /**
     * The id of the case study
     */
    private String id;
    /**
     * Default Constructor
     */
    public History(){
        id = "-1";
        CSPK = -1;
        hist = new JSONObject();
    }

    /**
     * Constructor
     * @param pk The primary key of the case study
     * @param id The id of the case study
     */
    public History(int pk, String id){
        this.CSPK = pk;
        this.id = id;
        hist = new JSONObject();
    }

    /**
     * Add a step (button press) to history
     * @param key teh question asked key
     * @return boolean indicates success or failure
     */
    public boolean addStep(String key){
        if (this.inHist(key)){
            return false;
        }
        try {
            hist.put(key, true);
            return true;
        } catch (JSONException e){
            return false;
        }
    }

    /**
     * Check if step is in history
     * @param key the question asked key
     * @return boolean indicates if the key is in the history
     */
    public boolean inHist(String key){
        try {
            return hist.getBoolean(key);
        } catch (JSONException e){
            return false;
        }
    }

    /**
     * Get the length of the History object
     * @return int the length of the history object
     */
    public int length(){
        return hist.length();
    }

}
