package com.example.myfirstapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicManager extends Service {
   MediaPlayer player;
   int op;
   Uri patchSound;
   String patchSoundString;

   public MusicManager(){


   }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R. raw.bso);
        player.setLooping(true); // talklive
    }

    @Override
    public void onDestroy() {

       super.onDestroy();
       player.stop();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        player.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       player.start();
       return super.onStartCommand(intent,flags,startId);
    }
}
