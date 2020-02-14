package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class OptionActivity extends AppCompatActivity {

    Switch musicSwitch;
    Boolean switchState;
    MediaPlayer songPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        songPlayer = MediaPlayer.create(OptionActivity.this,R.raw.main_song);
        musicSwitch = findViewById(R.id.musicSwitch);

        songPlayer = MediaPlayer.create(OptionActivity.this,R.raw.main_song);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

       getWindow().setLayout((int)(displayMetrics.widthPixels * 0.7), (int) (displayMetrics.heightPixels * 0.5));

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    songPlayer.start();
                }
                else{
                    if (songPlayer.isPlaying()){
                        songPlayer.pause();

                    }
//                    songPlayer = MediaPlayer.create(OptionActivity.this,R.raw.main_song);
                }
            }
        });

    }


    public void musicOption(){


    }

}
