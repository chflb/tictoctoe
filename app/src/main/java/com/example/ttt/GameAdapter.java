package com.example.ttt;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
          /* int cellWidth=(int) context.getResources().getDimension(R.dimen.cell_width);
            int cellHeight=(int) context.getResources().getDimension(R.dimen.cell_height);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(cellWidth, cellHeight));*/
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setBackgroundResource(R.drawable.cell_stroke);
        } else {
            imageView = (ImageView) convertView;
        }

        char symbol = game.getSymbolAtPosition(position);
        if (symbol == 'X') {
            imageView.setImageResource(R.drawable.x_image);
        } else if (symbol == 'O') {
            imageView.setImageResource(R.drawable.o_image);
        } else {
            imageView.setImageDrawable(null);
        }

        return imageView;
    }
}
