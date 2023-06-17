package com.devsimplified.xo;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.devsimplified.xo.Game;

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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout frameLayout;
        ImageView imageView;

        imageView = new ImageView(context);
        imageView.setLayoutParams(
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        if (convertView == null) {
            frameLayout = new FrameLayout(context);
           // frameLayout.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int desiredSize ;// Divide by 3 for 3x3 grid
           /* if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Landscape mode
                if (context.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                    // Tablet device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.9); // Adjust divisor for desired size
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    imageView.setScaleX(0.7F);
                    imageView.setScaleY(0.7F);
                } else if (context.getResources().getConfiguration().smallestScreenWidthDp >= 400) {
                    // Large smartphone device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.4); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                } else {
                    // Small smartphone device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.3); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                }
            } else {
                // Portrait mode
                if (context.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                    // Tablet device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 4.5); // Adjust divisor for desired size
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    imageView.setScaleX(0.7F);
                    imageView.setScaleY(0.7F);
                }
                else if (context.getResources().getConfiguration().smallestScreenWidthDp >= 400) {
                    // Large smartphone device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.0001); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                } else {
                    // Small smartphone device
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 2.9); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                }
            }

            */
            int screenSize = context.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;

            int densityDpi = context.getResources().getDisplayMetrics().densityDpi;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Landscape mode
                if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                    // xlarge screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.9); // Adjust divisor for desired size
                    imageView.setScaleX( 0.7F);
                    imageView.setScaleY( 0.7F);
                } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    // large screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.4); // Adjust divisor for desired size
                    imageView.setScaleX( 0.5F);
                    imageView.setScaleY( 0.5F);
                } else {
                    // small or normal screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.2); // Adjust divisor for desired size
                    imageView.setScaleX( 0.5F);
                    imageView.setScaleY( 0.5F);
                }
            } else {
                // Portrait mode
                if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                    // xlarge screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 4.5); // Adjust divisor for desired size
                    imageView.setScaleX( 0.7F);
                    imageView.setScaleY( 0.7F);
                } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    // large screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.0001); // Adjust divisor for desired size
                    imageView.setScaleX( 0.5F);
                    imageView.setScaleY( 0.5F);
                } else {
                    // small or normal screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 2.9); // Adjust divisor for desired size
                    imageView.setScaleX( 0.5F);
                    imageView.setScaleY( 0.5F);
                }
            }
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(desiredSize, desiredSize));


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
                imageView.setImageResource(R.drawable.o_image);
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
