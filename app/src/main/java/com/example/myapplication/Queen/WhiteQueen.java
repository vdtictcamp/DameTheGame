package com.example.myapplication.Queen;

import android.view.View;

import com.example.myapplication.R;

public class WhiteQueen {

    View queen;
    private int row;
    private int col;

    public WhiteQueen(View queen, int row, int col){
        this.row=row;
        this.col=col;
        this.queen=queen;
    }

    public View getQueen(){
        return this.queen;

    }
    public void setQueen(View queen){
       this.queen=queen;
        queen.setBackgroundColor(R.drawable.queen);

    }

}
