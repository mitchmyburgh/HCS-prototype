package com.hcs.prototype.hcs_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class FinishCSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_cs);
    }

    public void buttonOnClick(View v)
    {
    // do something when the button is clicked
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        finish();
    }
}
