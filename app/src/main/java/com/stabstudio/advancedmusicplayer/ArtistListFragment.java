package com.stabstudio.advancedmusicplayer;


import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.HashMap;

public class ArtistListFragment extends Fragment implements AdapterView.OnItemClickListener{

    public HashMap<String, String> artistList =  new HashMap<String, String>();
    public ArrayList<HashMap<String, String>> songsList;
    public MediaMetadataRetriever mmr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        songsList = new ArrayList<HashMap<String, String>>();
        //SongsManager plm = new SongsManager(this);
        //songsList = plm.getPlayList();

        for(HashMap<String, String> hmap : songsList){           //Make a list of all the artist names against file path
            String sPath = hmap.get("songPath");
            mmr.setDataSource(sPath);
            String artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if(artistName == null){
                artistName = "unknown";
            }
            artistList.put(artistName, sPath);
        }

        /*for(int i = 0; i < songsList.size(); i++){
            HashMap<String, String> song = songsList.get(i);
            String pathToSong = song.get("songPath");
            mmr.setDataSource(pathToSong);
            String artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if(artistName == null){
                artistName = "unknown";
            }
            artistList.put(artistName, pathToSong);
        }*/

        //Create a listView And display them using an arrayAdapter

        return inflater.inflate(R.layout.playlist, container, false);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
