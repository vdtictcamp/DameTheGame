package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private Button btnRegister, btnLogin;
    private String email;
    private String password;
    private FirebaseAuth firebaseAuth;
    private EditText txtName;
    private EditText txtPassword;
    private ProgressBar loadBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegister = findViewById(R.id.btnToRegister);
        txtName = findViewById(R.id.txtLoginUsername);
        txtPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loadBar = findViewById(R.id.loadBarLogin);
        loadBar.setVisibility(loadBar.INVISIBLE);

        View.OnClickListener toRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToRegister();
            }
        };
        btnRegister.setOnClickListener(toRegister);

        View.OnClickListener loginListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtName.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    txtName.setText("Name darf nicht leer sein");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Bitte geben Sie Ihr Passwort ein", Toast.LENGTH_SHORT).show();
                }
                firebaseAuth = FirebaseAuth.getInstance();
                //Authenticate the user
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    loadBar.setVisibility(loadBar.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadBar.setVisibility(loadBar.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "erfolgreich eingeloggt", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                                loadBar.setVisibility(loadBar.INVISIBLE);
                            }
                        }
                    });
                }
            }
        };
        btnLogin.setOnClickListener(loginListner);
    }

    public void changeToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (firebaseAuth.getCurrentUser() != null) {
            menu.removeItem(R.id.menuLoginItem);
            menu.add(R.id.lblAccountMenu);
        }
        if (firebaseAuth.getCurrentUser() == null) {
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
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(LoginActivity.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();

                } else {
                    intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.menuLogoutItem:
                firebaseAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.lblAccountMenu:
                intent=new Intent(getApplicationContext(), AccountDelete.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
