package com.stabstudio.advancedmusicplayer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class AlbumsFragment extends Fragment {

    public ListView listView;
    public ArrayList<Song> albumsList = new ArrayList<Song>();
    private ArrayList<String> albumsName = new ArrayList<>();
    public static HashMap<String, ArrayList<String>> albumsMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_albums, container, false);

        listView = (ListView) vi.findViewById(R.id.albumslist);
        albumsList = SongsManager.songDataList;
        Collections.sort(albumsList, new AlbumsComparator());

        for(int i = 0; i < albumsList.size(); i++){

        }

        return vi;
    }

    private class AlbumsComparator implements Comparator<Song>{
        @Override
        public int compare(Song a, Song b) {
            return (a.getAlbum().compareToIgnoreCase(b.getAlbum()));
        }
    }

}
