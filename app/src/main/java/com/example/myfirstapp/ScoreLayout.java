package com.example.myfirstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import logicClasses.SQLManager;

public class ScoreLayout extends AppCompatActivity implements View.OnClickListener{

    // ---- Member references ------- //
    ListView scoreTable;

    // ---- ATTRIBUTES -------------- //
    SQLManager sqlManager = new SQLManager(ScoreLayout.this, "PuzzlingDatabase",
            null, 1);

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_menu);

        //Setting value for the member references from the first layout
        this.scoreTable = findViewById(R.id.scoreTable);
        this.showScores();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void showScores(){
        ArrayAdapter<ArrayList> scores = new ArrayAdapter<>(ScoreLayout.this,
                android.R.layout.simple_list_item_1, this.sqlManager.selectBestScores());
        this.scoreTable.setAdapter(scores);
    }
}
