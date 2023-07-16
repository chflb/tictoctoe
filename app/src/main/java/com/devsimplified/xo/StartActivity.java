package com.devsimplified.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

import java.lang.reflect.Method;


public class StartActivity extends AppCompatActivity {

    private RadioButton radioButtonX, radioButtonO;
    private Button startButton;
    private String selectedOptionLevel;
    char selectedOption;
    private boolean isSoundEnabled ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setBackgroundDrawable
                (new ColorDrawable(Color.parseColor("#860144A6")));

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
        intent.putExtra("isSoundEnabled", isSoundEnabled);

        startActivity(intent);
        finish();
    }

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

       /* if (itemId == R.id.action_sound) {
            isSoundEnabled = !isSoundEnabled;
            return true;
        } else if (itemId == R.id.action_theme) {
            toggleTheme();
            return true;
        } else */
        if (itemId == R.id.action_policy) {
            String policyUrl = "https://www.iubenda.com/privacy-policy/96302661";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(policyUrl));
            startActivity(browserIntent);
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
            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#860144A6"));
        }
        recreate();
    }
}
