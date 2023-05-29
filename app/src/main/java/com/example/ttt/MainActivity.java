package com.example.ttt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private TextView winnerDisplay;
    private GridView gridView;
    private GameAdapter gameAdapter;
    private Game game;
    private SoundPool soundPool;
    private int soundStartGame, soundWin, soundClick;
    private LinearLayout linesLayout;
    Button newGameStart;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameStart=(Button)findViewById(R.id.new_game_button);
        winnerDisplay=(TextView)findViewById(R.id.winner);
        gridView = findViewById(R.id.gridview);
        game = new Game();
        gameAdapter = new GameAdapter(this, game);
        gridView.setAdapter(gameAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               newGameStart.setVisibility(View.VISIBLE);
                if (game.isGameOver()) {
                    Toast.makeText(getApplicationContext(), "Game over, please start a new game", Toast.LENGTH_SHORT).show();
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
                        if (game.getWinningLine() != null) {
                            if (game.getSymbolAtPosition(game.getWinningLine()[0]) == 'X') {
                                // Player wins
                                winnerDisplay.setText("Player wins !");
                            } else {
                                // Computer wins
                                winnerDisplay.setText("Computer wins !");                            }
                        } else {
                            winnerDisplay.setText("It's a draw !");
                        }
                        playSound(soundWin);
                    }

                }
            }
        });

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        soundStartGame = soundPool.load(this, R.raw.start_sound, 1);
        soundWin = soundPool.load(this, R.raw.win_sound, 1);
        soundClick = soundPool.load(this, R.raw.click_sound, 1);
        playSound(soundStartGame);

    }

    public void startNewGame(View view) {
        game.reset();
        gameAdapter.notifyDataSetChanged();
        playSound(soundStartGame);
    }

    private void computerMove() {
        int position = game.getComputerMove();
        game.makeMove(position);
        Log.d("Computer Move", "Position: " + position);

        gameAdapter.notifyDataSetChanged();
        playSound(soundClick);

    }





    private void playSound(int soundId) {
        soundPool.play(soundId, 1, 1, 1, 0, 1);
    }
}
