package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button playBtn=findViewById(R.id.playButton);
        Button settingsBtn=findViewById(R.id.settingsButton);
        Button exitBtn=findViewById(R.id.exitButton);

        playBtn.setOnClickListener((View.OnClickListener) this);
        settingsBtn.setOnClickListener((View.OnClickListener) this);
        exitBtn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.playButton:
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
                break;


            case R.id.settingsButton: //go to help View
                intent = new Intent(this, Help.class);
                startActivity(intent);
                break;

            case R.id.exitButton: //sale de la aplicacion
                finish();
                break;
        }
    }
}