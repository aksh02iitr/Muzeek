package com.stabstudio.advancedmusicplayer;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.StringDef;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Equaliser extends Activity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    String[] equalisers = { "Normal", "Flat", "Classic", "Rock", "Dance", "Pop", "Bass" };
    ArrayList<String> genres = new ArrayList<String>();

    private SeekBar sBar1;
    private SeekBar sBar2;
    private SeekBar sBar3;
    private SeekBar sBar4;
    private SeekBar sBar5;
    private SeekBar sBar6;
    private SeekBar sBar7;
    private SeekBar sBar8;

    private Button addNew;
    private Button off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equaliser);

        genres.add("Normal");
        genres.add("Flat");
        genres.add("Classic");
        genres.add("Rock");
        genres.add("Dance");
        genres.add("Pop");
        genres.add("Bass");

        sBar1 = (SeekBar) findViewById(R.id.seekBar);
        sBar2 = (SeekBar) findViewById(R.id.seekBar2);
        sBar3 = (SeekBar) findViewById(R.id.seekBar3);
        sBar4 = (SeekBar) findViewById(R.id.seekBar4);
        sBar5 = (SeekBar) findViewById(R.id.seekBar5);
        sBar6 = (SeekBar) findViewById(R.id.seekBar6);
        sBar7 = (SeekBar) findViewById(R.id.seekBar7);
        sBar8 = (SeekBar) findViewById(R.id.seekBar8);

        addNew = (Button) findViewById(R.id.addnew);
        off = (Button) findViewById(R.id.off);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNew.class);
                startActivityForResult(intent, 100);
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sBar1.setProgress(0);
                sBar2.setProgress(0);
                sBar3.setProgress(0);
                sBar4.setProgress(0);
                sBar5.setProgress(0);
                sBar6.setProgress(0);
                sBar7.setProgress(0);
                sBar8.setProgress(0);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genres);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){  //After Tapping the song on listview
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            genres.add(data.getStringExtra("genreName"));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view;
        //Toast.makeText(this, "Working " + position, Toast.LENGTH_SHORT).show();
        if(position == 0){
            sBar1.setProgress(50);
            sBar2.setProgress(50);
            sBar3.setProgress(50);
            sBar4.setProgress(50);
            sBar5.setProgress(50);
            sBar6.setProgress(50);
            sBar7.setProgress(50);
            sBar8.setProgress(50);
        }
        if(position == 1){
            sBar1.setProgress(60);
            sBar2.setProgress(20);
            sBar3.setProgress(40);
            sBar4.setProgress(60);
            sBar5.setProgress(60);
            sBar6.setProgress(70);
            sBar7.setProgress(50);
            sBar8.setProgress(40);
        }
        if(position == 2){
            sBar1.setProgress(60);
            sBar2.setProgress(20);
            sBar3.setProgress(40);
            sBar4.setProgress(60);
            sBar5.setProgress(60);
            sBar6.setProgress(70);
            sBar7.setProgress(50);
            sBar8.setProgress(40);
        }
        if(position == 3){
            sBar1.setProgress(60);
            sBar2.setProgress(20);
            sBar3.setProgress(40);
            sBar4.setProgress(60);
            sBar5.setProgress(60);
            sBar6.setProgress(70);
            sBar7.setProgress(50);
            sBar8.setProgress(40);
        }
        if(position == 4){
            sBar1.setProgress(60);
            sBar2.setProgress(60);
            sBar3.setProgress(70);
            sBar4.setProgress(80);
            sBar5.setProgress(70);
            sBar6.setProgress(70);
            sBar7.setProgress(30);
            sBar8.setProgress(20);
        }
        if(position == 5){
            sBar1.setProgress(30);
            sBar2.setProgress(30);
            sBar3.setProgress(40);
            sBar4.setProgress(50);
            sBar5.setProgress(60);
            sBar6.setProgress(70);
            sBar7.setProgress(80);
            sBar8.setProgress(80);
        }
        if(position == 6){
            sBar1.setProgress(60);
            sBar2.setProgress(60);
            sBar3.setProgress(60);
            sBar4.setProgress(50);
            sBar5.setProgress(50);
            sBar6.setProgress(60);
            sBar7.setProgress(70);
            sBar8.setProgress(70);
        }
        if(position == 7){
            sBar1.setProgress(80);
            sBar2.setProgress(80);
            sBar3.setProgress(50);
            sBar4.setProgress(60);
            sBar5.setProgress(70);
            sBar6.setProgress(60);
            sBar7.setProgress(40);
            sBar8.setProgress(30);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
