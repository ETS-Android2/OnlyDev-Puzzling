package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NoTime extends AppCompatActivity implements View.OnClickListener {

    private Button exit,retry;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_time);

        this.exit = findViewById(R.id.exitR);
        this.retry = findViewById(R.id.retryR);

        this.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_menu = new Intent(getApplicationContext(), Menu.class);
                startActivity(main_menu);
            }
        });

        this.retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seleccion_main = new Intent(getApplicationContext(), SeleccionarPuzzle.class);
                startActivity(seleccion_main);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}


