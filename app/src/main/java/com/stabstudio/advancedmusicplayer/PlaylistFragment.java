package com.stabstudio.advancedmusicplayer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PlaylistFragment extends Fragment {

    ListView listView;
    public static ArrayList<String> lists;
    ImageView addPlayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.fragment_playlist, container, false);

        lists = new ArrayList<String>();
        lists.add("TopRated");
        lists.add("Favourites");
        lists.add("MostPlayed");
        lists.add("LastPlayed");

        addPlayList = (ImageView) vi.findViewById(R.id.addplaylist);
        listView = (ListView) vi.findViewById(R.id.playlistView);
        //ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.playlist_item, lists);

        addPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity().getApplicationContext(), CreatePlaylistActivity.class);
                startActivity(in);
            }
        });

        CustomAdapter2 adapter2 = new CustomAdapter2();
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity().getApplicationContext(), OpenPlayListActivity.class);
                in.putExtra("ListName", lists.get(position));
                startActivity(in);
            }
        });
        return vi;
    }

    public class CustomAdapter2 extends BaseAdapter{

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View vi, ViewGroup parent) {
            if(vi == null){
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.playlist_item, null);
            }
            TextView name = (TextView)vi.findViewById(R.id.listlist);
            name.setText(lists.get(position));
            return vi;
        }
    }

}
