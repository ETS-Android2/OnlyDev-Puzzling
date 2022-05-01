package com.example.myfirstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NoTime extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_time);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        switch (button.getId()){
            case R.id.noTime_retry:
                break;
        }
    }
}
