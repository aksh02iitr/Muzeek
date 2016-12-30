package com.stabstudio.advancedmusicplayer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class GenresFragment extends Fragment {

    public ListView listView;
    public ArrayList<Song> genresList = new ArrayList<Song>();
    public static HashMap<String, ArrayList<String>> genresMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_genres, container, false);

        listView = (ListView) vi.findViewById(R.id.genreslist);
        genresList = SongsManager.songDataList;
        Collections.sort(genresList, new GenreComparator());

        for(int i = 0; i < genresList.size(); i++){

        }

        return vi;
    }

    private class GenreComparator implements Comparator<Song>{
        @Override
        public int compare(Song a, Song b) {
            return (a.getGenre().compareToIgnoreCase(b.getGenre()));
        }
    }

}
