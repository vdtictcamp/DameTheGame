package com.example.myapplication.PlayerController;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

//Diese Klasse schiebt das grüne Quadrat, um zu visualisieren, welcher Spieler am Zug ist
public class Controller {

    public void changeTurnOfPlayer(View view1, View view2){
        if(view1.getVisibility()==view1.VISIBLE){
            view1.setVisibility(view1.INVISIBLE);
        }
        else{
            view1.setVisibility(view1.VISIBLE);
        }
        if(view2.getVisibility()==view2.VISIBLE) {
            view2.setVisibility(view1.INVISIBLE);
        }else{
            view2.setVisibility(view2.VISIBLE);
        }
    }

}
