package edu.quinnipiac.ser210.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGameWithPlayers(View view) {
        startGame(false);
    }

    public void startGameWithComputer(View view) {
        startGame(true);
    }

    private void startGame(boolean computerActive) {
        EditText textBox = findViewById(R.id.txtName);
        String name = textBox.getText().toString();
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("computerActive", computerActive);
        startActivity(intent);
    }
}
