package com.example.myfirstapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeleccionarPuzzle extends AppCompatActivity implements View.OnClickListener {

    private static final int TAKE_PICTURE = 101;
    Bitmap btimage;

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
        Intent i= new Intent(this,MusicManager.class);
        stopService(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_main);
        Button btnCamara=findViewById(R.id.camera);
        Button ayuda=findViewById(R.id.help);
        Button volver=findViewById(R.id.botonvolver);
        btnCamara.setOnClickListener(this);
        ayuda.setOnClickListener(this);
        volver.setOnClickListener(this);
        Intent i= new Intent(this,MusicManager.class);
        Bundle datos=getIntent().getExtras();
        int op= datos.getInt("opcion");

        if(op==1) {
            startService(i);
        }

        AssetManager am;
        am = getAssets();

        try {
            final String[] files  = am.list("img"); //todas la imagenes para armar

            GridView grid = findViewById(R.id.tableropuzzles);
            grid.setAdapter(new Redimensionador(this)); //adapta las imagenes para mostrarla en tablero
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), CreaPuzzle.class);//envia al juego la imagen seleccionada
                    //por el jugador
                    intent.putExtra("assetName", files[i % files.length]);
                    startActivity(intent);
                }
            });
        } catch (
                IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT);
        }
    }








    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.help:
                Intent intent = new Intent(this, Help.class);
                startActivity(intent);

                break;

            case R.id.botonvolver:
                finish();
                break;
            case R.id.camera:
                openCamera();
                break;
        }

    }

    private void openCamera(){
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // if(i.resolveActivity(getPackageManager())!=null){
            startActivityForResult(i,1);

        //}

    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode== TAKE_PICTURE){
            if(resultCode== Activity.RESULT_OK && data!=null){
                btimage=(Bitmap) data.getExtras().get("data");
                saveImage();
                /*try{
                    FileOutputStream fos=openFileOutput(createNameJPG(), Context.MODE_PRIVATE);
                    btimage.compress(Bitmap.CompressFormat.JPEG,100,fos);
                    fos.close();
                }catch(Exception e){

                }*/
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    private String createNameJPG(){
        String fecha= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fecha+".jpg";
    }
    private void saveImage(){
        OutputStream fos=null;
        File file=null;

        ContentResolver resolver= getContentResolver();
        ContentValues values= new ContentValues();
        String filename= createNameJPG();
        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename);
        values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/MyApp");
        values.put(MediaStore.Images.Media.IS_PENDING,1);
        Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri imageUri= resolver.insert(collection,values);
        try {
            fos= resolver.openOutputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        values.clear();
        values.put(MediaStore.Images.Media.IS_PENDING,0);
        resolver.update(imageUri,values,null,null);

        btimage.compress(Bitmap.CompressFormat.JPEG,100,fos);
        try {
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this, new String[]{file.toString()},null,null);
    }
}