package com.stabstudio.advancedmusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OpenItemActivity extends Activity {

    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);

        textView = (TextView) findViewById(R.id.itemtext);
        listView = (ListView) findViewById(R.id.openitemlist);

        Intent in = getIntent();
        String artist = in.getStringExtra("artistName");
        textView.setText(artist);

        ArrayList<String> songsOfArtist = ArtistsFragment.artistMap.get(artist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsOfArtist);
        listView.setAdapter(adapter);

    }
}
