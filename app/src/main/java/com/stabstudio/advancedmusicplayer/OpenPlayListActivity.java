package com.stabstudio.advancedmusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OpenPlayListActivity extends Activity {

    private TextView textView;
    public ListView listView;
    public CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_play_list);

        Intent in = getIntent();
        String listName = in.getStringExtra("ListName");

        textView = (TextView)findViewById(R.id.heading);
        textView.setText(listName);
        listView = (ListView)findViewById(R.id.listPlaylist);

        if(listName.equals("TopRated")){
            adapter = new CustomAdapter(this, Playlists.topRated);
        }
        else if(listName.equals("Favourites")){
            adapter = new CustomAdapter(this, Playlists.favourites);
        }
        else if(listName.equals("MostPlayed")){
            adapter = new CustomAdapter(this, Playlists.mostPlayed);
        }
        else if(listName.equals("LastPlayed")){
            adapter = new CustomAdapter(this, Playlists.lastPlayed);
        }

        listView.setAdapter(adapter);

    }

}
