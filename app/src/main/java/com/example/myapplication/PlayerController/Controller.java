package com.example.myapplication.PlayerController;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

//Diese Klasse schiebt das grüne Quadrat, um zu visualisieren, welcher Spieler am Zug ist
public class Controller {

    public void changeTurnOfPlayer(View view1, View view2){
            view1.setBackgroundColor(Color.WHITE);
            view2.setBackgroundColor(Color.GREEN);
    }

}
