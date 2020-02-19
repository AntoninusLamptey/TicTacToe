package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreferences {

    Context context;

    public SettingPreferences(Context context) {

        this.context = context;
    }

    public boolean configureSys(String name, Boolean isMusicSet) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("isMusicSet", isMusicSet);
        return edit.commit();
    }

    public boolean getConfiguredSettings(String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isMusicSet", false);
    }


}
