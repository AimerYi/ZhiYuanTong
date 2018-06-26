package com.apress.gerber.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Colonel on 2017/8/10.
 */

public class SecondActivity extends AppCompatActivity {
    private Button bt2;
    private Button bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt2.setOnClickListener(onClick2);
        bt3.setOnClickListener(onClick3);

    }
    public View.OnClickListener onClick3 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SecondActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
    };
    public View.OnClickListener onClick2 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
