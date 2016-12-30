package com.stabstudio.advancedmusicplayer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class ArtistsFragment extends Fragment {

    private ListView listView;
    private ArrayList<Song> artistList;
    private ArrayList<String> artistNames;
    public static HashMap<String, ArrayList<String>> artistMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_artists, container, false);

        artistList = new ArrayList<Song>();
        artistNames = new ArrayList<String>();
        artistMap = new HashMap<String, ArrayList<String>>();

        artistList = SongsManager.songDataList;
        Collections.sort(artistList, new ArtistComparator());

        ArrayList<String> temp = new ArrayList<String>();
        temp.add(artistList.get(0).getTitle());

        for(int i = 1; i < artistList.size(); i++){
            if(artistList.get(i).getArtist() != artistList.get(i-1).getArtist()){
                artistMap.put(artistList.get(i-1).getArtist(), temp);
                artistNames.add(artistList.get(i-1).getArtist());
                temp.clear();
                temp.add(artistList.get(i).getTitle());
            }
            else{
                temp.add(artistList.get(i).getTitle());
            }
        }
        artistMap.put(artistList.get(artistList.size()-1).getArtist(), temp);
        artistNames.add(artistList.get(artistList.size()-1).getArtist());

        listView = (ListView) vi.findViewById(R.id.artistlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, artistNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity().getApplicationContext(), OpenItemActivity.class);
                in.putExtra("artistName", artistNames.get(position));
                startActivity(in);
            }
        });

        return vi;
    }

    public class ArtistComparator implements Comparator<Song>{
        @Override
        public int compare(Song a, Song b) {
            return (a.getArtist()).compareToIgnoreCase(b.getArtist());
        }
    }

}
