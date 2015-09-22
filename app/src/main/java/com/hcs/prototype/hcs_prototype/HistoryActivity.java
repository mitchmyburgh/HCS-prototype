package com.hcs.prototype.hcs_prototype;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class HistoryActivity extends AppCompatActivity {
    TextView histDataDisplay;
    int numViews = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        histDataDisplay = (TextView)findViewById(R.id.data_display_hist);
        int id = getIntent().getExtras().getInt("ID");
        History hist = (new CaseStudyDatabase(this)).getMyHist(User.getUser().getUsername()).get(id);
        CaseStudy cs = hist.getCaseStudy(this);
        JSONArray questions = cs.getAllQs();
        JSONObject answers = hist.getHist();
        histDataDisplay.append(Html.fromHtml("<b>"+cs.getDesc()+"<br><br>"+cs.getStart()+"</b><br>"));
        try {
            Log.v("INNER", answers.getJSONObject("questions").toString());
            Iterator<String> iter = answers.getJSONObject("questions").keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    JSONArray value = answers.getJSONObject("questions").getJSONArray(key);
                    Log.v("INNER", questions.toString());
                    JSONObject q = questions.getJSONObject(Integer.parseInt(key));


                    if (q.getString("type").equals("text")){
                        histDataDisplay.append(Html.fromHtml(q.getString("question")+"<br><b>"+q.getString("answer")+"</b><br>"));
                    } else {
                        //add answer to text view
                        histDataDisplay.append(Html.fromHtml("<b>" + cs.getAnswer(key)[1] + "</b><br>"));
                        //get the main layout
                        LinearLayout ll = (LinearLayout) findViewById(R.id.hist_view);
                        int len; //number of images
                        if (cs.getType().equals("LOCAL")){ //get image from assets
                            try {
                                String [] images = cs.getAnswer(key)[2].split(","); // array of image uris
                                Log.v("PICS", cs.getAnswer(key)[2]);
                                for(String f1 : getAssets().list("")){
                                    Log.v("names",f1);
                                }
                                //add each image
                                for (String img : images) {
                                    InputStream istr = this.getAssets().open(img.replace("[", "").replace("]", "").replace("\"", "").replace("\\", ""));
                                    Log.v("names",img.replace("[", "").replace("]", "").replace("\"", "").replace("\\", ""));
                                    ImageView iv = new ImageView(this);
                                    Bitmap b = ((BitmapDrawable) Drawable.createFromStream(istr, null)).getBitmap();
                                    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 300, 300, false); // scale for speed
                                    iv.setImageDrawable(new BitmapDrawable(getResources(), bitmapResized));
                                    ll.addView(iv, numViews);
                                    numViews++;
                                }
                                histDataDisplay = new TextView(this);
                                ll.addView(histDataDisplay, numViews);
                                numViews++;
                                Log.v("PICS2", cs.getAnswer(key)[2]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else { //get image from external storage
                            try {
                                String path = new File(cs.getLocation()).getPath().substring(0, cs.getLocation().lastIndexOf(File.separator)); //get path
                                String [] images = cs.getAnswer(key)[2].split(",");
                                len = images.length;
                                for (String img : images) {
                                    FileInputStream istr = new FileInputStream(new File(path+"/"+img.replace("[", "").replace("]", "").replace("\"", "").replace("\\", "")));
                                    ImageView iv = new ImageView(this);
                                    Bitmap b = ((BitmapDrawable)Drawable.createFromStream(istr, null)).getBitmap();
                                    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 300, 300, false);
                                    iv.setImageDrawable(new BitmapDrawable(getResources(), bitmapResized));
                                    ll.addView(iv, numViews);
                                    numViews++;
                                }
                                histDataDisplay = new TextView(this);
                                ll.addView(histDataDisplay, numViews);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        histDataDisplay.append(Html.fromHtml("<b>" + cs.getAnswer(key)[3] + "</b><br>"));
                        for (int i = 0; i <answers.getJSONObject("questions").getJSONArray(key).length(); i++){
                            if (!answers.getJSONObject("questions").getJSONArray(key).getString(i).equals("0")) {
                                histDataDisplay.append(Html.fromHtml("<font color='#EE0000'>" + q.getJSONArray("quiz_possible").getString(Integer.parseInt(answers.getJSONObject("questions").getJSONArray(key).getString(i)) - 1) + ": is wrong</font><br>"));
                            }
                        }
                        Log.v("HELP ME", q.toString());
                        histDataDisplay.append(Html.fromHtml("<b>" + q.getString("quiz_answer") + "</b><br>"));


                    }

                } catch (JSONException e) {
                    // Something went wrong!
                    Log.v("INNER", "error");
                }
                /*for (int i = 0; i <answers.getJSONObject("answer").length(); i++){
                    if (!answers.getJSONArray("answer").getString(i).equals("0")){
                        histDataDisplay.append(Html.fromHtml("<font color='#EE0000'>" + cs.getDiags()[0][Integer.parseInt(answers.getJSONArray("answer").getString(i)) - 1] + ": is wrong</font><br>"));
                    }
                }
                histDataDisplay.append(Html.fromHtml("<b>" + cs.getDiags()[0][0] + "</b><br>"));*/
            }
        } catch (JSONException e){
            Log.v("OUTER", "error");
        }
    }

}
