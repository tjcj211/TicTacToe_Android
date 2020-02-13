/*
 * Timothy Carta
 * 2/14/2020
 * Used to get information from the user and have them choose whether to play against AI or against another player
 */

package edu.quinnipiac.ser210.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //When the user presses this button, there will be no AI
    public void startGameWithPlayers(View view) {
        startGame(false);
    }

    //When the user presses this button, there will be AI
    public void startGameWithComputer(View view) {
        startGame(true);
    }

    //Starts a new activity
    private void startGame(boolean computerActive) {
        EditText textBox = findViewById(R.id.txtName);
        String name = textBox.getText().toString();
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("name",name); //Passes the username
        intent.putExtra("computerActive", computerActive); //Passes whether the computer is active
        startActivity(intent);
    }
}
