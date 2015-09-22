package com.hcs.prototype.hcs_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;

public class FinishCSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_cs);
        ((TextView)findViewById(R.id.score)).append(CaseStudy.getCS().getScore()+"");
        for (int i = 0; i < CaseStudy.getCS().getTips().length(); i++){
            try {
                ((TextView) findViewById(R.id.tips)).append(CaseStudy.getCS().getTips().get(i) + "\n");
            } catch (JSONException e){

            }
        }
    }

    public void buttonOnClick(View v)
    {
    // do something when the button is clicked
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        CaseStudy.clearCS();
        finish();
    }
    /**
     * Handle back button pressed
     */
    @Override
    public void onBackPressed() {
        CaseStudy.clearCS();
        finish();
    }
}
