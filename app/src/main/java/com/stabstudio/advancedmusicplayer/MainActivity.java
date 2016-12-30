package com.stabstudio.advancedmusicplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import android.os.Parcelable;


public class MainActivity extends Activity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{

    private Button btnPlay;
    private Button btnNext;
    private Button btnPrevious;
    private Button btnPlaylist;
    private Button btnRepeat;
    private Button btnShuffle;
    private Button info;
    private Button equaliser;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLevel;
    private TextView songTotalDurationLabel;
    private ImageView coverArt;
    private MediaPlayer mp;                           //Media Player object
    private Handler mHandler;                         //To update UI timer, progress bar
    private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000;               // 5000 milliseconds
    private int seekBackwardTime = 5000;              // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;

    private TextView current;
    private TextView total;

    public MediaMetadataRetriever mmr;
    public String path;

    public CheckBox chkBox;
    public RatingBar ratingBar;

    public Playlists playlists;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        playlists = new Playlists();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

        mHandler = new Handler();
        //ALBUM ART
        coverArt = (ImageView) findViewById(R.id.imageView);
        chkBox = (CheckBox) findViewById(R.id.checkBox);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        mmr = new MediaMetadataRetriever();

        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_SHORT).show();
                    SongsManager.songDataList.get(currentSongIndex).setFavourite(true);
                    Playlists.favourites.add(SongsManager.songsList.get(currentSongIndex));
                }else{
                    Toast.makeText(getApplicationContext(), "UnChecked", Toast.LENGTH_SHORT).show();
                    SongsManager.songDataList.get(currentSongIndex).setFavourite(false);
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int val = (int)ratingBar.getRating();
                SongsManager.songDataList.get(currentSongIndex).setRating(ratingBar.getNumStars());
                if(ratingBar.getNumStars() == 5){
                    Playlists.topRated.add(SongsManager.songsList.get(currentSongIndex));
                }
            }
        });

        //BUTTONS
        btnPlay = (Button) findViewById(R.id.play);
        btnNext = (Button) findViewById(R.id.next);
        btnPrevious = (Button) findViewById(R.id.previous);
        btnPlaylist = (Button) findViewById(R.id.playlist);
        btnRepeat = (Button) findViewById(R.id.repeat);
        btnShuffle = (Button) findViewById(R.id.shuffle);
        info = (Button) findViewById(R.id.info);
        equaliser = (Button) findViewById(R.id.equaliser);

        //SEEKBAR
        songProgressBar = (SeekBar) findViewById(R.id.seekBar4);

        //TEXTVIEWS
        songTitleLabel = (TextView) findViewById(R.id.title);
        songCurrentDurationLevel = (TextView) findViewById(R.id.currentDuration);
        songTotalDurationLabel = (TextView) findViewById(R.id.totalDuration);
        current = (TextView)findViewById(R.id.current);
        total = (TextView)findViewById(R.id.total);

        //MediaPlayer
        mp = new MediaPlayer();
        songManager = new SongsManager(this);      //Main Step where all the songs are loaded in hashmap
        utils = new Utilities();

        //Toast.makeText(this, SongsManager.MEDIA_PATH, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, SongsManager.EXTERNAL_MEDIA_PATH, Toast.LENGTH_SHORT).show();

        //Listeners
        songProgressBar.setOnSeekBarChangeListener(this);
        mp.setOnCompletionListener(this);

        //EQUALISER BUTTON CLICK EVENT
        equaliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eqIntent = new Intent(getApplicationContext(), Equaliser.class);
                startActivity(eqIntent);
            }
        });

        //INFO BUTTON CLICK EVENT
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(getApplicationContext(), MetaData.class);
                if(path != null) {
                    infoIntent.putExtra("MetaData", path);
                }
                startActivity(infoIntent);
            }
        });

        //PLAY BUTTON CLICK EVENT
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    if (mp != null) {
                        mp.pause();
                        btnPlay.setText("Play");
                        //btnPlay.setImageResource(R.drawable.btn_play);
                    }
                } else {
                    if (mp != null) {
                        mp.start();
                        btnPlay.setText("Pause");
                        //btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }
            }
        });

        //LOAD ALL THE SONGS
        btnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PlaylistActivity2.class);
                startActivityForResult(i, 100);
            }
        });

        //FORWARD AND BACK BUTTONS
        /*btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mp.getCurrentPosition();
                if(currentPosition + seekForwardTime <= mp.getDuration()){
                    mp.seekTo(currentPosition + seekForwardTime);
                }else{
                    mp.seekTo(mp.getDuration());
                }
            }
        });
        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mp.getCurrentPosition();
                if(currentPosition - seekBackwardTime >= 0){
                    mp.seekTo(currentPosition - seekBackwardTime);
                }else{
                    mp.seekTo(0);
                }
            }
        });*/

        //NEXT BUTTON CLICK EVENT
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSongIndex < (SongsManager.songsList.size() - 1)){
                    currentSongIndex++;
                    playSong(currentSongIndex);
                }
                else{
                    playSong(0);
                    currentSongIndex = 0;
                }
            }
        });
        //PREV BUTTON CLICK EVENT
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSongIndex > 0){
                    playSong(currentSongIndex - 1);
                    currentSongIndex--;
                }
                else{
                    playSong(SongsManager.songsList.size() - 1);
                    currentSongIndex = SongsManager.songsList.size() - 1;
                }
            }
        });

        //REPEAT BUTTON CLICK EVENT
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRepeat){
                    isRepeat = false;
                    //Toast.makeText(getApplicationContext(), "Repeat OFF", Toast.LENGTH_SHORT).show();
                    //btnRepeat.setImageResource(R.drawable.btn_repeat);
                }else{
                    isRepeat = true;
                    //Toast.makeText(getApplicationContext(), "Repeat ON", Toast.LENGTH_SHORT).show();
                    isShuffle = false;
                    //btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    //btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });

        //SHUFFLE BUTTON CLICK EVENT
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(isShuffle){
                    isShuffle = false;
                    //Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    //btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }else{
                    // make repeat to true
                    isShuffle= true;
                    //Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    //btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    //btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public void playSong(int songIndex){
        path = SongsManager.songsList.get(songIndex).get("songPath");               //Path to the current song being played
        try {
            mp.reset();
            mp.setDataSource(path);
            mmr.setDataSource(path);
            //SETTING METADATA
            //String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            //Toast.makeText(getApplicationContext(), album, Toast.LENGTH_LONG).show();
            byte[] artBytes = mmr.getEmbeddedPicture();
            if(artBytes != null){
                Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
                coverArt.setImageBitmap(bm);
            }else{
                //coverArt.setImageDrawable(getResources().getDrawable(R.drawable.default_cover));
                coverArt.setImageResource(R.drawable.default_cover);
            }
            //SETTING METADATA
            //Toast.makeText(this, songsList.get(songIndex).get("songPath"), Toast.LENGTH_LONG).show();
            mp.prepare();
            mp.start();
            String songTitle = SongsManager.songsList.get(songIndex).get("songTitle"); //Displaying Title
            songTitleLabel.setText(songTitle);
            //btnPlay.setImageResource(R.drawable.btn_pause);    //Changing button image to pause image
            songProgressBar.setProgress(0);                      //Set progress bar values
            songProgressBar.setMax(100);
            //updateProgressBar();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    public void playSongByLink(String path){
        try {
            mp.reset();
            mp.setDataSource(path);
            mmr.setDataSource(path);
            //SETTING METADATA
            //String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            //Toast.makeText(getApplicationContext(), album, Toast.LENGTH_LONG).show();
            byte[] artBytes = mmr.getEmbeddedPicture();
            if(artBytes != null){
                Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
                coverArt.setImageBitmap(bm);
            }else{
                coverArt.setImageDrawable(getResources().getDrawable(R.drawable.default_cover));
            }
            //SETTING METADATA
            //Toast.makeText(this, songsList.get(songIndex).get("songPath"), Toast.LENGTH_LONG).show();
            mp.prepare();
            mp.start();
            //String songTitle = songsList.get(songIndex).get("songTitle"); //Displaying Title
            //songTitleLabel.setText(songTitle);
            //btnPlay.setImageResource(R.drawable.btn_pause);    //Changing button image to pause image
            songProgressBar.setProgress(0);                      //Set progress bar values
            songProgressBar.setMax(100);
            //updateProgressBar();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){  //After Tapping the song on listview
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            currentSongIndex = data.getExtras().getInt("songIndex");
            String songLink = data.getStringExtra("link");
            playSong(currentSongIndex);
            SongsManager.songDataList.get(currentSongIndex).incrementTime();
            if(SongsManager.songDataList.get(currentSongIndex).getPlayTimes() > 10){
                Playlists.mostPlayed.add(SongsManager.songsList.get(currentSongIndex));
            }
            //playSongByLink(songLink);
        }
    }

    @Override          //Play the next song automatically when the previous song is completed
    public void onCompletion(MediaPlayer mp) {
        if(isRepeat){
            playSong(currentSongIndex);
        }
        else if (isShuffle){
            Random rand = new Random();
            currentSongIndex = rand.nextInt((SongsManager.songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        }
        else {
            if(currentSongIndex < (SongsManager.songsList.size() - 1)){
                currentSongIndex++;
                playSong(currentSongIndex);
            }else{
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    public void updateProgressBar(){
        mHandler.postDelayed(mUpdateTimeTask, 100);       //On calling of this, the progress bar gets updated
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();
            //current.setText("" + currentDuration);
            //total.setText("" + totalDuration);
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            songCurrentDurationLevel.setText("" + utils.milliSecondsToTimer(currentDuration));
            //UPDATING THE PROGRESS BAR
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            songProgressBar.setProgress(progress);
            mHandler.postDelayed(this, 100);       //On calling of this, the progress bar gets updated
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override          //When user starts dragging the progress bar
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override          //When user stops moving the progress bar
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
        mp.seekTo(currentPosition);    //MediaPlayer callback method
        updateProgressBar();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mp.release();
    }

}
