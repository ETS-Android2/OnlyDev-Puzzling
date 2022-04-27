package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button playBtn=findViewById(R.id.playButton);
        Button settingsBtn=findViewById(R.id.settingsButton);
        Button exitBtn=findViewById(R.id.closeSesiontButton);

        playBtn.setOnClickListener((View.OnClickListener) this);
        settingsBtn.setOnClickListener((View.OnClickListener) this);
        exitBtn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.playButton:
                Intent intent = new Intent(Menu.this, SeleccionarPuzzle.class);
                startActivity(intent);
                break;

            case R.id.closeSesiontButton: //sale de la aplicacion
                finish();
                break;
        }
    }

}
