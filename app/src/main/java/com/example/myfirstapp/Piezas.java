package com.example.myfirstapp;

import android.content.Context;

public class Piezas extends androidx.appcompat.widget.AppCompatImageView {
    public int xCoord;
    public int yCoord;
    public int pieceWidth;
    public int pieceHeight;
    public boolean canMove = true;

    public Piezas(Context context) { super(context); }
}