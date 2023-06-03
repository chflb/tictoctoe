package com.example.ttt;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class GameAdapter extends BaseAdapter {

    private Context context;
    private Game game;

    public GameAdapter(Context context, Game game) {
        this.context = context;
        this.game = game;
    }

    @Override
    public int getCount() {
        return game.BOARD_SIZE;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

  /*  @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
                   imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setBackgroundResource(R.drawable.cell_stroke);
        } else {
            imageView = (ImageView) convertView;
        }


        char symbol = game.getSymbolAtPosition(position);
        //Log.i("****", symbol+"");

        if (symbol == 'X') {
            if (game.getSelectedOption()=='X') {
                imageView.setImageResource(R.drawable.x_image);
            } else {
                imageView.setImageResource(R.drawable.o_image);
            }
        } else if (symbol == 'O') {

            if (game.getSelectedOption()=='X') {
                imageView.setImageResource(R.drawable.o_image);
            } else {
                imageView.setImageResource(R.drawable.x_image);
            }
        } else {
            imageView.setImageDrawable(null);
        }


        int[] winningLine = game.getWinningLine();
        if (winningLine != null) {
            // Check if the current position is part of the winning line
            boolean isWinningCell = false;
            for (int i = 0; i < winningLine.length; i++) {
                if (position == winningLine[i]) {
                    isWinningCell = true;
                    break;
                }
            }

            // Set a different background color for the winning line cells
            if (isWinningCell) {
               imageView.setBackgroundResource(R.drawable.winning_cell_background);

               // imageView.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
            } else {
                imageView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                imageView.setBackgroundResource(R.drawable.cell_stroke);

            }
        } else {
            imageView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            imageView.setBackgroundResource(R.drawable.cell_stroke);

        }
        return imageView;


    }

   */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout frameLayout;
        ImageView imageView;

        if (convertView == null) {
            frameLayout = new FrameLayout(context);
           // frameLayout.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int desiredSize ;// Divide by 3 for 3x3 grid
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Landscape mode
                  if (context.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                    // Tablet device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 4); // Adjust divisor for desired size
                } else {
                    // Smartphone device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.5); // Adjust divisor for desired size
                }
               // desiredSize = (int) (Math.min(screenWidth, screenHeight) / 4); // Adjust divisor for desired size

                // Apply specific behavior for landscape mode
                // For example, change the background color
                frameLayout.setBackgroundColor(Color.BLUE);
            } else if (context.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                // Tablet device
                desiredSize = (int) (Math.min(screenWidth, screenHeight) / 4.5); // Adjust divisor for desired size
            } else {
                // Smartphone device
                desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3); // Adjust divisor for desired size
            }
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(desiredSize, desiredSize));

            imageView = new ImageView(context);
            imageView.setLayoutParams(
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
           imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
          imageView.setScaleX(0.5F);
            imageView.setScaleY(0.5F);
            frameLayout.setBackgroundResource(R.drawable.cell_stroke);

            frameLayout.addView(imageView);
        } else {
            frameLayout = (FrameLayout) convertView;
            imageView = (ImageView) frameLayout.getChildAt(0);
        }

        char symbol = game.getSymbolAtPosition(position);

        if (symbol == 'X') {
            if (game.getSelectedOption()=='X') {
                imageView.setImageResource(R.drawable.x_image);

            } else {
                imageView.setImageResource(R.drawable.x_image);
            }
        } else if (symbol == 'O') {

            if (game.getSelectedOption()=='X') {
                imageView.setImageResource(R.drawable.o_image);//correct
            } else {
                imageView.setImageResource(R.drawable.o_image);
            }
        } else {
            imageView.setImageDrawable(null);
        }

        int[] winningLine = game.getWinningLine();
        if (winningLine != null) {
            // Check if the current position is part of the winning line
            boolean isWinningCell = false;
            for (int i = 0; i < winningLine.length; i++) {
                if (position == winningLine[i]) {
                    isWinningCell = true;
                    break;
                }
            }

            // Set a different background color for the winning line cells
            if (isWinningCell) {
                frameLayout.setBackgroundResource(R.drawable.winning_cell_background);

                // imageView.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
            } else {
                imageView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                frameLayout.setBackgroundResource(R.drawable.cell_stroke);

            }
        } else {
            imageView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
           frameLayout.setBackgroundResource(R.drawable.cell_stroke);

        }
        return frameLayout;
    }

}
