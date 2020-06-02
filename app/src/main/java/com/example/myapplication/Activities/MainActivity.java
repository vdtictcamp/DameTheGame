package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Firebase.Firebase;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnInitNewGame;
    private Button btnToLogin;
    private Button btnLogout;
    private Button btnToSearchGame;
    FirebaseAuth currentUserAuth;
    private Button btnCreateGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInitNewGame=findViewById(R.id.btnInitnewGame);
        btnToLogin=findViewById(R.id.btnToLogin);
        btnCreateGame=findViewById(R.id.btnCreateNewGame);
        btnCreateGame.setVisibility(btnCreateGame.INVISIBLE);
        btnLogout=findViewById(R.id.btnLogout);
        btnToSearchGame=findViewById(R.id.btnToSearchGame);

        currentUserAuth = FirebaseAuth.getInstance();

        if (currentUserAuth.getCurrentUser()!=null){
            btnCreateGame.setVisibility(btnCreateGame.VISIBLE);
        }


        View.OnClickListener toGameField = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToGamefield();
            }
        };

        btnInitNewGame.setOnClickListener(toGameField);

    View.OnClickListener toFire = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeToLogin();

        }
    };
        btnToLogin.setOnClickListener(toFire);

        View.OnClickListener logoutListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        };

        btnLogout.setOnClickListener(logoutListener);

        View.OnClickListener createGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToGameSettings();
            }
        };
        btnCreateGame.setOnClickListener(createGameListener);

        View.OnClickListener toSearchGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSearchGame();
            }
        };

        btnToSearchGame.setOnClickListener(toSearchGameListener);

    }

public void logout(){
        FirebaseAuth.getInstance().signOut();
        btnCreateGame.setVisibility(btnCreateGame.INVISIBLE);
}

    public void changeToGamefield(){
        Intent intent = new Intent(this, GameField.class);
        startActivity(intent);
    }

    public void changeToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void changeToGameSettings(){
        Intent intent = new Intent(this, GameSettings.class);
        startActivity(intent);
    }


    public void changeToSearchGame(){
        Intent intent = new Intent(this, SearchGameActivity.class);
        startActivity(intent);
    }

}
