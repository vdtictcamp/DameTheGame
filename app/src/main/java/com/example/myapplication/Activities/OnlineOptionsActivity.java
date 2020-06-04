package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class OnlineOptionsActivity extends AppCompatActivity {


    private Button btnSearchOnlineGame;
    private Button btnCreateOnlineGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_options);
        btnCreateOnlineGame=findViewById(R.id.btnCreateOnlineGame);
        btnSearchOnlineGame = findViewById(R.id.btnSearchOnlineGame);


        View.OnClickListener createOnlineGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateOnlineGame.class);
                startActivity(intent);
            }
        };
        btnCreateOnlineGame.setOnClickListener(createOnlineGameListener);

        View.OnClickListener searchGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchGameActivity.class);
                startActivity(intent);
            }
        };

        btnSearchOnlineGame.setOnClickListener(searchGameListener);
    }
}