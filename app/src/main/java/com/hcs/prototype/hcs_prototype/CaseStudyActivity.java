package com.hcs.prototype.hcs_prototype;

import android.content.Intent;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CaseStudyActivity extends AppCompatActivity implements View.OnClickListener {
    CaseStudy cs;
    TextView csDescDisplay;
    TextView csDataDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_study);
        int id = getIntent().getExtras().getInt("ID");
        //new AlertDialog.Builder(this).setTitle("title").setMessage(id+"").show();
        cs = CaseStudy.getCaseStudy(id, this);
        //new AlertDialog.Builder(this).setTitle("title").setMessage(cs.loadJSONFromAsset(cs.getLocation())).show();
        csDescDisplay = (TextView)findViewById(R.id.desc_display);
        csDataDisplay = (TextView)findViewById(R.id.data_display);
        Button[] buts = new Button[4];
        buts[0] = (Button)findViewById(R.id.question_button_1);
        buts[1] = (Button)findViewById(R.id.question_button_2);
        buts[2] = (Button)findViewById(R.id.question_button_3);
        buts[3] = (Button)findViewById(R.id.question_button_4);
        setTitle(cs.getJSONName());
        csDescDisplay.setTypeface(null, Typeface.BOLD);
        csDescDisplay.setText(cs.getJSONDesc() + "\n");
        csDataDisplay.setText(Html.fromHtml("<b>" + cs.getStart() + "</b><br>" ));
        String[][] sa = cs.getNextButtons();
        for (int i = 0; i < sa[0].length; i++){
            //csDataDisplay.append(sa[0][i] + "\n");
            buts[i].setText( sa[0][i]);
            buts[i].setTag(sa[1][i]);
        }
        for (int i = sa[0].length; i <4; i++){
            buts[i].setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_case_study, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * ask a question
     */
    public void askQuestion(View view) {
        csDataDisplay.append(((Button) view).getText().toString() + "\n");
        if (cs.getAnswer((String)view.getTag())[0].equals("text")) {
            csDataDisplay.append(Html.fromHtml("<b>"+cs.getAnswer((String) view.getTag())[1]+"</b><br>"));
        } else {
            csDataDisplay.append(Html.fromHtml("<b>"+cs.getAnswer((String) view.getTag())[1]+"</b><br>"));
            csDataDisplay.append(Html.fromHtml("<b>" + cs.getAnswer((String) view.getTag())[2].split(",")[0].replace("[", "").replace("]", "")+" : " +cs.getAnswer((String) view.getTag())[2] + "</b><br>"));
            if (cs.getType().equals("LOCAL")){ //get image from assets
                int len;
                try {
                    String [] images = cs.getAnswer((String) view.getTag())[2].split(",");
                    len = images.length;
                    LinearLayout ll = (LinearLayout) findViewById(R.id.cs_view);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    for (String img : images) {
                        InputStream istr = this.getAssets().open(img.replace("[", "").replace("]", "").replace("\"", "").replace("\\", ""));
                        ImageView iv = new ImageView(this);
                        Bitmap b = ((BitmapDrawable)Drawable.createFromStream(istr, null)).getBitmap();
                        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 300, 300, false);


                        iv.setImageDrawable(new BitmapDrawable(getResources(), bitmapResized));

                        ll.addView(iv, 2);
                    }
                    csDataDisplay = new TextView(this);
                    ll.addView(csDataDisplay, 2+len);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                int len;
                try {
                    String path = new File(cs.getLocation()).getPath().substring(0, cs.getLocation().lastIndexOf(File.separator));;
                    String [] images = cs.getAnswer((String) view.getTag())[2].split(",");
                    len = images.length;
                    LinearLayout ll = (LinearLayout) findViewById(R.id.cs_view);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    for (String img : images) {
                        FileInputStream istr = new FileInputStream(new File(path+"/"+img.replace("[", "").replace("]", "").replace("\"", "").replace("\\", "")));
                        ImageView iv = new ImageView(this);
                        Bitmap b = ((BitmapDrawable)Drawable.createFromStream(istr, null)).getBitmap();
                        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 300, 300, false);


                        iv.setImageDrawable(new BitmapDrawable(getResources(), bitmapResized));

                        ll.addView(iv, 2);
                    }
                    csDataDisplay = new TextView(this);
                    ll.addView(csDataDisplay, 2+len);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*File imgFile = new File(cs.getAnswer((String) view.getTag())[2].split(",")[0].replace("[", "").replace("]", ""));
            if(imgFile.exists()){
                Log.v("TEST", "ITS WORKING");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView iv = new ImageView(this);

                iv.setImageBitmap(myBitmap);
                LinearLayout ll = (LinearLayout)findViewById(R.id.cs_view);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(iv, lp);

            }*/

        }
        Button[] buts = new Button[4];
        buts[0] = (Button)findViewById(R.id.question_button_1);
        buts[1] = (Button)findViewById(R.id.question_button_2);
        buts[2] = (Button)findViewById(R.id.question_button_3);
        buts[3] = (Button)findViewById(R.id.question_button_4);
        String[][] sa = cs.getNextButtons();
        for (int i = 0; i < sa[0].length; i++){
            //csDataDisplay.append(sa[0][i] + "\n");
            buts[i].setText(sa[0][i]);
            buts[i].setTag(sa[1][i]);
        }
        for (int i = sa[0].length; i <4; i++){
            buts[i].setVisibility(View.GONE);
        }
    }

    /**
     * Diagnose the disease
     * @param view the button clicked
     */
    public void diagnose(View view){
        Button[] buts = new Button[5];
        buts[0] = (Button)findViewById(R.id.question_button_1);
        buts[1] = (Button)findViewById(R.id.question_button_2);
        buts[2] = (Button)findViewById(R.id.question_button_3);
        buts[3] = (Button)findViewById(R.id.question_button_4);
        buts[4] = (Button)findViewById(R.id.diag_button);
        for (int i = 0; i < buts.length; i++){
            buts[i].setVisibility(View.GONE);
        }
        String[][] diags = cs.getDiags();
        LinearLayout ll = (LinearLayout)findViewById(R.id.cs_view);
        for (int i = 0; i< diags[0].length; i++){
            Button myButton = new Button(this);
            myButton.setText(diags[1][i]+" "+diags[0][i]);
            myButton.setTag(diags[1][i]);
            myButton.setOnClickListener(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
        }

    }
    @Override
    public void onClick(View v) {
        if (cs.checkDiag((String)v.getTag())){
            Button b = (Button)v;
            new AlertDialog.Builder(this)
                    .setTitle("WOOOOOO You got it right")
                    .setMessage("The Person had" +b.getText()).show();
            UserNormal.getUser().incScore(100);
            Intent intent = new Intent(this, FinishCSActivity.class);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("You got it wrong")
                    .setMessage("You Are Wrong").show();
        }
    }

}
