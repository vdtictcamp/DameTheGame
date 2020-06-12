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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class OfflineGameSettings extends AppCompatActivity {


    private String gameName;
    private Button btnStartGame;
    private FirebaseAuth currentUserAuth;
    private EditText editTxtPlayerOneName;
    private EditText editTxtPlayerTwoName;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_game_settings);
        btnStartGame = findViewById(R.id.btnStartGame);
        currentUserAuth = FirebaseAuth.getInstance();
        editTxtPlayerOneName = findViewById(R.id.editTxtPlayerOneName);
        editTxtPlayerTwoName = findViewById(R.id.editTxtPlayerTwoName);
        checkBox=findViewById(R.id.checkBoxTimer);

        View.OnClickListener startGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        };

        btnStartGame.setOnClickListener(startGameListener);
    }

    public void startGame() {
        Intent intent = new Intent(getApplicationContext(), localGame.class);
        if(checkBox.isChecked()){
            intent.putExtra("Timer", true);
        }
        else{
            intent.putExtra("Timer", false);
        }
        gameName = "offline";
        intent.putExtra("gameName", gameName);
        intent.putExtra("playerOneName", editTxtPlayerOneName.getText().toString());
        intent.putExtra("playerTwoName", editTxtPlayerTwoName.getText().toString());
        startActivity(intent);
    }

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
                    Toast.makeText(OfflineGameSettings.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();

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
