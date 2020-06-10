package com.example.myapplication.PlayerController;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

//Diese Klasse schiebt das grüne Quadrat, um zu visualisieren, welcher Spieler am Zug ist
public class Controller {

    public void changeTurnOfPlayer(boolean playerOneTurn, boolean playerTwoTurn, View view1, View view2){
        if(playerOneTurn){
            view1.setVisibility(view1.VISIBLE);
        }else{
            view1.setVisibility(view1.INVISIBLE);
        }

       if(playerTwoTurn){
           view2.setVisibility(view2.VISIBLE);
       }else{
           view2.setVisibility(view2.INVISIBLE);
       }
    }

}
