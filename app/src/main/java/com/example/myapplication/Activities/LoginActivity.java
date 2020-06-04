package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


private Button btnRegister, btnLogin;
String email;
String password;
FirebaseAuth firebaseAuth;
EditText txtName;
EditText txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegister=findViewById(R.id.btnToRegister);
        txtName = findViewById(R.id.txtLoginUsername);
        txtPassword=findViewById(R.id.txtLoginPassword);
        btnLogin=findViewById(R.id.btnLogin);


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
                email=txtName.getText().toString().trim();
                password=txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    txtName.setText("Name darf nicht leer sein");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Bitte geben Sie Ihr Passwort ein", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth = FirebaseAuth.getInstance();

                //Authenticate the user
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "erfolgreich eingeloggt", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {
                                Toast.makeText(LoginActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }

            }
        };


            btnLogin.setOnClickListener(loginListner);
    }

    public void changeToRegister(){
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
