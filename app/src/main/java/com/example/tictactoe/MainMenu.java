package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    SharedPreferences musicSettings;
    MusicHandler mainMusic;
    SettingPreferences settingsPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songPlayer = MediaPlayer.create(MainMenu.this,R.raw.main_song);

        settingsPref = new SettingPreferences(this);
        mainMusic = new MusicHandler(songPlayer, this);


        Log.i("Anton", settingsPref.getConfiguredSettings("MUSIC_STATE") + "main menu music state");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);
    }

    public void playGame(View view) {

        Intent startGame = new Intent(MainMenu.this,MainActivity.class);
        mainMusic.stopMusic();
        startActivity(startGame);
        finish();
    }

    public void optionsMenu(View view){

        Intent options = new Intent(this, OptionActivity.class);
        startActivity(options);
    }


    @Override
    protected void onStop() {
          super.onStop();


    }

    @Override
    protected void onPause() {
          super.onPause();
          mainMusic.currentPos = mainMusic.songPlayer.getCurrentPosition();
          Log.i("Anto", "Main menu in on pause");
          mainMusic.stopMusic();

    }

    @Override
    protected void onDestroy() {
          super.onDestroy();
          Log.i("Anto","in on destroy main menu");
          //mainMusic.stopMusic();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mainMusic.isMusicPlaying() && !settingsPref.getConfiguredSettings("MUSIC_STATE")){
            mainMusic.stopMusic();
        }
        if (mainMusic.currentPos != 0 && settingsPref.getConfiguredSettings("MUSIC_STATE")) {
            mainMusic.seekMusic();
        }
        if(mainMusic.currentPos == 0 && settingsPref.getConfiguredSettings("MUSIC_STATE")){
            mainMusic.playMusic();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("Anto", "Main menu on restart");
    }


}
