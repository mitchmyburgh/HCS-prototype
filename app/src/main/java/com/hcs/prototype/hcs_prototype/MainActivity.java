package com.hcs.prototype.hcs_prototype;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    /**
     * List of Case Studies
     */
    List<CaseStudy> csl;
    /**
     * The List View containing the case studies
     */
    ListView listview;
    /**
     * Creates the activity
     * @param savedInstanceState the previous saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreLoginActivity.getAct().finish();
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

        listview = (ListView) findViewById(R.id.cs_listview);
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        */
        this.drawCSList();

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
            //String m_chosen;
            //Intent intent = new Intent(this, AddCSActivity.class);
            //startActivity(intent);
            //code taken from http://www.scorchworks.com/Blog/simple-file-dialog-for-android-applications/
            SimpleFileDialog FileOpenDialog = new SimpleFileDialog(MainActivity.this, "FileOpen", new SimpleFileDialog.SimpleFileDialogListener() {
                @Override
                public void onChosenDir(String chosenDir) { // The code in this function will be executed when the dialog OK button is pushed
                    String m_chosen = chosenDir;
                    //Pattern p = Pattern.compile(".*\\.hson");
                    if (Pattern.matches(".*\\.hson", m_chosen)){
                        Toast.makeText(MainActivity.this, "Chosen FileOpenDialog File: " + m_chosen, Toast.LENGTH_LONG).show();
                        CaseStudy cs = new CaseStudy(m_chosen, MainActivity.this);
                        csl.add(cs);
                        drawCSList();
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect File Type: " + m_chosen, Toast.LENGTH_LONG).show();
                    }
                }
            }); //You can change the default filename using the public variable "Default_File_Name"
              FileOpenDialog.Default_File_Name = ""; FileOpenDialog.chooseFile_or_Dir(); ///////////////////////////////////////////////////////////////////////////////////////////////// - See more at: http://www.scorchworks.com/Blog/simple-file-dialog-for-android-applications/#sthash.RN6X6Tku.dpuf
            return true;
        }
        if (id == R.id.action_logout) {
            if (User.getUser().logout()) {
                Intent intent = new Intent(this, PreLoginActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void drawCSList(){
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
     * Handle back button pressed
     */
    /*@Override
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
    }*/

    /**
     * Array Adapter for printing the List of Case studies
     */
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
