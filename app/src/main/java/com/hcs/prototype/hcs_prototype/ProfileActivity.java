package com.hcs.prototype.hcs_prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ProfileActivity extends AppCompatActivity {

    List<History> hl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView scoreDisplay = (TextView)findViewById(R.id.score_display);
        scoreDisplay.setText("STAFF NUMBER: "+User.getUser().getUsername() + "\nSCORE: " + User.getUser().getScore()+"\nNAME:"+User.getUser().getName()+"\nTEL:"+User.getUser().getTel());
        ListView listview = (ListView) findViewById(R.id.hist_listview);
        hl = (new CaseStudyDatabase(this)).getMyHist(User.getUser().getUsername());
        final ArrayList<String> list = new ArrayList<String>();
        for (History hist : hl){
            list.add(hist.toString(this));
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("ID", position);
                intent.putExtras(b);
                startActivity(intent);

                //view.animate().setDuration(2000).alpha(0);

            }

        });
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
