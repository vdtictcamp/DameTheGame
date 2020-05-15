package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.GridLayout;

public class GameField extends Activity implements View.OnClickListener{

    GridLayout gameLayout;
    Stone stone;
    Paint p;
    int[][]positions;
    int[][]whitestones;
    int[][]blackstones;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        gameLayout = findViewById(R.id.gamelayout);

        positions= new int[][]{{R.id.pos1a,R.id.pos1b, R.id.pos1c, R.id.pos1d, R.id.pos1e, R.id.pos1f, R.id.pos1e, R.id.pos1h},
                {R.id.pos2a, R.id.pos2b, R.id.pos2c, R.id.pos2d, R.id.pos2e, R.id.pos2f, R.id.pos2g, R.id.pos2h},
                {R.id.pos3a, R.id.pos3b, R.id.pos3c, R.id.pos3d, R.id.pos3e, R.id.pos3f, R.id.pos3g, R.id.pos3h},
                 {R.id.pos4a, R.id.pos4b, R.id.pos4c, R.id.pos4d, R.id.pos4e, R.id.pos4f, R.id.pos4g, R.id.pos4h}};

View v = findViewById(R.id.pos1a);
v.setBackgroundColor(R.color.green);

        whitestones = new int[][]{{R.id.w1, R.id.w2, R.id.w3, R.id.w4},{R.id.w4, R.id.w5, R.id.w6, R.id.w7, R.id.w8}, {R.id.w9, R.id.w10, R.id.w11, R.id.w12}};
        //blackstones = new int[][]{{}, {R.id.}, {}, {}}


    }


//------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(View v) {

    }
}

