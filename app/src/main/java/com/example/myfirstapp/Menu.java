package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Menu extends AppCompatActivity implements View.OnClickListener{


    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

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

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

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
                //Intent intent = new Intent(Menu.this, SeleccionarPuzzle.class);
                downloadFirebase();
                /*Intent intent = new Intent(Menu.this, CreaPuzzle.class);
                Bundle bundle2= new Bundle();
                Bundle datos=getIntent().getExtras();
                int op= datos.getInt("opcion");
                bundle2.putInt("opcion",op);
                intent.putExtras(bundle2);
                startActivity(intent);*/
                break;

            case R.id.closeSesiontButton: //sale de la aplicacion
                finish();
                break;
        }
    }
    public void downloadFirebase(){
        StorageReference imageRef = storageReference.child("images/puzzle4.jpg");
        long MAXBYTES = 1024*1024;
        imageRef.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //convertimos bytes a bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                Intent intent = new Intent(Menu.this, CreaPuzzle.class);
                Bundle bundle2= new Bundle();
                Bundle datos=getIntent().getExtras();
                int op= datos.getInt("opcion");
                bundle2.putInt("opcion",op);
                intent.putExtras(bundle2);
                intent.putExtra("image",bitmap);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

}
