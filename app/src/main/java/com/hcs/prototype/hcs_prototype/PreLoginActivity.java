package com.hcs.prototype.hcs_prototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;


public class PreLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelogin);

        if (User.getCurrentUser(this)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        UserDatabase database = new UserDatabase(this);
        database.getRowsStringUser();
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("WOOOOOO You got it right")
                .setMessage(database.getRowsStringUser()).show();
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("WOOOOOO You got it right")
                .setMessage(database.getScoreUser("name")+"").show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
