package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener{




    @Override
    public void onResume() {
        super.onResume();
        Intent i= new Intent(this,MusicManager.class);
        Bundle datos=getIntent().getExtras();
        int op= datos.getInt("opcion");

        if(op==1) {
            startService(i);
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        Intent in= new Intent(this,MusicManager.class);
        stopService(in);
    }
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

       Intent i= new Intent(this,MusicManager.class);
        Bundle datos=getIntent().getExtras();
        int op= datos.getInt("opcion");

        if(op==1) {
            startService(i);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.playButton:
                Intent intent = new Intent(Menu.this, SeleccionarPuzzle.class);
                Bundle bundle2= new Bundle();
                Bundle datos=getIntent().getExtras();
                int op= datos.getInt("opcion");
                bundle2.putInt("opcion",op);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;

            case R.id.closeSesiontButton: //sale de la aplicacion
                finish();
                break;
        }
    }

}
