package com.devsimplified.xo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class WinningLineDrawable extends Drawable {
    private final Paint paint;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;

    public WinningLineDrawable(int startX, int startY, int endX, int endY) {
        paint = new Paint();
        paint.setColor(Color.RED); // Set the color of the line
        paint.setStrokeWidth(5); // Set the width of the line
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        // Implement if needed
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // Implement if needed
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
}
