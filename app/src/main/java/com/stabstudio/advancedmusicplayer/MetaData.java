package com.stabstudio.advancedmusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MetaData extends Activity {

    public MediaMetadataRetriever mmr;

    private TextView title;
    private ImageView coverArt;
    private TextView album;
    private TextView artist;
    private TextView genre;
    private TextView composer;
    private TextView duration;
    private TextView bitrate;
    private TextView year;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);

        title = (TextView) findViewById(R.id.title);
        coverArt = (ImageView) findViewById(R.id.imageView);
        album = (TextView) findViewById(R.id.album);
        artist = (TextView) findViewById(R.id.artist);
        genre = (TextView) findViewById(R.id.genre);
        composer = (TextView) findViewById(R.id.composer);
        duration = (TextView) findViewById(R.id.duration);
        bitrate = (TextView) findViewById(R.id.bitrate);
        year = (TextView) findViewById(R.id.year);

        Intent intent = getIntent();
        path = intent.getStringExtra("MetaData");

        mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        //Toast.makeText(this, path, Toast.LENGTH_LONG).show();

        /*byte[] artBytes = mmr.getEmbeddedPicture();
        if(artBytes != null){
            Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
            coverArt.setImageBitmap(bm);
        }else{
            coverArt.setImageDrawable(getResources().getDrawable(R.drawable.default_cover));
        }*/

        String titleName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String genreName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        String durationVal = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        String composerName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER);
        String bitrateVal = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        String yearName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);

        if(titleName == null)        titleName = "null";
        //else    titleName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if(albumName == null)        albumName = "null";
        //else    albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        if(artistName == null)       artistName = "null";
        //else    artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if(genreName == null)        genreName = "null";
        //else    genreName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        if(durationVal == null)     durationVal = "null";
        //else    durationVal = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        if(bitrateVal == null)     bitrateVal = "null";
        //else    bitrateVal = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        if(composerName == null)     composerName = "null";
        //else    composerName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER);
        if(yearName == null)        yearName = "null";
        //else    yearName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);

        title.setText(titleName);
        album.setText(albumName);
        artist.setText(artistName);
        genre.setText(genreName);
        duration.setText(durationVal);
        bitrate.setText(bitrateVal);
        composer.setText(composerName);
        year.setText(yearName);

    }
}
