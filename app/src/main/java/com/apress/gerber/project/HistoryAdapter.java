package com.apress.gerber.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Colonel on 2017/9/26.
 */

public class HistoryAdapter extends ArrayAdapter<History> {
    public HistoryAdapter(Context context, int resource, List<History> items){
        super(context,resource,items);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.putout_item,null);
        }
        History history = getItem(position);
        if(history != null){
            TextView textViewName = (TextView) v.findViewById(R.id.text1);
            TextView textViewGender = (TextView) v.findViewById(R.id.text2);
            TextView textViewAge = (TextView) v.findViewById(R.id.text3);
            textViewName.setText(history.gets_name());
            textViewGender.setText(history.getmajoy());
            textViewAge.setText(history.getsocoure());
        }
        return v;
    }
}
