package com.stabstudio.advancedmusicplayer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class SongsFragment extends Fragment {

    public HashMap hs = new HashMap();
    public EditText editText;
    public ListView listView;
    public Context context;

    public SongsFragment(Context c){
        this.context = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_songs, container, false);
        vi.findViewById(R.id.parent).requestFocus();
        editText = (EditText) vi.findViewById(R.id.editText3);

        listView = (ListView) vi.findViewById(R.id.songlistview);
        final CustomAdapter adapter = new CustomAdapter(context, SongsManager.songsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                in.putExtra("songIndex", position);
                in.putExtra("link", "songPath");
                getActivity().setResult(100, in);
                getActivity().finish();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return vi;
    }

}
