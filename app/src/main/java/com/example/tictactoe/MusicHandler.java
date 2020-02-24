package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MusicHandler{


    static MediaPlayer songPlayer;
    static int currentPos;
    SharedPreferences settings;
    Context mainContext;

    public MusicHandler(MediaPlayer newSongPlayer, Context activityContext) {
        songPlayer = newSongPlayer;
        mainContext = activityContext;
    }

    public void playMusic(){
            songPlayer = MediaPlayer.create(mainContext,R.raw.main_song);
            songPlayer.start();
            Log.i("Anto", "inside music handler music off");

    }

    public void stopMusic(){
        if (songPlayer.isPlaying()) {
            songPlayer.stop();
        }
    }

    public boolean isMusicPlaying(){
        return songPlayer.isPlaying();
    }

    public void seekMusic(){
        songPlayer = MediaPlayer.create(mainContext,R.raw.main_song);
        songPlayer.seekTo(currentPos);
        songPlayer.start();
    }


}
