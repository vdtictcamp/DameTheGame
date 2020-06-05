package com.example.myapplication.Activities;

public class Transaction {
    public int colPos;
    public int rowPos;
    public int colStone;
    public int rowStone;

    public Transaction(int cP, int rP, int cS, int rS){
        this.colPos = cP;
        this.rowPos = rP;
        this.colStone = cS;
        this.rowStone = rS;
    }

}
