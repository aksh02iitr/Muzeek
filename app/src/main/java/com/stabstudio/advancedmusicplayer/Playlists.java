package com.stabstudio.advancedmusicplayer;

import java.util.ArrayList;
import java.util.HashMap;


public class Playlists {

    public static ArrayList<HashMap<String, String>> topRated;
    public static ArrayList<HashMap<String, String>> favourites;
    public static ArrayList<HashMap<String, String>> mostPlayed;
    public static ArrayList<HashMap<String, String>> lastPlayed;

    public Playlists(){
        topRated = new ArrayList<HashMap<String, String>>();
        favourites = new ArrayList<HashMap<String, String>>();
        mostPlayed = new ArrayList<HashMap<String, String>>();
        lastPlayed = new ArrayList<HashMap<String, String>>();
    }

    public void CreateNewPlaylist(){
        ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
    }

}
