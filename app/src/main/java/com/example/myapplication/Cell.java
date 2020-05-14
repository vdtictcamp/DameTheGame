package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class Cell extends View implements View.OnTouchListener{

    Rect rect;
    Paint paint;
    int row;
    int col;
    float posx;
    float posy;
    int left, top, right, bottom;

    public Cell(Context context, int left, int top, int right, int bottom, float posx, float posy, int col, int row, Paint p) {
        super(context);
        this.posx=posx;
        this.posy=posy;
        this.row=row;
        this.col=col;
        this.paint=p;
        this.left = left;
        this.right=right;
        this.top=top;
        this.bottom=bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect = new Rect(left, top, right, bottom);
        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
    public int getCol(){
        return this.col;
    }
    public int getRow(){
        return this.row;
    }

    public void getPosition(int col, int row){

    }
}
