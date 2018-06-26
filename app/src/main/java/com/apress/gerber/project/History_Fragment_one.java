package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/26.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.StaticLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by cg on 2015/10/21.
 */
public class History_Fragment_one extends Fragment  {

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }

}