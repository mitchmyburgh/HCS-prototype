package com.hcs.prototype.hcs_prototype;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CaseStudyActivity extends AppCompatActivity {
    CaseStudy cs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_study);
        int id = getIntent().getExtras().getInt("ID");
        //new AlertDialog.Builder(this).setTitle("title").setMessage(id+"").show();
        cs = CaseStudy.getCaseStudy(id, this);
        //new AlertDialog.Builder(this).setTitle("title").setMessage(cs.loadJSONFromAsset(cs.getLocation())).show();
        TextView csDisplay = (TextView)findViewById(R.id.json_display);
        if (cs.getType().equals("LOCAL")) {
            csDisplay.setText(cs.getJSONName());
        } else {
            csDisplay.setText(cs.getJSONName());
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


}
