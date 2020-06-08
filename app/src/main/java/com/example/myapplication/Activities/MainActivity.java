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
    private Button btnCreateGame;

    private Button btnTestransaction;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth currentUserAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInitNewGame=findViewById(R.id.btnInitnewGame);
        btnRegister=findViewById(R.id.btnRegister);
        btnOnlineSpielen=findViewById(R.id.btnOnlineSpiel);
        currentUserAuth = FirebaseAuth.getInstance();
        btnTestransaction = findViewById(R.id.btnTestTransaction);


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
                startActivity(intent);
            }
        }));

        btnOnlineSpielen.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUserAuth.getCurrentUser()==null){
                    Toast.makeText(MainActivity.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                    startActivity(intent);
                }
            }
        }));

       /* btnTestransaction.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //firebase = new FirebaseGameController(stones, gameName);
                //firebase.initStartSituation(player);
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("test").child("Testevent");
                //reference.setValue("lol");
                Map<String, Transaction> transactions = new HashMap<>();
                transactions.put("yeahFirebase", new Transaction("1", "2", "3", "4"));
                reference.setValue(transactions);
            }
        }));
        */
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
                if(currentUserAuth.getCurrentUser()==null){
                    Toast.makeText(MainActivity.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();

                }else {
                    intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                    startActivity(intent);
                }
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
