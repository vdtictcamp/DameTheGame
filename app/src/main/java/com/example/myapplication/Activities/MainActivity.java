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

import com.example.myapplication.Firebase.FirebaseGameController;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnInitNewGame;
    private Button btnToLogin;

    private Button btnRegister;
    private Button btnOnlineSpielen;
    private Button btnToSearchGame;
    FirebaseAuth currentUserAuth;
    private Button btnCreateGame;

    //firebase Testelements
    private FirebaseGameController firebase;
    private String gameName;
    private String player;
    static int[][] stones;
    private Button btnTestransaction;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInitNewGame=findViewById(R.id.btnInitnewGame);
        btnRegister=findViewById(R.id.btnRegister);
        btnOnlineSpielen=findViewById(R.id.btnOnlineSpiel);
        currentUserAuth = FirebaseAuth.getInstance();
        btnTestransaction = findViewById(R.id.btnTestTransaction);

        gameName ="Test";
        player="testPlayer";
        stones = new int[][]{{1},{2}};

        btnInitNewGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), GameSettings.class);
            intent.putExtra("gameName", "Default");
            startActivity(intent);
        }
        }));

        btnRegister.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                //intent.putExtra("gameName", "Default");
                startActivity(intent);
            }
        }));

        btnOnlineSpielen.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                //intent.putExtra("gameName", "Default");
                startActivity(intent);
            }
        }));

        btnTestransaction.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //firebase = new FirebaseGameController(stones, gameName);
                //firebase.initStartSituation(player);
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("test").child("Testevent");
                //reference.setValue("lol");
                Map<String, Transaction> transactions = new HashMap<>();
                transactions.put("alanisawesome", new Transaction("1", "2", "3", "4"));
                reference.setValue(transactions);
            }
        }));

        /**
        reference = database.getReference("test").child("Testevent");
        DatabaseReference usersRef = reference.child("Testevent");

        Map<String, Transaction> transactions = new HashMap<>();
        transactions.put("alanisawesome", new Transaction("1", "2", "3", "4"));

        usersRef.setValueAsync(transaction);
         **/


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
                intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
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
