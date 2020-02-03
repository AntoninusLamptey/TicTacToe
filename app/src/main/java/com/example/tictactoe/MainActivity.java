package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.Utility.TicTacAlertDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private boolean player1Turn = true;
    private int roundCount = 1;
    private int player1Points = 0;
    private int player2Points = 0;
    private static String[][] board;



    private ArrayList<String [][]> allBoards;
    private int testing = 0;

    private TextView player1TextView;
    private TextView player2TextView;
    AlertDialog.Builder gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        allBoards.add(roundCount,board);
        Log.i("Anton", "From button on grid: ");
        //debugging code to see content of arraylist
        for(int a = 0; a <3; a++){
            for(int b = 0; b<3;b++){
                Log.i("Anton", "\n" + allBoards.get(0)[a][b]);
            }
        }




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
            new TicTacAlertDialog(this, "Helo", "you won");
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
    public void undoLast(View view) {
        Button eachButton;
        Resources res = getResources();

        //debugging code for weird arraylist behavior
        Log.i("Anton", "From undo button: ");
        for(int i = 0; i<3;i++){
            for(int j = 0; j<3; j++){

                Log.i("Anton", "\n\n" + allBoards.get(0)[i][j]);
            }
        }


        //Resetting board to all blank provided undo button is clicked when round count =1
        if(roundCount ==1)
        {
            //set entire  grid to blank
            for(int i = 0; i <3; i++)
            {
                for(int j = 0; j<3;j++)
                {
                    int id = res.getIdentifier("grid" + i + j, "id", getBaseContext().getPackageName());
                    eachButton = findViewById(id);
                        eachButton.setText("");
                }
            }


        }
        else{
            for(int i = 0; i <3; i++)
            {
                for(int j = 0; j<3;j++)
                {
                    int id = res.getIdentifier("grid" + i + j, "id", getBaseContext().getPackageName());
                    eachButton = findViewById(id);
                    String cellValue = allBoards.get(roundCount-2)[i][j];
                    if(cellValue.equals("X") || cellValue.equals("O"))
                    {
                        eachButton.setText(cellValue);
                    }else{
                        eachButton.setText("");
                    }
                }
            }

            //use index of 0 for rounnd 1
        }
    }
}
