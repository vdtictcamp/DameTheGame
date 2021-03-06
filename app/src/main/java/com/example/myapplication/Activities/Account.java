package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {

    private Button btnDeleteAccount;
    private FirebaseAuth currentUserAuth;
    private FirebaseUser firebasUser;
    private ProgressBar loadBar;
    private TextView lblEmailAccount;
    private String email;
    private Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        loadBar = findViewById(R.id.loadBarDeleteAccount);
        loadBar.setVisibility(loadBar.INVISIBLE);
        lblEmailAccount = findViewById(R.id.lblAccountEmail);
        btnResetPassword=findViewById(R.id.btnResetPassword);

        email=firebasUser.getEmail();
        lblEmailAccount.setText(email);
        currentUserAuth=FirebaseAuth.getInstance();
        firebasUser =currentUserAuth.getCurrentUser();

        //On Click the Process to reset the password starts
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBar.setVisibility(loadBar.VISIBLE);
                currentUserAuth.sendPasswordResetEmail(firebasUser.getEmail().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loadBar.setVisibility(loadBar.INVISIBLE);
                            Toast.makeText(Account.this, "Ein neues Passwort wurde dir auf deinen Email Account gesendet", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Account.this, "Dein Passwort konnte leider nicht zurückgesetzt werden", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

        //on Click the delete process of the User Account starts
        View.OnClickListener deletListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Account.this);
                dialog.setTitle("Bist du sicher?");
                dialog.setMessage("Alle deine Daten werden gelöscht und du kannst nicht mehr online spielen");
                dialog.setPositiveButton("Ja ich will mein Konto löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadBar.setVisibility(loadBar.VISIBLE);
                        firebasUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Account.this, "Dein Konto wurde erfolgreich gelöscht", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Account.this, "Etwas ist leider schief gegangen", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Konto nicht löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        };
        btnDeleteAccount.setOnClickListener(deletListener);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (currentUserAuth.getCurrentUser() != null) {
            menu.removeItem(R.id.menuLoginItem);
            menu.add(R.id.lblAccountMenu);
        }
        if (currentUserAuth.getCurrentUser() == null) {
            menu.add(R.id.menuLoginItem);
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
                    Toast.makeText(Account.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();

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
                intent = new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}