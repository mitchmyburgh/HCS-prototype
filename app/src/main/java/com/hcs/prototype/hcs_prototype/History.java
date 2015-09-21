package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.util.Log;

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
     * The context for accessing the databse
     */
    private Context context = null;

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
     */
    public History(int pk){
        this.CSPK = pk;
        hist = new JSONObject();

        try {
            hist.put("answer", new JSONArray());
            hist.put("score", 100);
        } catch (JSONException e) {
        }
    }
    /**
     * Constructor
     * @param pk The primary key of the case study
     * @param json The json data for teh case study
     */
    public History(int pk, String json){
        this.CSPK = pk;
        try {
            hist = new JSONObject(json);
        } catch (JSONException e) {
            hist = new JSONObject();
        }

    }

    /**
     * Constructor
     * @param pk The primary key of the case study
     * @param id The id of the case study
     * @param c The context for accessing the databse
     */
    public History(int pk, String id, Context c){
        this.CSPK = pk;
        this.id = id;
        this.context = c;
        hist = new JSONObject();

        try {
            hist.put("answer", new JSONArray());
            hist.put("score", 100);
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
    public boolean addImageAnswer(String key, String ans, boolean right){
        try {
            hist.getJSONArray(key).put(ans);
            if (!right){
                hist.put("score", hist.getInt("score")-5);
            }
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
    public boolean addAnswer(String ans, boolean right){
        try {
            hist.getJSONArray("answer").put(ans);
            if (!right){
                hist.put("score", hist.getInt("score")-10);
            }
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Get the score for this case study
     * @return the score for this case study
     */
    public int getScore(){
        try {
            return hist.getInt("score");
        } catch (JSONException e) {
            return -1;
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
        Log.v("LENGTH", (hist.length()-2)+"");
        return hist.length()-2;
    }

    public boolean save(){
        if ((new CaseStudyDatabase(context)).writeRowHistory(this.CSPK, this.hist.toString(), User.getUser().getUsername())!= -1){
            return true;
        } else {
            return false;
        }
    }

    public String toString(Context context){
        return ((CaseStudy)(new CaseStudyDatabase(context)).getCaseStudy(this.CSPK)).getJSONName()+" SCORE: "+this.getScore();
    }

}
