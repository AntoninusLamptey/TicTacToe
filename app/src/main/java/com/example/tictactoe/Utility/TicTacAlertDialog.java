package com.example.tictactoe.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class TicTacAlertDialog implements DialogInterface.OnClickListener {

    AlertDialog.Builder builder;


    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public TicTacAlertDialog(Context context, String title, String message){
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", this);
    }




}
