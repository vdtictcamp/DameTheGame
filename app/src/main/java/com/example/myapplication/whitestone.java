package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class whitestone extends Stone implements View.OnTouchListener {

    List<Cell> availablePostions = new ArrayList<>();
    GameField gameField = new GameField();
    private int row;
    private int col;
    private Cell position;


    public whitestone(Context context, float posx, float posy, Paint paint, int col, int row, Cell position) {
        super(context, posx, posy, paint, col, row, position);
        this.col=col;
        this.row = row;
        this.position=position;
        this.setOnTouchListener(this);

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
    public void moveStone(int fromX, int toX, int fromY, int toY, View view) {

    }

    @Override
    public Cell getPosition() {
        return this.position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_BUTTON_PRESS) {
            availablePostions = gameField.getAvailablePositionsForWhiteStones(col, this.row);
            for (Cell pos : availablePostions) {
                pos.setBackgroundColor(R.color.green);
            }
        }
        return true;
    }
}
