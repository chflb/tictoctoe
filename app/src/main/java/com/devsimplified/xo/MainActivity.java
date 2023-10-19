package com.devsimplified.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.lang.reflect.Method;


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
    private String selectedOptionLevel;
    private boolean isSoundEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#860144A6")));

        playerScoreTextView = findViewById(R.id.playerScoreTextView);
        computerScoreTextView = findViewById(R.id.computerScoreTextView);
        playerOptionImageView = findViewById(R.id.playerOptionImageView);
        computerOptionImageView = (ImageView) findViewById(R.id.computerOptionImageView);

restart= findViewById(R.id.restart);
back=findViewById(R.id.back);

restart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
           // game.setPlayerStart(!game.isPlayerStarts());

        game.reset();
        //winnerDisplay.setVisibility(View.INVISIBLE);
        gameAdapter.notifyDataSetChanged();
       if (!game.isPlayerStarts()) {
           computerMove();
          // game.setPlayerStart(true);
       }



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
            selectedOptionLevel = extras.getString("selectedOptionLevel") ;
        isSoundEnabled = getIntent().getBooleanExtra("isSoundEnabled", true);
            ;
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
                    return;
                }
                if (!game.isCellEmpty(position)) {
                    return; // Cell is already occupied, ignore the click
                }
                game.makeMove(position);
                gameAdapter.notifyDataSetChanged();
                playSound(soundClick);

                if (!game.isGameOver()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computerMove();
                        }
                    }, 200);
                } else {
                    // The game is over, determine the winner
                    if (game.getWinningLine() != null) {

                        if (game.getSymbolAtPosition(game.getWinningLine()[0]) == selectedOption) {
                            // Player wins
                            playSound(soundFail);
                            scorePlayer++;
                            playerScoreTextView.setText(String.valueOf(scorePlayer));
                        } else {
                            // Computer wins
                            playSound(soundFail);
                            scoreComputer++;
                            computerScoreTextView.setText(String.valueOf(scoreComputer));
                        }
                    } else {
                        // It's a tie
                        playSound(soundFail);
                    }

                    // Switch the player start for the next game
                    game.setPlayerStart(!game.isPlayerStarts());
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
        // Delay the computer's move by 500 milliseconds (0.5 seconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int positionDifficult = game.getComputerMoveAdvancedLevel();
                int positionEasy = game.getComputerMoveWeakLevel();

                if (selectedOptionLevel.equals("Easy")) {
                    game.makeMove(positionEasy);
                } else if (selectedOptionLevel.equals("Difficult")) {
                    game.makeMove(positionDifficult);
                }

                gameAdapter.notifyDataSetChanged();
                playSound(soundClick);

                // Check if the game is over after the computer's move
                if (game.isGameOver()) {
                    // The game is over, determine the winner
                    if (game.getWinningLine() != null) {
                        if (game.getSymbolAtPosition(game.getWinningLine()[0]) == selectedOption) {
                            // Player wins
                            playSound(soundFail);
                            scorePlayer++;
                            playerScoreTextView.setText(String.valueOf(scorePlayer));
                        } else {
                            // Computer wins
                            playSound(soundFail);
                            scoreComputer++;
                            computerScoreTextView.setText(String.valueOf(scoreComputer));
                        }
                    } else {
                        // It's a tie
                        playSound(soundFail);
                    }

                    // Switch the player start for the next game
                    game.setPlayerStart(!game.isPlayerStarts());
                }
            }
        }, 200);
    }





    private void playSound(int soundId) {
        if (isSoundEnabled) {
            soundPool.play(soundId, 1, 1, 1, 0, 1);
        }    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_start, menu);
       // menu.findItem(R.id.action_theme).setIntent(new Intent(this, MainActivity.class));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

     /*   if (itemId == R.id.action_sound) {
            isSoundEnabled = !isSoundEnabled;
            return true;
        } else if (itemId == R.id.action_theme) {
            toggleTheme();
            return true;
        } else*/
        if (itemId == R.id.action_policy) {
           /* String policyUrl = "https://www.iubenda.com/privacy-policy/96302661";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(policyUrl));
            startActivity(browserIntent);*/

            startActivity(new Intent(getApplicationContext(), PrivacyPolicy.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }



    private void toggleTheme() {
        int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentTheme == Configuration.UI_MODE_NIGHT_YES) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        } else {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#860144A6")));
        }
        recreate();
    }
}
