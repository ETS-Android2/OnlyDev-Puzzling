package com.example.myfirstapp;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;



public class AcopladorPieza implements View.OnTouchListener {
    private float xDelta;
    private float yDelta;
    private CreaPuzzle activity;

    // ----- AUDIO ------------ //
    private AudioManager manager;
    private SoundPool soundPool;
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private float volume;
    private boolean loaded;
    private int soundPieceSet;
    private int soundPiecePicked;
    private int soundGreat;
    private int soundLose;
    private static final int MAX_STREAMS = 5;

    public AcopladorPieza(CreaPuzzle activity) {
        this.activity = activity;
        setAudioSettings(activity);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();
        //Calcula una tolerancia del 10% del tamaño diagonal de la pieza.
        // Si la pieza está más cerca que este en la posición original, se ajustará en su lugar y su propiedad canMove se establecerá en falso.
        // De esta manera, la próxima vez que el jugador intente moverlo, ya no funcionará.

        final double tolerancia = sqrt(pow(view.getWidth(), 2) + pow(view.getHeight(), 2)) / 10;

        Piezas pieza = (Piezas) view;
        if (!pieza.canMove) {
            return true;
        }

        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            //La pieza sale al frente de las otras cuando se toca, en caso de que quede
            // parcialmente oscurecida por otras piezas, y se envíe a la parte posterior de la pila
            // cuando encaje en su lugar, por lo que no cubre ninguna otra pieza que pueda ser movida
            case MotionEvent.ACTION_DOWN:
                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                pieza.bringToFront();
                //Se llama al movimiento de la pieza.
                animPieceAlpha(pieza);
                playPickPieceSound();
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
                    playSetPieceSound();
                    //Se chequea estado del juego.
                    activity.checkGameOver();
                }

                break;
        }
        return true;
    }

    //Se crea el método que hace que la pieza se mueva.
    private void animPieceAlpha(Piezas piece) {
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(piece, "alpha", 0.3f, 1f);
        animation1.setDuration(600);
        animation1.start();
    }

    //Se crea el método que hace que la pieza rote en su posión.
    private void animPieceRotate(Piezas piece) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(piece, "rotation", 15, -15, 15, -15, 15, -15, 0);
        animation.setDuration(600);
        animation.start();
    }

    //Se crea el método que hace que la pieza emita sonido al tocarla.
    private void playPickPieceSound() {
        if (loaded) {
            float leftVolume = volume;
            float rightVolume = volume;
            this.soundPool.play(soundPiecePicked, leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    //Se crea el método que hace que la pieza emita sonido al posicionarla.
    private void playSetPieceSound() {
        if (loaded) {
            float leftVolume = volume;
            float rightVolume = volume;
            this.soundPool.play(soundPieceSet, leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    public void enviarAtras(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    private void setAudioSettings(Activity act) {
        manager = (AudioManager) act.getSystemService(Context.AUDIO_SERVICE);
        float currentVolumeIndex = (float) manager.getStreamVolume(streamType);
        float maxVolumeIndex = (float) manager.getStreamMaxVolume(streamType);
        volume = currentVolumeIndex * maxVolumeIndex;
        act.setVolumeControlStream(streamType);

        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        } else {
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        this.soundPieceSet = this.soundPool.load(act.getApplicationContext(), getFileResId(act, "clinck_sound"), 1);
        this.soundPiecePicked = this.soundPool.load(act.getApplicationContext(), getFileResId(act, "tap_sound"), 1);
    }

    private int getFileResId(Activity act, String resName) {
        return act.getResources().getIdentifier(resName, "raw", act.getPackageName());
    }
}