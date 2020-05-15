package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class AvailablePositions extends View {

    int posx, posy;
    public AvailablePositions(Context context) {
        super(context);
        this.posx = posx;
        this.posy = posy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAvailablePostions(canvas, posx, posy);
    }

    public void drawAvailablePostions(Canvas canvas, int posx, int posy){
        int diffX = canvas.getWidth()/8;
        int diffY = canvas.getHeight()/8;
        Paint p = new Paint(Color.GREEN);
        Rect rect = new Rect(posx+posx, posy+posy,posx+posx+diffX, posy+posy+diffY);
        canvas.drawRect(rect, p);
    }
}
