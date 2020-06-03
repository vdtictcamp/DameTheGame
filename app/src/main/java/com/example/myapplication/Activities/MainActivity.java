package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnInitNewGame;
    private Button btnToLogin;
    private Button btnLogout;
    private Button btnRegister;
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
        btnRegister=findViewById(R.id.btnRegister);
        btnToSearchGame=findViewById(R.id.btnToSearchGame);

        currentUserAuth = FirebaseAuth.getInstance();

        if (currentUserAuth.getCurrentUser()!=null){
            btnCreateGame.setVisibility(btnCreateGame.VISIBLE);
        }

        btnRegister.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(RegisterActivity);
            }
        }));

        btnInitNewGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameField.class);
            startActivity(intent);
        }
        }));

        btnToLogin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        }));

        btnLogout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                btnCreateGame.setVisibility(btnCreateGame.INVISIBLE);
            }
        }));

        btnCreateGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameSettings.class);
                startActivity(intent);
            }
        }));

        btnToSearchGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchGameActivity.class);
                startActivity(intent);
            }
        }));
    }
}
