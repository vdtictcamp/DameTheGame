package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class Stone extends View{

    private Paint paint;
    private int row;
    private int col;
    private float posx;
    private float posy;
    private Cell position;

    public Stone(Context context, float posx, float posy, Paint paint, int col, int row, Cell position) {
        super(context);
        this.paint = new Paint();
        this.row = row;
        this.col = col;
        this.paint = paint;
        this.posx = posx;
        this.posy = posy;
        this.col = col;
        this.row = row;
        this.position = position;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(posx, posy, 20, paint);
    }

    public abstract Stone getStone();
    public abstract int getRow();
    public abstract int getCol();
    public abstract void moveStone(int fromX, int toX, int fromY, int toY, View view);
    public abstract Cell getPosition();


}
