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
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnInitNewGame;
    private Button btnRegister;
    private Button btnOnlineSpielen;
    private FirebaseAuth currentUserAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInitNewGame = findViewById(R.id.btnInitnewGame);
        btnRegister = findViewById(R.id.btnToRegister);
        btnOnlineSpielen = findViewById(R.id.btnOnlineSpiel);
        currentUserAuth = FirebaseAuth.getInstance();



        //On click we change to Game Setting Activity
        btnInitNewGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OfflineGameSettings.class);
                intent.putExtra("gameName", "Default");
                startActivity(intent);
            }
        }));

        //On click we change to Register Activity
        btnRegister.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                if(currentUserAuth.getCurrentUser()!=null){
                    Toast.makeText(MainActivity.this, "Du bist bereits eingeloggt", Toast.LENGTH_LONG).show();
                }
                startActivity(intent);
            }
        }));

        //If the User is loged in on click the app displays the online game options activity
        btnOnlineSpielen.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUserAuth.getCurrentUser() == null) {
                    Toast.makeText(MainActivity.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                    startActivity(intent);
                }
            }
        }));
    }

    //Creates the Menu
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (currentUserAuth.getCurrentUser() != null) {
            menu.removeItem(R.id.menuLoginItem);
        }
        if (currentUserAuth.getCurrentUser() == null) {
            menu.removeItem(R.id.menuLogoutItem);
            menu.removeItem(R.id.lblAccountMenu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    //All Items of the menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
                if (currentUserAuth.getCurrentUser() == null) {
                    Toast.makeText(MainActivity.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();

                } else {
                    intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.menuLogoutItem:
                currentUserAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.lblAccountMenu:
                intent=new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
