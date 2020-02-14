package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainMenu extends AppCompatActivity {

    MediaPlayer songPlayer;
    int mainCurrentPosition;
    static int resumeCount= 0;
    int screenChangeCount = 1;
    int playGameActivityCount = 0;

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
        songPlayer.stop();
    }

    public void optionsMenu(View view){

        Intent options = new Intent(this, OptionActivity.class);
        startActivity(options);
    }

    public void resumeMusic(){
        songPlayer = MediaPlayer.create(MainMenu.this,R.raw.main_song);
        songPlayer.start();
    }

    public void stopMusic(){
        if (songPlayer.isPlaying()){
            songPlayer.stop();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        mainCurrentPosition = songPlayer.getCurrentPosition();
        stopMusic();
        Log.i("Anto", "main menu on stop");


    }

    @Override
    protected void onPause() {
        super.onPause();
        mainCurrentPosition = songPlayer.getCurrentPosition();
        stopMusic();
        Log.i("Anto", "main menu on pause");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
        Log.i("Anto", "main menu on destroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeCount = resumeCount + 1;
        Log.i("Anto", "on resume main menu " + resumeCount);
//        //trying to resume from back button of actual game screen
        if(resumeCount > 1 && MainActivity.getActivityInstance() != null && screenChangeCount == MainActivity.getActivityInstance().getCurrentPosition().get(1))
        {

            mainCurrentPosition = MainActivity.getActivityInstance().getCurrentPosition().get(0);
            screenChangeCount = MainActivity.getActivityInstance().getCurrentPosition().get(1);
            songPlayer.seekTo(mainCurrentPosition);
            songPlayer.start();
            Log.i("Anto", " inside if main menu on resume");
        }else if(MainActivity.getActivityInstance() == null && resumeCount >1){
            songPlayer = MediaPlayer.create(MainMenu.this,R.raw.main_song);
            songPlayer.seekTo(mainCurrentPosition);
            songPlayer.start();
            Log.i("Anto", "Main menu else if on Resume");
        }else if(MainActivity.getActivityInstance() != null && screenChangeCount != MainActivity.getActivityInstance().getCurrentPosition().get(1)){
            songPlayer = MediaPlayer.create(MainMenu.this,R.raw.main_song);
            songPlayer.seekTo(mainCurrentPosition);
            songPlayer.start();
            Log.i("Anto", "Main menu second else if on Resume");

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("Anto", "Main menu on restart");
    }


}
