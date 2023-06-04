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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Switch switchOption = findViewById(R.id.switch_option);
        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char selectedOption;
                if (switchOption.isChecked()) {
                    selectedOption = 'O';
                } else {
                    selectedOption = 'X';
                }
                startGame(selectedOption);
            }
        });








    }

    private void startGame(char selectedOption) {
        // Start the game with the selected option (X or O)
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra("selectedOption", selectedOption);
        startActivity(intent);
        finish();
    }
}
