package com.stabstudio.advancedmusicplayer;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class CreatePlaylistActivity extends Activity {

    private EditText editText;
    private Button create;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_playlist);

        editText = (EditText) findViewById(R.id.editText6);
        create = (Button) findViewById(R.id.create1);
        cancel = (Button) findViewById(R.id.cancel1);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaylistFragment.lists.add(editText.getText().toString());
                finish();
            }
        });

    }
}
