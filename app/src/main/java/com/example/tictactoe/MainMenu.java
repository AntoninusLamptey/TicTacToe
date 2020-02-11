package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainMenu extends AppCompatActivity {

    MediaPlayer songPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songPlayer = MediaPlayer.create(MainMenu.this,R.raw.main_song);
        songPlayer.start();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);
    }

    public void playGame(View view) {

        Intent startGame = new Intent(MainMenu.this,MainActivity.class);
        startGame.putExtra("current_music_position", songPlayer.getCurrentPosition());
        startActivity(startGame);
        finish();
    }

    public void stopMusic(){
        if (songPlayer.isPlaying()){
            songPlayer.stop();
        }

    }

    @Override
    protected void onStop() {
        stopMusic();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMusic();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }
}
