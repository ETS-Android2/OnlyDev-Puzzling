package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GreatMenu extends AppCompatActivity implements View.OnClickListener {

    private Button exit,next;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.great);

        this.exit = findViewById(R.id.exitR);
        this.next = findViewById(R.id.retryR);

        this.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_menu = new Intent(getApplicationContext(), Menu.class);
                startActivity(main_menu);
            }
        });

        this.next.setOnClickListener(new View.OnClickListener() {
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


