package com.stabstudio.advancedmusicplayer;

//This class SCANS all files from sdcard and ExtSdCard and filters the ones having extension of .mp3 and creates a playlist
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SongsManager{

    public final String MEDIA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/";
    public final String EXTERNAL_MEDIA_PATH = "extSdCard/Music";
    private final String mp3Pattern = ".mp3";
    private Context context;

    public static ArrayList<HashMap<String, String>> songsList;
    public static ArrayList<Song> songDataList;

    public SongsManager(Context context){
        this.context = context;
        songsList = new ArrayList<HashMap<String, String>>();
        songDataList = new ArrayList<Song>();
        getPlayList();
    }

    public ArrayList<HashMap<String, String>> getPlayList(){
        if(MEDIA_PATH != null){                             //Search in Internal SDCARD
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    //System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        if(EXTERNAL_MEDIA_PATH != null){                   //Search in External SDCARD
            File home = new File(EXTERNAL_MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0){
                for (File file : listFiles) {
                    //System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        return songsList;
    }

    public ArrayList<HashMap<String, String>> getSongList(){
        return songsList;
    }

    private void scanDirectory(File directory) {                        //DFS RECURSIVE TREE TRAVERSAL ALGORITHM
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
    }

    private void addSongToList(File song) {
        if(song.getName().endsWith(mp3Pattern)) {
            HashMap<String, String> songMap = new HashMap<String, String>();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            String title = song.getName().substring(0, (song.getName().length() - 4));
            String path = song.getPath();
            mmr.setDataSource(path);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String genre = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);

            if(title == null)        title = "null";
            if(artist == null)       artist = "null";
            if(album == null)        album = "null";
            if(genre == null)        genre = "null";

            songMap.put("songTitle", song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song.getPath());
            //Log.d("Song", songMap.get("songTitle"));
            songsList.add(songMap);
            songDataList.add(new Song(title, path, artist, album, genre));
        }
    }

    class FileExtensionFilter implements FilenameFilter{
        public boolean accept(File dir, String name){
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }

}
