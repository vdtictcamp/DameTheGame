package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class darkstone extends Stone implements View.OnTouchListener, View.OnClickListener {

    List<Cell> availablePostions = new ArrayList<>();
    GameField gameField = new GameField();
    private int row;
    private int col;
    private Cell position;
    private float posx;
    private float posy;
    private Paint paint;


    public darkstone(Context context, float posx, float posy, Paint paint, int col, int row) {
        super(context, posx, posy, paint, col, row);
        this.col = col;
        this.row=row;
        this.position=position;
        this.posx = posx;
        this.posy = posy;
        this.paint = paint;
        this.setOnTouchListener(this);
    }

    @Override
    public Stone getStone() {
        return null;
    }

    @Override
    public int getRow() {
        return 0;
    }

    @Override
    public int getCol() {
        return 0;
    }

    @Override
    public void setCol(int col) {

    }

    @Override
    public void setRow(int row) {

    }

    @Override
    public void moveStone(int toX, int toY, View view) {

    }


    @Override
    public Cell getPosition() {
        return this.position;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        availablePostions =gameField.getAvailablePositionsForDarkStones(this.col, this.row);
        System.out.println(availablePostions.size()+"from darkstone");

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("dunkler stein hinzugefügt");
        canvas.drawCircle(posx, posy, 20, paint);
    }

    @Override
    public void onClick(View v) {
        System.out.println("AUs darkstone");
    }
}
