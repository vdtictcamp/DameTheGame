package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Stone extends View implements View.OnTouchListener{



    private Paint paint;
    private float row;
    private float col;
    private float posx;
    private float posy;
    private Stone stone;
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
        this.setOnTouchListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(posx, posy, 20, paint);
    }

    public Stone getStone() {
        return this;
    }
    public Cell getPositionOfStone(){
        return this.position;
    }


    //Lässt die Steine bewegen
    public void moveStone(int fromX, int toX, int fromY, int toY, View view) {


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }
}
