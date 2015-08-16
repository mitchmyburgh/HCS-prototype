package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<CaseStudy> csl;
    /**
     * Creates the activity
     * @param savedInstanceState the previous saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CaseStudy.createDatabase(this);
        TextView scoreDisplay = (TextView)findViewById(R.id.ls_display);
        scoreDisplay.setText("");
        /*for (int i = 0; i<100; i++){
            if (CaseStudy.addCaseStudy("id"+i, "name", "desc", "type", "location") == -1){
                //scoreDisplay.append("Duplicate case\n");
            }
        }*/


        //scoreDisplay.append(CaseStudy.getAllCaseString());
        csl = CaseStudy.getAllCaseStudy();

        final ListView listview = (ListView) findViewById(R.id.cs_listview);
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        */
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < csl.size(); ++i) {
            list.add(csl.get(i).toString());
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                /*new AlertDialog.Builder(MainActivity.this)
                        .setTitle(position+"")
                        .setMessage(csl.get(position).getId()).show();*/
                Intent intent = new Intent(MainActivity.this, CaseStudyActivity.class);
                Bundle b = new Bundle();
                b.putInt("ID", csl.get(position).getPrimaryKey());
                intent.putExtras(b);
                startActivity(intent);
                //view.animate().setDuration(2000).alpha(0);

            }

        });
    }

    /**
     * Creates the options menu
     * @param menu the menu to be created
     * @return boolean indicatess teh success of creating the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Opens selected item from the menu
     * @param item The menu item opened
     * @return boolean indicates teh success of opening the menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_addcs) {
            Intent intent = new Intent(this, AddCSActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
