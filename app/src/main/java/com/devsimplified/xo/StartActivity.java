package com.devsimplified.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;


public class StartActivity extends AppCompatActivity {

    private RadioButton radioButtonX, radioButtonO;
    private Button startButton;
    private String selectedOptionLevel;
    char selectedOption;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Switch switchOption = findViewById(R.id.switch_option);
        Switch switchOptionLevel = findViewById(R.id.switch_option_level);

        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchOption.isChecked()) {
                    selectedOption = 'O';
                } else {
                    selectedOption = 'X';
                }
                if (switchOptionLevel.isChecked()) {
                    selectedOptionLevel = "Difficult";
                } else {
                    selectedOptionLevel = "Easy";
                }
                startGame();
            }
        });








    }

    private void startGame() {
        // Start the game with the selected option (X or O)
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra("selectedOption", selectedOption);
        intent.putExtra("selectedOptionLevel", this.selectedOptionLevel);
        startActivity(intent);
        finish();
    }
}
