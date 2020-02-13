/*
 * Timothy Carta
 * 2/14/2020
 * Simple TicTacToe application. Allows the user to play with both the AI and other players.
 */

package edu.quinnipiac.ser210.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{
    TicTacToe tictactoe;
    boolean computerActive;
    int currentState = tictactoe.PLAYING;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, resetButton;
    TextView currPlayerText;
    TextView greetingTxt;
    Button[] buttons;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("computerActive",computerActive); //Saves whether or not computer is active
        outState.putInt("currentState",currentState); //Saves the current play state
        outState.putString("greeting", greetingTxt.getText().toString()); //Saves the greeting text
        outState.putString("currentPlayerText", currPlayerText.getText().toString()); //Saves the current player text
        ///saves the player at each location so that it can be restored
        outState.putInt("loc0",tictactoe.getPlayerAtLocation(0));
        outState.putInt("loc1",tictactoe.getPlayerAtLocation(1));
        outState.putInt("loc2",tictactoe.getPlayerAtLocation(2));
        outState.putInt("loc3",tictactoe.getPlayerAtLocation(3));
        outState.putInt("loc4",tictactoe.getPlayerAtLocation(4));
        outState.putInt("loc5",tictactoe.getPlayerAtLocation(5));
        outState.putInt("loc6",tictactoe.getPlayerAtLocation(6));
        outState.putInt("loc7",tictactoe.getPlayerAtLocation(7));
        outState.putInt("loc8",tictactoe.getPlayerAtLocation(8));

        //Save the state of the buttons
        boolean[] lockedButtons = {btn0.isClickable(), btn1.isClickable(), btn2.isClickable(), btn3.isClickable(),
                btn4.isClickable(), btn5.isClickable(), btn6.isClickable(), btn7.isClickable(), btn8.isClickable()};
        outState.putBooleanArray("lockedButtons", lockedButtons); //Saves all of the buttons status'
        outState.putBoolean("resetButton", resetButton.isClickable()); //Saves reset button status
        //save which players turn it was
        outState.putInt("currentPlayer",tictactoe.getCurrentPlayer());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        currPlayerText = findViewById(R.id.currentPlayer); //gets a reference to the current player text.

        //Get Reference to Buttons
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        resetButton = findViewById(R.id.btnReset);

        buttons = new Button[]{btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8};

        //Make the buttons all communicate with this onClick method
        for (int i = 0; i <= 8; i++) {
            buttons[i].setOnClickListener(this);
        }

        greetingTxt = findViewById(R.id.txtGreeting); //gets reference to greetingText

        tictactoe = new TicTacToe(); //Creates a new instance of TicTacToe

        if (savedInstanceState != null) { //If orientation is flipped, restore the game.
            System.out.println("SCREEN FLIPPED");
            computerActive = savedInstanceState.getBoolean("computerActive"); //Restores whether or not computer is active
            currentState = savedInstanceState.getInt("currentState"); //Restores the current play state
            greetingTxt.setText(savedInstanceState.getString("greeting")); //Restores the greeting text
            currPlayerText.setText(savedInstanceState.getString("currentPlayerText")); //Restores the current player text
            //Fixes the board state
            tictactoe.setMove(savedInstanceState.getInt("loc0"),0);
            tictactoe.setMove(savedInstanceState.getInt("loc1"),1);
            tictactoe.setMove(savedInstanceState.getInt("loc2"),2);
            tictactoe.setMove(savedInstanceState.getInt("loc3"),3);
            tictactoe.setMove(savedInstanceState.getInt("loc4"),4);
            tictactoe.setMove(savedInstanceState.getInt("loc5"),5);
            tictactoe.setMove(savedInstanceState.getInt("loc6"),6);
            tictactoe.setMove(savedInstanceState.getInt("loc7"),7);
            tictactoe.setMove(savedInstanceState.getInt("loc8"),8);
            //Restore the current player
            tictactoe.setCurrentPlayer(savedInstanceState.getInt("currentPlayer"));

            //fix the state of the buttons
            fixButtons(savedInstanceState.getBooleanArray("lockedButtons"));
            resetButton.setClickable(savedInstanceState.getBoolean("resetButton"));
        } else {
            System.out.println("NEW GAME");
            computerActive = getIntent().getBooleanExtra("computerActive", true); //recieces info from SP vs MP buttons
            String greeting = "Hello, " + getIntent().getStringExtra("name"); //Gets the users name
            greetingTxt.setText(greeting);
            resetGame(null); //Default to resetting the game
        }
        updateTurn();
    }

    /*
    Reset all values and make all buttons reset
     */
    public void resetGame(View view) {
        currentState = ITicTacToe.PLAYING; //Resets the state
        tictactoe.clearBoard(); //Clears the board on the backend
        tictactoe.setCurrentPlayer(1); //Makes X go first
        currPlayerText.setText("Player Turn: X"); //Resets the text
        ///Resets all the buttons
        for (int i = 0; i <= 8; i++) {
            buttons[i].setText("");
        }
        unPauseButtons(); //Lets the player play again
        updateUI();
    }


    private String getSymbol(int cellContent) {
        if (cellContent == 2) { //Player O
            return "O";
        } else if (cellContent == 1){//Player X
            return "X";
        } else {//Empty Space
            return "";
        }
    }

    /*
    Used to update the text for the game and check for the winner
     */
    private void updateTurn() {
        currentState = tictactoe.checkForWinner(); //Checks for the winner

        if (tictactoe.getCurrentPlayer() == 1) {
            currPlayerText.setText("Player Turn: X");
        } else {
            currPlayerText.setText("Player Turn: O");
        }

        if (currentState == ITicTacToe.CROSS_WON) {
            currPlayerText.setText("'X' Won!");
            pauseButtons(); //Stops player from playing
        } else if (currentState == ITicTacToe.NOUGHT_WON) {
            currPlayerText.setText("'O' Won");
            pauseButtons(); //Stops player from playing
        } else if (currentState == ITicTacToe.TIE) {
            currPlayerText.setText("TIE!");
            pauseButtons(); //Stops player from playing
        }
        updateUI(); //Fixes all of the button text
    }

    /*
    gets the move from the computer, places it in the board, and then disables the button associated with the move
     */
    private void computerTurn() {
        currentState = tictactoe.checkForWinner(); //Checks for the winner
        if (computerActive && currentState == ITicTacToe.PLAYING) {
            int location = tictactoe.getComputerMove(); //finds the computers move
            tictactoe.setMove(0, location); //populates the board with the computers move
            switch (location) { //Make the button no longer clickable to the player when the AI has pressed it
                case 0:
                    btn0.setClickable(false);
                    break;
                case 1:
                    btn1.setClickable(false);
                    break;
                case 2:
                    btn2.setClickable(false);
                    break;
                case 3:
                    btn3.setClickable(false);
                    break;
                case 4:
                    btn4.setClickable(false);
                    break;
                case 5:
                    btn5.setClickable(false);
                    break;
                case 6:
                    btn6.setClickable(false);
                    break;
                case 7:
                    btn7.setClickable(false);
                    break;
                case 8:
                    btn8.setClickable(false);
                    break;
            }
            }

    }

    /*
    updates the UI of the buttons with their appropriate symbols in relation to the back end.
     */
    private void updateUI() {
        for (int i = 0; i <= 8; i++) {
            buttons[i].setText(getSymbol(tictactoe.getSymbolAtLocation(i)));
        }
    }

    private void fixButtons(boolean[] locked) {
        for (int i = 0; i < 8; i++) {
            buttons[i].setClickable(locked[i]);
        }
    }

    //Make the buttons pressable
    private void pauseButtons() {
        for (int i = 0; i <= 8; i++) {
            buttons[i].setClickable(false);
        }
        resetButton.setClickable(true); //make the user able to press the reset button
    }

    //Make the buttons pressable
    private void unPauseButtons() {
        for (int i = 0; i <= 8; i++) {
            buttons[i].setClickable(true);
        }
        resetButton.setClickable(false); //make the user unable to press the reset button
    }

    /*
    Makes the game respond to the buttons that are pressed. Each button will
    set the move in the backend and update the image on the button.
    All buttons function in the same way, so only one is explained
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn0: //If the top-left button is pressed
                if (tictactoe.isValidMove(0)) { //Make sure that the button is a valid move
                    tictactoe.setMove(tictactoe.getCurrentPlayer(),0); //Populate the board with the players move
                    btn0.setClickable(false); //Make the button non-clickable
                }
                computerTurn(); //Process the computers turn
                updateTurn(); //Update everything. Check for winner.
                break;
            case R.id.btn1:
                if (tictactoe.isValidMove(1)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 1);
                    btn1.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
            case R.id.btn2:
                if (tictactoe.isValidMove(2)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 2);
                    btn2.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
            case R.id.btn3:
                if (tictactoe.isValidMove(3)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 3);
                    btn3.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
            case R.id.btn4:
                if (tictactoe.isValidMove(4)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 4);
                    btn4.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
            case R.id.btn5:
                if (tictactoe.isValidMove(5)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 5);
                    btn5.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
            case R.id.btn6:
                if (tictactoe.isValidMove(6)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 6);
                    btn6.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
            case R.id.btn7:
                if (tictactoe.isValidMove(7)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 7);
                    btn7.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
            case R.id.btn8:
                if (tictactoe.isValidMove(8)) {
                    tictactoe.setMove(tictactoe.getCurrentPlayer(), 8);
                    btn8.setClickable(false);
                }
                computerTurn();
                updateTurn();
                break;
        }
    }
}
