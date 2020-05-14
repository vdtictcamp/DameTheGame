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

public class whitestone extends Stone implements View.OnTouchListener, View.OnClickListener {

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
        view.setOnClickListener(this);
        view.setOnTouchListener(this);
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
        gameField.drawNewStone(posx, posy, (Stone) view, col, row, position);
    }

    @Override
    public Cell getPosition() {
        return this.position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
       if(event.getAction()==MotionEvent.ACTION_MOVE){
           System.out.println("Aus touched");
               v.setX(event.getX());
               v.setY(event.getY());
           try {
               Thread.sleep(50);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           view.getDrawingRect(new Rect());
       }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.getWidth();
        System.out.println(canvas.getHeight());
        System.out.println("Weiser stein hinzugefügt");
        canvas.drawCircle(posx, posy, 20, paint);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        FrameLayout l = findViewById(R.id.gamelayout);
        System.out.println("clicked");
        availablePostions = gameField.getAvailablePositionsForWhiteStones(this.col, this.row);
        System.out.println("Spalte:"+this.col+ "Reihe:"+this.row);
        System.out.println(availablePostions.size());
        this.animate()
                .y(-10)
                .setDuration(5)
                .start();




    }




}
