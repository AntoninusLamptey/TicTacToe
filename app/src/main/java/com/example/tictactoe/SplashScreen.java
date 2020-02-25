package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    SettingPreferences settingPreferences;

    Handler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingPreferences = new SettingPreferences(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Log.i("Anto", settingPreferences.getConfiguredSettings("MUSIC_STATE") + " music setting") ;

//       if(!settingPreferences.getConfiguredSettings("MUSIC_STATE")){
//           settingPreferences.configureSys("MUSIC_STATE", true);
//       }

        myHandler = new Handler();
        setContentView(R.layout.activity_splash_screen);
         myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashScreen.this,MainMenu.class);
                startActivity(splashIntent);
                finish();

            }
        },1700);
    }
}
