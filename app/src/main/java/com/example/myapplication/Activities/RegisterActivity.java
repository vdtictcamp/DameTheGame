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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private Button btnRegister;
    private EditText txtUserName;
    private EditText txtPassword;
    private EditText txtPasswordRepeat;
    private ProgressBar loadBar;
    String name;
    String password;
    String password_repeat;
    String password_hash;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister);
        txtUserName = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtPasswordRepeat = findViewById(R.id.txtPasswordRepeat);
        loadBar = findViewById(R.id.loadBarRegister);
        loadBar.setVisibility(loadBar.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        View.OnClickListener createAccountListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtUserName.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                password_repeat = txtPasswordRepeat.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "Email muss vorhanden sein", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Bitte legen Sie ein Passwort fest", Toast.LENGTH_LONG).show();
                }
                if (password.length() < 5) {
                    Toast.makeText(RegisterActivity.this, "Passwörter müssen übereinstimmen", Toast.LENGTH_LONG).show();
                }
                if (!password_repeat.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "Passwörter müssen übereinstimmen", Toast.LENGTH_LONG).show();
                }

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(password_repeat)) {
                    loadBar.setVisibility(loadBar.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadBar.setVisibility(loadBar.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, "Account erfolgreich erstellt", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error" + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        };

        btnRegister.setOnClickListener(createAccountListener);

    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (firebaseAuth.getCurrentUser() != null) {
            menu.removeItem(R.id.menuLoginItem);
        }
        if (firebaseAuth.getCurrentUser() == null) {
            menu.add(R.id.menuLoginItem);
            menu.removeItem(R.id.menuLogoutItem);
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
                intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuLogoutItem:
                firebaseAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
