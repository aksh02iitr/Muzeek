package com.stabstudio.advancedmusicplayer;



public class Song {

    private String title;
    private String path;
    private String artist;
    private String album;
    private String genre;
    private int rating;
    private boolean favourite;
    private int playTimes;

    public Song(String title, String path){
        this.title = title;
        this.path = path;
    }

    public Song(String title, String path, String artist, String album, String genre){
        this.title = title;
        this.path = path;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public void setFavourite(boolean favourite){
        this.favourite = favourite;
    }

    public void incrementTime(){
        this.playTimes++;
    }

    public int getPlayTimes(){
        return playTimes;
    }

    public String getTitle(){
        return title;
    }

    public String getPath(){
        return path;
    }

    public String getArtist(){
        return artist;
    }

    public String getAlbum(){
        return album;
    }

    public String getGenre(){
        return genre;
    }

}
