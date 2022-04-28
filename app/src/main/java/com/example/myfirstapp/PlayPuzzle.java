package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.widget.ImageView;

import java.util.ArrayList;

public class PlayPuzzle extends AppCompatActivity {
    ArrayList<Bitmap> pieces;
    @Override
    public void onResume() {
        super.onResume();
        Intent i= new Intent(this,MusicManager.class);
        startService(i);
    }
    @Override
    public void onPause() {
        super.onPause();
        Intent i= new Intent(this,MusicManager.class);
        stopService(i);
    }
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_menu); //Matching this class with the play_menu UI

        final ConstraintLayout layout = findViewById(R.id.playMenuUI);
        ImageView puzzle = findViewById(R.id.puzzle);
        Intent i= new Intent(this,MusicManager.class);
        startService(i);
        // run image related code after the view was laid out
        // to have all dimensions calculated
        puzzle.post(new Runnable(){
            @Override
            public void run(){
                pieces = splitImage();
                for (Bitmap piece : pieces){
                    ImageView puzzlePieces = new ImageView(getApplicationContext());
                    puzzlePieces.setImageBitmap(piece);
                    layout.addView(puzzlePieces);
                }
            }
        });
    }

    private ArrayList<Bitmap> splitImage() {
        int piecesAmount = 12;
        int rows = 4;
        int columns = 4;

        ImageView imageView = findViewById(R.id.puzzle);
        ArrayList<Bitmap> pieces = new ArrayList<>(piecesAmount);
        //Get Bitmap from source image aka puzzle
        BitmapDrawable puzzleDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = puzzleDrawable.getBitmap();
        //Get width and height for the pieces that will be made
        int widthFromPieces = bitmap.getWidth() / columns; //In order to fulfill the amount set for the columns
        int heightFromPieces = bitmap.getHeight() / rows;
        //Create the pieces from the ImageView and append them to the arraylist
        int height = 0;
        for (int row = 0; row < rows; row++) {
            int width = 0;
            for (int column = 0; column < columns; column++) {
                pieces.add(Bitmap.createBitmap(bitmap, width, height, widthFromPieces, heightFromPieces));
                width += widthFromPieces;
            }
            height += heightFromPieces;
        }
        return pieces;
    }
}
