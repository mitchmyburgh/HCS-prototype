package com.hcs.prototype.hcs_prototype;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class CaseStudyActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * the case study being worked on
     */
    CaseStudy cs;
    /**
     * display the description
     */
    TextView csDescDisplay;
    /**
     * display the data
     */
    TextView csDataDisplay;
    /**
     * current question
     */
    String currentQuestion = "0";
    /**
     * the buttons for image quizzes
     */
    Button[] QuizButs;
    /**
     * Main Buttons list
     */
    Button[] buts;

    /**
     * Number of the TextViews and Image views
     */
    int numViews = 2;


    /**
     * Create the activity
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_study);
        int id = getIntent().getExtras().getInt("ID");//CS id
        //create the case study
        if (CaseStudy.getCS() != null){
            cs = CaseStudy.getCS();
        } else {
            cs = CaseStudy.getCaseStudy(id, this);
        }
        setTitle(cs.getJSONName());
        //Text displays
        csDescDisplay = (TextView)findViewById(R.id.desc_display);
        csDataDisplay = (TextView)findViewById(R.id.data_display);
        //Main Question Buttons
        buts = new Button[5];
        buts[0] = (Button)findViewById(R.id.question_button_1);
        buts[1] = (Button)findViewById(R.id.question_button_2);
        buts[2] = (Button)findViewById(R.id.question_button_3);
        buts[3] = (Button)findViewById(R.id.question_button_4);
        buts[4] = (Button)findViewById(R.id.diag_button);

        //Add the description
        csDescDisplay.setTypeface(null, Typeface.BOLD);
        csDescDisplay.setText(cs.getJSONDesc() + "\n");
        //set start text (introduction to case study)
        csDataDisplay.setText(Html.fromHtml("<b>" + cs.getStart() + "</b><br>" ));
        //add button text fror 4 random questions
        String[][] sa = cs.getNextButtons();
        for (int i = 0; i < sa[0].length; i++){
            buts[i].setText( sa[0][i]);
            buts[i].setTag(sa[1][i]);
        }
        //Hide buttons when there are less than 4 questions
        for (int i = sa[0].length; i <4; i++){
            buts[i].setVisibility(View.GONE);
        }
    }


    /**
     * Ask a question (reponds to button text)
     * @param view the button pressed
     */
    public void askQuestion(View view) {
        csDataDisplay.append(((Button) view).getText().toString() + "\n"); //put question in text display
        currentQuestion = (String)view.getTag(); //the current question clicked
        if (cs.getAnswer((String)view.getTag())[0].equals("text")) { // handle text questions
            csDataDisplay.append(Html.fromHtml("<b>"+cs.getAnswer((String) view.getTag())[1]+"</b><br>")); //add the answer
            //get the next set of buttons
            String[][] sa = cs.getNextButtons();
            for (int i = 0; i < sa[0].length; i++){
                buts[i].setText(sa[0][i]);
                buts[i].setTag(sa[1][i]);
                buts[i].setVisibility(View.VISIBLE);
            }
            for (int i = sa[0].length; i <4; i++){
                buts[i].setVisibility(View.GONE);
            }
        } else { //handle image questions
            //add answer to text view
            csDataDisplay.append(Html.fromHtml("<b>"+cs.getAnswer((String) view.getTag())[1]+"</b><br>"));
            //get the main layout
            LinearLayout ll = (LinearLayout) findViewById(R.id.cs_view);
            int len; //number of images
            if (cs.getType().equals("LOCAL")){ //get image from assets
                try {
                    String [] images = cs.getAnswer((String) view.getTag())[2].split(","); // array of image uris
                    //add each image
                    for (String img : images) {
                        InputStream istr = this.getAssets().open(img.replace("[", "").replace("]", "").replace("\"", "").replace("\\", ""));
                        ImageView iv = new ImageView(this);
                        Bitmap b = ((BitmapDrawable)Drawable.createFromStream(istr, null)).getBitmap();
                        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 300, 300, false); // scale for speed
                        iv.setImageDrawable(new BitmapDrawable(getResources(), bitmapResized));
                        ll.addView(iv, numViews);
                        numViews++;
                    }
                    csDataDisplay = new TextView(this);
                    ll.addView(csDataDisplay, numViews);
                    numViews++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else { //get image from external storage
                try {
                    String path = new File(cs.getLocation()).getPath().substring(0, cs.getLocation().lastIndexOf(File.separator)); //get path
                    String [] images = cs.getAnswer((String) view.getTag())[2].split(",");
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
                    csDataDisplay = new TextView(this);
                    ll.addView(csDataDisplay, numViews);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            csDataDisplay.append(Html.fromHtml("<b>" + cs.getAnswer((String) view.getTag())[3] + "</b><br>"));//ask question about Pictures
            String [] answers = cs.getAnswer((String) view.getTag())[5].split(","); //Picture quiz answers
            int i = 0;
            ll = (LinearLayout) findViewById(R.id.cs_view);
            //correct button
            Button myButton = new Button(this);
            myButton.setText(cs.getAnswer((String) view.getTag())[4]);
            myButton.setTag(i);
            myButton.setOnClickListener(new Button.OnClickListener() { //you got the answer correct
                public void onClick(View v) {
                    csDataDisplay.append(Html.fromHtml("<b style='colour: red'>" + ((Button) v).getText() + "</b><br>"));
                    cs.addQuizAns(currentQuestion, ((Button) v).getTag().toString());
                    for (Button but : QuizButs) {
                        but.setVisibility(View.GONE);
                    }
                    String[][] sa = cs.getNextButtons();
                    for (int i = 0; i < sa[0].length; i++) {
                        buts[i].setText(sa[0][i]);
                        buts[i].setTag(sa[1][i]);
                        buts[i].setVisibility(View.VISIBLE);
                    }
                    buts[4].setVisibility(View.VISIBLE);
                    for (int i = sa[0].length; i < 4; i++) {
                        buts[i].setVisibility(View.GONE);
                    }
                }
            });
            QuizButs = new Button[answers.length+1]; //store the quiz buttons
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            QuizButs[i] = myButton;
            i++;
            for (String ans : answers){
                myButton = new Button(this);
                myButton.setText(ans.replace("[", "").replace("]", "").replace("\"", ""));
                myButton.setTag(i);
                myButton.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        csDataDisplay.append(Html.fromHtml("<font color='#EE0000'>" + ((Button) v).getText() + ": is wrong</font><br>"));
                        cs.addQuizAns(currentQuestion, ((Button) v).getTag().toString());
                    }
                });
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                QuizButs[i] = myButton;

                i++;
            }
            QuizButs = this.shuffleArray(QuizButs);
            for (Button but: QuizButs){
                ll.addView(but, lp);
            }
            for (i = 0; i <5; i++){
                buts[i].setVisibility(View.GONE);
            }

        }

    }

    /**
     * Diagnose the disease
     * @param view the button clicked
     */
    public void diagnose(View view){
        for (int i = 0; i < buts.length; i++){
            buts[i].setVisibility(View.GONE);
        }
        String[][] diags = cs.getDiags();
        LinearLayout ll = (LinearLayout)findViewById(R.id.cs_view);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buts = new Button[diags[0].length];
        for (int i = 0; i< diags[0].length; i++){
            Button myButton = new Button(this);
            myButton.setText(diags[0][i]);
            myButton.setTag(diags[1][i]);
            myButton.setOnClickListener(this);

            buts[i] = myButton;
        }
        buts = this.shuffleArray(buts);
        for (Button but : buts){
            ll.addView(but, lp);
        }

    }

    /**
     * Handle click of the diagnosis
     * @param v the button clicked
     */
    @Override
    public void onClick(View v) {
        if (cs.checkDiag((String)v.getTag())){
            Button b = (Button)v;
            UserNormal.getUser().incScore(cs.getScore());
            //CaseStudy.clearCS();
            Intent intent = new Intent(this, FinishCSActivity.class);
            startActivity(intent);
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("You got it wrong")
                    .setMessage("You Are Wrong").show();
            csDataDisplay.append(Html.fromHtml("<font color='#EE0000'>" + ((Button) v).getText() + ": is wrong</font><br>"));
        }
    }

    /**
     * Implementing Fisher–Yates shuffle
     * @param ar The arrsy to be shuffled
     * @return teh shuffled array
     */
    static Button[] shuffleArray(Button[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Button a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    /**
     * Handle back button pressed
     */
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Exit Case Study?")
                .setMessage("Your Progress will not be saved").setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CaseStudy.clearCS();
                                finish();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event
                                dialog.cancel();
                            }
                        }).show();
        //finish();
        return;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Do nothing on rotate
    }
}
