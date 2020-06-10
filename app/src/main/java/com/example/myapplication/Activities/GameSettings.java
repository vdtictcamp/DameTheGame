package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;

public class GameSettings extends AppCompatActivity {


    String gameName;
    EditText txtFieldGameName;
    private Button btnStartGame;
    FirebaseAuth currentUserAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        btnStartGame= findViewById(R.id.btnStartGame);
        currentUserAuth = FirebaseAuth.getInstance();
        View.OnClickListener startGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        };

        btnStartGame.setOnClickListener(startGameListener);
    }

    public void startGame(){
        gameName = "offline";
        Intent intent = new Intent(getApplicationContext(), localGame.class);
        intent.putExtra("gameName", gameName);
        startActivity(intent);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (currentUserAuth.getCurrentUser()!=null){
            menu.removeItem(R.id.menuLoginItem);
        }
        if(currentUserAuth.getCurrentUser()==null){
            menu.add(R.id.menuLoginItem);
            menu.removeItem(R.id.menuLogoutItem);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLoginItem:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulesMenuItem:
                intent = new Intent(getApplicationContext(), GameRulesActivity.class);
                startActivity(intent);
                return true;
            case R.id.registerMenuItem:
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuOnlineItem:
                intent = new Intent(getApplicationContext(), CreateOnlineGame.class);
                startActivity(intent);
                return true;
            case R.id.menuLogoutItem:
                currentUserAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

}
