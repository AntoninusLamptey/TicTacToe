package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;


public class OptionActivity extends AppCompatActivity {


    SharedPreferences musicPreference;
    Switch musicSwitch;
    Boolean switchState;
    MediaPlayer songPlayer;
    SettingPreferences settingPreferences;
    MusicHandler optionMusic;

    SharedPreferences optionMusicSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        musicSwitch = findViewById(R.id.musicSwitch);
        optionMusicSettings = PreferenceManager.getDefaultSharedPreferences(this);

        songPlayer = MediaPlayer.create(this,R.raw.main_song);
        optionMusic  = new MusicHandler(songPlayer, this);
        settingPreferences = new SettingPreferences(this);


        if (optionMusic.currentPos != 0 && settingPreferences.getConfiguredSettings("MUSIC_STATE")) {

            optionMusic.seekMusic();
        }else if(optionMusic.currentPos == 0 && settingPreferences.getConfiguredSettings("MUSIC_STATE")){
            optionMusic.playMusic();
            }






        songPlayer = MediaPlayer.create(OptionActivity.this,R.raw.main_song);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        getWindow().setLayout((int)(displayMetrics.widthPixels * 0.6), (int) (displayMetrics.heightPixels * 0.6));

        Log.i("Anto", settingPreferences.getConfiguredSettings("MUSIC_STATE") + " music setting options menu" +
                "") ;

        musicSwitch.setChecked(settingPreferences.getConfiguredSettings("MUSIC_STATE"));

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingPreferences.configureSys("MUSIC_STATE", isChecked);

            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(optionMusic.isMusicPlaying()){
            optionMusic.currentPos = optionMusic.songPlayer.getCurrentPosition();
            optionMusic.stopMusic();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!optionMusic.isMusicPlaying() && settingPreferences.getConfiguredSettings("MUSIC_STATE")){
        optionMusic.seekMusic();
        }
//        if (optionMusic.currentPos != 0 && settingPreferences.getConfiguredSettings("MUSIC_STATE")) {
//            optionMusic.seekMusic();
//        }
    }
}
