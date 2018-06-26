package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/21.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecommendAdapter extends ArrayAdapter<Recommend> {


    public RecommendAdapter(Context context, int resource, List<Recommend> items){
        super(context,resource,items);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.putout_item,null);
        }
        Recommend recommend = getItem(position);
        if(recommend != null){
            TextView textViewName = (TextView) v.findViewById(R.id.text1);
            TextView textViewGender = (TextView) v.findViewById(R.id.text2);
            TextView textViewAge = (TextView) v.findViewById(R.id.text3);
            textViewName.setText(recommend.getName());
            textViewGender.setText(recommend.getGender());
            textViewAge.setText(recommend.getAge());
        }
        return v;
    }
}