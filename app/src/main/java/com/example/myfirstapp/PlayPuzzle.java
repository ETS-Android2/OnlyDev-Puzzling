package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.os.CountDownTimer;
import android.widget.EditText;

import java.util.ArrayList;

import logicClasses.SQLManager;

public class PlayPuzzle extends AppCompatActivity {
    // --- ATTRIBUTES --------
    ArrayList<Bitmap> pieces;
    SQLManager sqlManager = new SQLManager(PlayPuzzle.this, "PuzzlingDatabase",
            null, 1);

    // --- MEMBER REFERENCES -
    TextView timeRemaining;

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
        this.timeRemaining = findViewById(R.id.timeRemaining);
        ImageView puzzle = findViewById(R.id.puzzle);
        PlayPuzzle.this.countDown(20L); //TODO: change time value depending on image
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

    public void countDown(Long initialTime){
        //Time is passed as seconds
        initialTime = initialTime * 1000;
        CountDownTimer countDownTimer = new CountDownTimer(initialTime, 1000) { //Decreasing by seconds
            @Override
            public void onTick(long timeRemaining) {
                String countDownShown = String.valueOf(timeRemaining / 1000);
                PlayPuzzle.this.timeRemaining.setText(countDownShown);
            }

            @Override
            public void onFinish() {
                Intent noTime = new Intent(getApplicationContext(), NoTime.class);
                startActivity(noTime);
            }
        }.start();
    }
}
