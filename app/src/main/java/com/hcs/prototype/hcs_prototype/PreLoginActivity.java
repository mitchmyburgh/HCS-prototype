package com.hcs.prototype.hcs_prototype;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;


public class PreLoginActivity extends AppCompatActivity {
    /**
     * Store the current activity so it can be finished on login
     */
    public static Activity me = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = this;
        setContentView(R.layout.activity_prelogin);

        if (User.getCurrentUser(this)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        /*new AlertDialog.Builder(this)
                .setTitle("You got it wrong")
                .setMessage((new CaseStudyDatabase(this).getRowsStringUser())).show();*/
    }

    /*
    * Open the login screen
    */
    public void goToLogin(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    /*
    * Open the register screen
    */
    public void goToRegister(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        //Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        /*User.createUser("test", "test", this);
        if (User.getUser().register()) {
            startActivity(intent);
        } else if (User.getUser().login()){
            startActivity(intent);
        }*/
    }
    public static Activity getAct(){
        return me;
    }
}
