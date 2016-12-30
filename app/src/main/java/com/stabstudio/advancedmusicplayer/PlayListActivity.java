package com.stabstudio.advancedmusicplayer;

//This class reads the playlist created by SongsManager class and displays them in a ListView

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class PlayListActivity extends ListActivity implements AdapterView.OnItemClickListener{

    public EditText editText;
    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        findViewById(R.id.parent).requestFocus();
        editText = (EditText)findViewById(R.id.editText);
        lv = getListView();
        //final SimpleAdapter adapter = new SimpleAdapter(this, songsList, R.layout.song_item, new String[]{ "songTitle" }, new int[]{ R.id.songTitle });
        final CustomAdapter adapter = new CustomAdapter(this, SongsManager.songsList);
        setListAdapter(adapter);
        lv.setOnItemClickListener(this);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*for(int i = 0; i < songsList.size(); i++){
            HashMap<String, String> song = songsList.get(i);            //Adding the first playlist to another buffer
            song.get("songTitle");
            String pathToSong = song.get("songPath");
            songsListData.add(song);}
        sortSongs();                         //Sort all the songs in alphabetical order
        ArrayAdapter<HashMap<String, String>> adapter = new ArrayAdapter<HashMap<String, String>>(this, android.R.layout.simple_list_item_1, songsListData);
        songView.setAdapter(adapter);
        songView.setOnItemClickListener(this);*/

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        in.putExtra("songIndex", position);
        in.putExtra("link", "songPath");
        setResult(100, in);
        finish();
    }

    /*public void sortSongs(){
        Collections.sort(songsListData, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> a, HashMap<String, String> b) {
                mmrt1.setDataSource(a.get("songPath"));
                mmrt2.setDataSource(b.get("songPath"));
                String title1 = mmrt1.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String title2 = mmrt2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                return title1.compareTo(title2);
            }
        });
    }*/

}
