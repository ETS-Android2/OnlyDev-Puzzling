package com.example.myfirstapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesion_main);

        Button iniSesionBtn=findViewById(R.id.ini_sesion);
        Button helpBtn=findViewById(R.id.help);
        Button ceateAccBtn=findViewById(R.id.exitApp);

        iniSesionBtn.setOnClickListener((View.OnClickListener) this);
        helpBtn.setOnClickListener((View.OnClickListener) this);
        ceateAccBtn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ini_sesion:
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
                break;


            case R.id.help: //go to help View
                intent = new Intent(this, Help.class);
                startActivity(intent);
                break;

            case R.id.exitApp: //sale de la aplicacion
                finish();
                break;
        }
    }
}
//    public void playMenu(View view){
//        Intent i = new Intent(this, Menu.class);
//        startActivity(i);
//    }
//
//    public void helpPage(View view){
//        Intent i = new Intent(this,Help.class);
//        startActivity(i);
//    }
//
//    public void clickExit(View view){
//        moveTaskToBack(true);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
