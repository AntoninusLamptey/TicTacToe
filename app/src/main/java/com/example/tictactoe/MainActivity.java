package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private boolean player1Turn = true;
    private int roundCount = 0;
    private int player1Points = 0;
    private int player2Points = 0;
    private static String[][] board;

    MusicHandler gameMusic;
    SettingPreferences gameSettings;


    private ArrayList<String [][]> allBoards;

    private int testing = 0;
    MediaPlayer songContinue;
    int current_pos;
    int accessCount = 0;
    ArrayList<Integer> activityMonitor;


    private TextView player1TextView;
    private TextView player2TextView;
    AlertDialog.Builder gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        songContinue = MediaPlayer.create(MainActivity.this,R.raw.main_song);
        gameMusic = new MusicHandler(songContinue,this);

        gameSettings = new SettingPreferences(this);



        Log.i("Anton", gameMusic.currentPos + "  on create game screen");

        if (gameMusic.currentPos != 0 && gameSettings.getConfiguredSettings("MUSIC_STATE")) {
            Log.i("Anton", gameMusic.currentPos + "  on create game screen if current pos is != 0");
            //gameMusic.seekMusic();
        }else if(gameMusic.currentPos == 0 && gameSettings.getConfiguredSettings("MUSIC_STATE")){
            Log.i("Anton", gameMusic.currentPos + "  on create game screen second if current pos is == 0");
            gameMusic.playMusic();
        }

        player1TextView = findViewById(R.id.player1);

        player2TextView = findViewById(R.id.player2);

        gameOver = new AlertDialog.Builder(this);

        board = new String[3][3];
        allBoards = new ArrayList<String[][]>();


        boardSet();
        allBoards.add(0,board);

    }



    //function defines behavior of when a user plays, i.e. clicks any button on the grid

    public void playerPlayed(View view) {
        Button button = (Button) view;
        String userSelect = button.getText().toString();


        //adding current board to arraylist containing all board states per round. Using roundcount as index.

        allBoards.add(roundCount,deepCopyStrMatrix(board));


        //getting i,j index of what button was just clicked on the grid
        int i = Character.getNumericValue(button.getTag().toString().charAt(4));
        int j = Character.getNumericValue(button.getTag().toString().charAt(5));


        //Printing of X or O on buttons depending on button clicked by user
        if (userSelect.equals("") && player1Turn) {
            button.setText("X");
            roundCount++;


        } else if (userSelect.equals("") && !player1Turn) {
            button.setText("O");
            roundCount++;
        }

        //copying board most recently played button into board state array
        board[i][j] = button.getText().toString();
        Boolean whoWon = winner();

        //checking for winner to display dialog box
        if (player1Turn == true && whoWon == true) {
            player1TextView.setText("Player 1: " + ++player1Points);
            whoWon = false;
            boardSet();

            //Alerts
            gameOver.setMessage("Game over, Player 1 Won");
            gameOver.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameWinReset();
                    boardSet();
                }
            });
            AlertDialog gameEnded = gameOver.create();
            gameEnded.show();

        } else if (player1Turn == false && whoWon == true) {
            player2TextView.setText("Player 2: " + ++player2Points);
            whoWon = false;
            boardSet();

            gameOver.setMessage("Game over, Player 2 Won");
            gameOver.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameWinReset();
                    boardSet();
                }
            });
            AlertDialog gameEnded = gameOver.create();
            gameEnded.show();

        } else if (roundCount == 9) {
            whoWon = false;
            boardSet();

            gameOver.setMessage("Game over, it is a draw.");
            gameOver.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gameWinReset();
                    boardSet();
                }
            });
            AlertDialog gameEnded = gameOver.create();
            gameEnded.show();

        }
        player1Turn = !player1Turn;



    }

    //function to determine winner of the game

    public boolean winner() {

        //columns

        if (board[0][0].equals(board[1][0]) && board[0][0].equals(board[2][0])) {
            return true;
        }

        if (board[0][1].equals(board[1][1]) && board[0][1].equals(board[2][1])) {

            return true;
        }

        if (board[0][2].equals(board[1][2]) && board[0][2].equals(board[2][2])) {
            return true;
        }

        //rows

        if (board[0][0].equals(board[0][1]) && board[0][0].equals(board[0][2])) {

            return true;
        }

        if (board[1][0].equals(board[1][1]) && board[1][0].equals(board[1][2])) {

            return true;
        }

        if (board[2][0].equals(board[2][1]) && board[2][0].equals(board[2][2])) {

            return true;
        }

        //diagonals

        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return true;
        }

        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return true;
        }
        return false;
    }

    //reset board button behavior

    public void resetBoard(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button b = (Button) findViewById(getResources().getIdentifier("grid" + i + j, "id", getPackageName()));
                b.setText("");
            }
        }
        boardSet();
    }



    //function to reset the board state array
    public void boardSet() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Integer.toString(testing);
                testing++;
            }
        }
        roundCount = 0;
        testing = 0;
    }




    //function to set board to initial blank state after game has ended or been drawn
    public void gameWinReset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button b = (Button) findViewById(getResources().getIdentifier("grid" + i + j, "id", getPackageName()));
                b.setText("");
                roundCount = 0;
            }
        }
        boardSet();
    }


    //undo button behavior
    public void undoLast(View view)
    {
        Button eachButton;
        Resources res = getResources();

        if(roundCount >0) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int id = res.getIdentifier("grid" + i + j, "id", getBaseContext().getPackageName());
                    eachButton = findViewById(id);
                    String cellValue = allBoards.get(roundCount - 1)[i][j];
                    if (cellValue.equals("X") || cellValue.equals("O")) {
                        eachButton.setText(cellValue);

                    } else {
                        eachButton.setText("");

                    }
                }
            }

            board = deepCopyStrMatrix(allBoards.get(roundCount - 1));
            player1Turn = !player1Turn;
            roundCount--;
        }

    }

    //to allow deep copies of arrays, copied from: https://stackoverflow.com/a/9106176/2711811
    public static String[][] deepCopyStrMatrix(String[][] input) {
        if (input == null)
            return null;
        String[][] result = new String[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }


    //back button behavior

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent backButton = new Intent(MainActivity.this,MainMenu.class);
            startActivity(backButton);
            gameMusic.currentPos = gameMusic.songPlayer.getCurrentPosition();
            //backButton.putExtra("current_music_backposition", songContinue.getCurrentPosition());
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    //add a counter to return to indicate activity has been there. Returning an arraylist
    public ArrayList<Integer> getCurrentPosition(){
        activityMonitor = new ArrayList<Integer>();
        activityMonitor.add(songContinue.getCurrentPosition());
        accessCount++;
        activityMonitor.add(accessCount);
        return activityMonitor;

    }

    //stop music function

//    public void stopMusic(){
//        if (songContinue.isPlaying()){
//            current_pos = songContinue.getCurrentPosition();
//            songContinue.stop();
//
//        }
//
//    }
//
//    public void resumeSong(){
//        songContinue = MediaPlayer.create(MainActivity.this,R.raw.main_song);
//        songContinue.seekTo(this.current_pos);
//        songContinue.start();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        gameMusic.currentPos = gameMusic.songPlayer.getCurrentPosition();
        gameMusic.stopMusic();
        Log.i("Anton", "on pause gme screen, current pos:");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!gameMusic.songPlayer.isPlaying() && gameSettings.getConfiguredSettings("MUSIC_STATE")){
            gameMusic.seekMusic();
        }
        Log.i("Anton", "on resume game screen");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //resumeSong();
        Log.i("Anton", "on restart urrent pos:" + current_pos);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stopMusic();
        Log.i("Anton", "on stop current pos:" + current_pos);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //stopMusic();
        Log.i("Anton", "on destroycurrent pos:" + current_pos);
    }
}

