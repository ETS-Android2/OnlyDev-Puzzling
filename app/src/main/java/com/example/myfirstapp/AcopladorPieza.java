package com.example.myfirstapp;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AcopladorPieza implements View.OnTouchListener {
    private float xDelta;
    private float yDelta;
    private CreaPuzzle activity;

    public AcopladorPieza(CreaPuzzle activity) {
        this.activity = activity;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();

        //calcula una tolerancia del 10% del tamaño diagonal de la pieza.
        // Si la pieza está más cerca que este en la posición original,
        // se ajustará en su lugar y su propiedad canMove se establecerá en falso.
        // De esta manera, la próxima vez que el jugador intente moverlo, ya no funcionará.

        final double tolerancia = sqrt(pow(view.getWidth(), 2) + pow(view.getHeight(), 2)) / 10;

        Piezas pieza = (Piezas) view;
        if (!pieza.canMove) {
            return true;
        }

        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            //la pieza sale al frente de las otras cuando se toca, en caso de que quede
            // parcialmente oscurecida por otras piezas, y se envíe a la parte posterior de la pila
            // cuando encaje en su lugar, por lo que no cubre ninguna otra pieza que pueda ser movida
            case MotionEvent.ACTION_DOWN:
                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                pieza.bringToFront();
                //Se llama al movimiento de la pieza.
                animPieceAlpha(pieza);
                //playPickPieceSound();
                break;
            case MotionEvent.ACTION_MOVE:
                lParams.leftMargin = (int) (x - xDelta);
                lParams.topMargin = (int) (y - yDelta);
                view.setLayoutParams(lParams);
                break;
            case MotionEvent.ACTION_UP:
                int xDiff = abs(pieza.xCoord - lParams.leftMargin);
                int yDiff = abs(pieza.yCoord - lParams.topMargin);
                if (xDiff <= tolerancia && yDiff <= tolerancia) {
                    lParams.leftMargin = pieza.xCoord;
                    lParams.topMargin = pieza.yCoord;
                    pieza.setLayoutParams(lParams);
                    pieza.canMove = false;
                    enviarAtras(pieza);
                    //Se llama al movimiento de la rotación de la pieza.
                    animPieceRotate(pieza);
                    //playSetPieceSound();
                    activity.checkGameOver(); //se chequea estado del juego
                }
                break;
        }
        return true;
    }

    //Se crea el método que hace que la pieza se mueva.
    private void animPieceAlpha(Piezas piece) {
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(piece, "alpha", 0.5f, 1f);
        animation1.setDuration(600);
        animation1.start();
    }

    //Se crea el método que hace que la pieza rote en su posión.
    private void animPieceRotate(Piezas piece) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(piece, "rotation", 5, -5, 5, -5, 5, -5, 0);
        animation.setDuration(300);
        animation.start();
    }

    public void enviarAtras(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }
}