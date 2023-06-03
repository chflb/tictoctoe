package com.example.ttt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {


    private TextView playerScoreTextView;
    private TextView computerScoreTextView;
    private ImageView playerOptionImageView;
    private ImageView computerOptionImageView;
   // private ImageView winnerDisplay;
    private GridView gridView;
    private GameAdapter gameAdapter;
    private Game game;
    private SoundPool soundPool;
    private int soundWin, soundClick;
    private LinearLayout linesLayout;
    Button newGameStart;
    CardView cardView;
    private char selectedOption;
    private int soundFail;
    ImageView restart, back;
    private static final String KEY_GAME = "game";
    private static final String KEY_SELECTED_OPTION = "selectedOption";
    private int scorePlayer, scoreComputer;
    private static final String PLAYER_SCORE_KEY = "player_score";
    private static final String COMPUTER_SCORE_KEY = "computer_score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerScoreTextView = findViewById(R.id.playerScoreTextView);
        computerScoreTextView = findViewById(R.id.computerScoreTextView);
        playerOptionImageView = findViewById(R.id.playerOptionImageView);
        computerOptionImageView = (ImageView) findViewById(R.id.computerOptionImageView);

restart= findViewById(R.id.restart);
back=findViewById(R.id.back);

restart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        game.reset();
        //winnerDisplay.setVisibility(View.INVISIBLE);
        gameAdapter.notifyDataSetChanged();


    }
});
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
        finish();
    }
});
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedOption = extras.getChar("selectedOption");
        }
        game = new Game();
        if (savedInstanceState != null) {
            // Restore the game state and selected option
            game = savedInstanceState.getParcelable(KEY_GAME);
            selectedOption = savedInstanceState.getChar(KEY_SELECTED_OPTION);
        } else {
            // Create a new game
            game = new Game();
        }

        // Rest of your code...


        game.setSelectedOption(selectedOption);
        if(selectedOption=='X'){
            Log.i("+++","X");
            playerOptionImageView.setImageResource(R.drawable.x_image);
            computerOptionImageView.setImageResource(R.drawable.o_image);
        }else if (selectedOption=='O'){
            Log.i("+++","O");
            computerOptionImageView.setImageResource(R.drawable.x_image);
            playerOptionImageView.setImageResource(R.drawable.o_image);
        }
         Log.i("===",selectedOption+"");
        // Log.i("===",selectedOption+"");

        //winnerDisplay=findViewById(R.id.winner);
        gridView = findViewById(R.id.gridview);
        gameAdapter = new GameAdapter(this, game);
        gridView.setAdapter(gameAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                restart.setVisibility(View.VISIBLE);
                if (game.isGameOver()) {
                    Toast.makeText(getApplicationContext(), "Game over, please start a new game", Toast.LENGTH_SHORT).show();
                   // winnerDisplay.setVisibility(View.VISIBLE);
                    return;
                }
                if (!game.isCellEmpty(position)) {
                    return; // Cell is already occupied, ignore the click
                }
                if (game.isCellEmpty(position)) {
                    game.makeMove(position);
                    gameAdapter.notifyDataSetChanged();
                    playSound(soundClick);
                    if (!game.isGameOver()) {
                        computerMove();
                    }
                    if (game.isGameOver()) {
                       // winnerDisplay.setVisibility(View.VISIBLE);

                        if (game.getWinningLine() != null) {
                            if (game.getSymbolAtPosition(game.getWinningLine()[0]) == selectedOption) {
                                // Player wins
                            //    winnerDisplay.setImageResource(R.drawable.like);
                                playSound(soundFail);
                                scorePlayer++;
                                playerScoreTextView.setText(scorePlayer+"");
                               // computerScoreTextView.setText("0");
                            } else {
                                // Computer wins
                            //    winnerDisplay.setImageResource(R.drawable.dislike);
                                playSound(soundFail);
                                scoreComputer++;
                                computerScoreTextView.setText(scoreComputer+"");
                               // playerScoreTextView.setText("0");
                            }
                        }  else {
                                //winnerDisplay.setImageResource(R.drawable.equity);
                            playSound(soundFail);
                        }
                        }
                    }

                }

        });

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        //soundStartGame = soundPool.load(this, R.raw.start_sound, 1);
        //soundWin = soundPool.load(this, R.raw.win_sound, 1);
        soundFail = soundPool.load(this, R.raw.fail_sound, 1);

        soundClick = soundPool.load(this, R.raw.click_sound, 1);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the game state and selected option
        outState.putParcelable(KEY_GAME, game);
        outState.putChar(KEY_SELECTED_OPTION, selectedOption);
        outState.putInt("12", restart.getVisibility());
        outState.putInt(PLAYER_SCORE_KEY, scorePlayer);
        outState.putInt(COMPUTER_SCORE_KEY, scoreComputer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the game state and selected option
        game = savedInstanceState.getParcelable(KEY_GAME);
        selectedOption = savedInstanceState.getChar(KEY_SELECTED_OPTION);

        // Update your UI based on the restored state if needed
        gameAdapter.notifyDataSetChanged();
       // winnerDisplay.setVisibility(game.isGameOver() ? View.VISIBLE : View.INVISIBLE);
        restart.setVisibility(savedInstanceState.getInt("12"));
        scorePlayer = savedInstanceState.getInt(PLAYER_SCORE_KEY);
        scoreComputer = savedInstanceState.getInt(COMPUTER_SCORE_KEY);
        playerScoreTextView.setText( scorePlayer+"");
        computerScoreTextView.setText(scoreComputer+"");
    }



    private void computerMove() {
       // int position = game.getComputerMoveWeakLevel();
        int position = game.getComputerMoveAdvancedLevel();
        game.makeMove(position);
        Log.d("Computer Move", "Position: " + position);

        gameAdapter.notifyDataSetChanged();
        playSound(soundClick);

    }





    private void playSound(int soundId) {
        soundPool.play(soundId, 1, 1, 1, 0, 1);
    }
}
