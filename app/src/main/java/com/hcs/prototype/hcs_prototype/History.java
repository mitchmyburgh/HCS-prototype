package com.hcs.prototype.hcs_prototype;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

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

        try {
            hist.put("answer", new JSONArray());
        } catch (JSONException e) {
        }
    }

    /**
     * Add a step (button press) to history
     * @param key teh question asked key
     * @return boolean indicates success or failure
     */
    public boolean addStep(String key) {
        if (this.inHist(key)) {
            return false;
        }
        try {
            hist.put(key, new JSONArray());
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Add answer to an image question numbered key with answer number ans
     * @param key the number of the question in the Case Study
     * @param ans the number of the answer to the question
     * @return boolean if the insert was successful
     */
    public boolean addImageAnswer(String key, String ans){
        try {
            hist.getJSONArray(key).put(ans);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     *
     * @param ans
     * @return
     */
    public boolean addAnswer(String ans){
        try {
            hist.getJSONArray("answer").put(ans);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Check if step is in history
     * @param key the question asked key
     * @return boolean indicates if the key is in the history
     */
    public boolean inHist(String key){
        return hist.has(key);
    }

    /**
     * Get the length of the History object
     * @return int the length of the history object
     */
    public int length(){
        return hist.length();
    }

}
