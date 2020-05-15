package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class whitestone extends Stone implements View.OnTouchListener{

    List<Cell> availablePostions = new ArrayList<>();
    GameField gameField = new GameField();
    private int row;
    private int col;
    private Cell position;
    private float posx;
    private float posy;
    private Paint paint;
    View view;
    Context context;


    public whitestone(Context context, float posx, float posy, Paint paint, int col, int row) {
        super(context, posx, posy, paint, col, row);
        this.col = col;
        this.row=row;
        this.position=position;
        this.context = context;
        this.posx = posx;
        this.posy = posy;
        this.paint = paint;
        view=this;
        view.setOnTouchListener(this);
        this.layout=layout;
    }

    @Override
    public Stone getStone() {
        return null;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    @Override
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public void setRow(int row) {
        this.row=row;
    }


    public void moveStone(int toX, int toY, View view) {
        this.col =toX;
        this.row = toY;
        FrameLayout layout = findViewById(R.id.gamelayout);
        layout.removeView(view);
    }

    @Override
    public Cell getPosition() {
        return this.position;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("breite von view:"+ view.getHeight());
        canvas.drawCircle(posx, posy, 20, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.getLayoutParams().width=20;
        v.getLayoutParams().height=20;
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            v.setX(event.getX());
            v.setY((float) (event.getY()));
        }

        return true;
    }
}
