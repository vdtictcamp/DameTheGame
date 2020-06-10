package com.example.myapplication.PlayerController;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

//Diese Klasse schiebt das grüne Quadrat, um zu visualisieren, welcher Spieler am Zug ist
public class Controller {

    public void changeTurnOfPlayer(boolean playerOneTurn, boolean playerTwoTurn, View view2, View view1){
        if(playerOneTurn){
            view2.setVisibility(view1.VISIBLE);
            view1.setVisibility(view2.INVISIBLE);
        }
       if(playerTwoTurn){
           view1.setVisibility(view2.VISIBLE);
           view2.setVisibility(view1.INVISIBLE);
       }


    }

}
