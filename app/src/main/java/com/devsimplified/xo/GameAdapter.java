package com.devsimplified.xo;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.devsimplified.xo.Game;

public class GameAdapter extends BaseAdapter {

    private Context context;
    private  Game game;

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
        CellView imageView;
        LineView lineView=null;
        imageView = new CellView(context);
        imageView.setLayoutParams(
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        if (convertView == null) {
            frameLayout = new FrameLayout(context);
            // frameLayout.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int desiredSize;// Divide by 3 for 3x3 grid

            int screenSize = context.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;

            int densityDpi = context.getResources().getDisplayMetrics().densityDpi;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Landscape mode
                if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                    // xlarge screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.9); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);

                } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    // large screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                } else {
                    // small or normal screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.46); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                }
            } else {
                // Portrait mode
                if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                    // xlarge screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 4.5); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    // large screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.0001); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                } else {
                    // small or normal screen size
                    desiredSize = (int) (Math.min(screenWidth, screenHeight) / 3.2); // Adjust divisor for desired size
                    imageView.setScaleX(0.5F);
                    imageView.setScaleY(0.5F);
                }
            }
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(desiredSize, desiredSize));


            frameLayout.setBackgroundResource(R.drawable.cell_stroke);

            frameLayout.addView(imageView);
        } else {
            frameLayout = (FrameLayout) convertView;
            imageView = (CellView) frameLayout.getChildAt(0);
            lineView = (LineView) frameLayout.getChildAt(1);
        }

        char symbol = game.getSymbolAtPosition(position);

        if (symbol == 'X') {
            if (game.getSelectedOption() == 'X') {
                imageView.setBackgroundResource(R.drawable.x_image);

            } else {
                imageView.setBackgroundResource(R.drawable.x_image);
            }
        } else if (symbol == 'O') {

            if (game.getSelectedOption() == 'X') {
                imageView.setBackgroundResource(R.drawable.o_image);
            } else {
                imageView.setBackgroundResource(R.drawable.o_image);
            }
        } else {
            imageView.setBackgroundResource(0);
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

            if (isWinningCell) {
                imageView.setWinningCell(true);
                if (lineView == null) {
                    lineView = new LineView(context, game);
                    frameLayout.addView(lineView);
                }
            } else {
                imageView.setWinningCell(false);
            }
        } else {
            imageView.setWinningCell(false);
            if (lineView != null) {
                frameLayout.removeView(lineView);
            }
        }
        return frameLayout;
    }


    private static class CellView extends View {
        private char symbol;
        private boolean isWinningCell;
        private Paint winningLinePaint;

        public CellView(Context context) {
            super(context);
            symbol = '\0';
            isWinningCell = false;

            // Initialize the paint for the winning line
            winningLinePaint = new Paint();
            winningLinePaint.setColor(Color.RED);
            winningLinePaint.setStrokeWidth(10f);
        }

        public void setSymbol(char symbol) {
            this.symbol = symbol;
            invalidate(); // Redraw the view when the symbol changes
        }

        public void setWinningCell(boolean isWinningCell) {
            this.isWinningCell = isWinningCell;
            invalidate(); // Redraw the view when the winning cell status changes
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw the symbol (X or O)
            if (symbol == 'X') {
                drawXSymbol(canvas);
            } else if (symbol == 'O') {
                drawOSymbol(canvas);
            }

            // Draw the winning line if it's a winning cell

        }

        private void drawXSymbol(Canvas canvas) {
            // Draw X symbol logic here
        }

        private void drawOSymbol(Canvas canvas) {
            // Draw O symbol logic here
        }

        private void drawWinningLine(Canvas canvas) {
            int gridWidth = getWidth();
            int gridHeight = getHeight();
            int lineColor = getResources().getColor(android.R.color.holo_blue_light);
            int lineWidth = getResources().getDimensionPixelSize(R.dimen.winning_line_width);

            // Calculate the line position to be drawn within the grid
            int lineY = (int) (gridHeight * 0.5f);

            // Draw the winning line across the grid horizontally
            Paint paint = new Paint();
            paint.setColor(lineColor);
            paint.setStrokeWidth(lineWidth);
            canvas.drawLine(0, lineY, gridWidth, lineY, paint);
        }


    }


    public static class LineView extends View {

        private final Game game;
        private Paint linePaint;

        public LineView(Context context, Game game) {
            super(context);
        this.game=game;
            // Initialize the paint for the line
            linePaint = new Paint();
            linePaint.setColor(Color.RED);
            linePaint.setStrokeWidth(10f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

           /* // Calculate the line coordinates
            int startX = 0;
            int startY = getHeight() / 2;
            int endX = getWidth();
            int endY = getHeight() / 2;

            // Draw the line
            canvas.drawLine(startX, startY, endX, endY, linePaint);*/
            drawWinningLine(canvas);

        }


        private void drawWinningLine(Canvas canvas) {
            int cellWidth = getWidth();
            int cellHeight = getHeight();
            int lineColor = getResources().getColor(R.color.line_color);
            int lineWidth = getResources().getDimensionPixelSize(R.dimen.winning_line_width);

            int[] winningLine = game.getWinningLine();
            if (winningLine != null && winningLine.length == 3) {
                int startX = 0;
                int startY = 0;
                int endX = 0;
                int endY = 0;
                int startCell = winningLine[0];
                int endCell = winningLine[2];
                int startRow = startCell / 3;
                int startColumn = startCell % 3;
                int endRow = endCell / 3;
                int endColumn = endCell % 3;
                // Calculate the start and end points for the winning line based on its orientation
                if (startRow == endRow) {
                    // Horizontal line
                    startX = 0;
                    startY = cellHeight / 2;
                    endX = cellWidth+1;
                    endY = startY;
                } else if (startColumn == endColumn) {
                    // Vertical line
                    startX = cellWidth / 2;
                    startY = 0;
                    endX = startX;
                    endY = cellHeight+1;
                }  else if (startColumn > endColumn) {
                // Diagonal line (left to right)
                startX = 0;
                startY = cellHeight+1;
                endX = cellWidth+1;
                endY = 0;
            } else {
                // Diagonal line (right to left)
                startX = 0;
                startY = 0;
                endX = cellWidth;
                endY = cellHeight;
            }
                // Define the gradient colors
                int startColor = getResources().getColor(R.color.golden_start_color);
                int endColor = getResources().getColor(R.color.golden_end_color);

                // Create a linear gradient
                Shader shader = new LinearGradient(startX, startY, endX, endY, startColor, endColor, Shader.TileMode.CLAMP);

                // Create the paint object with gradient
                Paint paint = new Paint();
                paint.setStrokeWidth(lineWidth);
                paint.setShader(shader);
                canvas.drawLine(startX, startY, endX, endY, paint);
            }

        }


    }
}