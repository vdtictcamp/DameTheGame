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

    public int getColPos() {
        return colPos;
    }

    public int getColStone() {
        return colStone;
    }

    public int getRowPos() {
        return rowPos;
    }

    public int getRowStone() {
        return rowStone;
    }

    public void setColPos(int colPos) {
        this.colPos = colPos;
    }

    public void setColStone(int colStone) {
        this.colStone = colStone;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public void setRowStone(int rowStone) {
        this.rowStone = rowStone;
    }
}
