package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_main);
    }

    public void playMenu(View view){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

    public void helpPage(View view){
        Intent i = new Intent(this,Help.class);
        startActivity(i);
    }

    public void exit(){
        System.exit(0);
    }

}
