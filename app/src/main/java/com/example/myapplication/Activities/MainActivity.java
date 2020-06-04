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
        btnRegister=findViewById(R.id.btnRegister);
        currentUserAuth = FirebaseAuth.getInstance();


        btnInitNewGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameField.class);
            intent.putExtra("gameName", "Default");
            startActivity(intent);
        }
        }));


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
