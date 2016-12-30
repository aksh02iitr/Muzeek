package com.stabstudio.advancedmusicplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends BaseAdapter implements Filterable{

    Context context;
    private ArrayList<HashMap<String, String>> originalData;
    private ArrayList<HashMap<String, String>> filteredData;

    public CustomAdapter(Context c, ArrayList<HashMap<String, String>> songsList){
        this.context = c;
        this.originalData = songsList;
        this.filteredData = songsList;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View vi, ViewGroup parent) {
        if(vi == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.song_item, null);
        }
        TextView name = (TextView)vi.findViewById(R.id.songTitle);
        name.setText(filteredData.get(position).get("songTitle"));
        return vi;
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                charSequence = charSequence.toString().toLowerCase();
                FilterResults results = new FilterResults();
                //If there is nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.toString().length() == 0){
                    results.values = originalData;
                    results.count = originalData.size();
                }
                else{
                    ArrayList<HashMap<String, String>> founded = new ArrayList<HashMap<String, String>>();
                    //Iterate through originalData and put those in filteredData which matches the string
                    for(HashMap<String, String> data : originalData){
                        if(data.get("songTitle").toString().toLowerCase().contains(charSequence)){
                            founded.add(data);
                        }
                    }
                    results.values = founded;
                    results.count = founded.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<HashMap<String, String>>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
