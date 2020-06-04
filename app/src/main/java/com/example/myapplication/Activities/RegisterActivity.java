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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private Button btnRegister;
    private EditText txtUserName;
    private EditText txtPassword;
    private EditText txtPasswordRepeat;
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
        btnRegister=findViewById(R.id.btnRegister);
        txtUserName = findViewById(R.id.txtUsername);
        txtPassword=findViewById(R.id.txtPassword);
        txtPasswordRepeat=findViewById(R.id.txtPasswordRepeat);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        View.OnClickListener createAccountListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =txtUserName.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                password_repeat = txtPasswordRepeat.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    txtUserName.setText("Name darf nicht leer sein");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    txtPassword.setText("Bitee legen Sie ein Passwirt fest");
                }
                if(password.length()<5){
                    txtPassword.setText("Das Passwort muss mindestens 8 Zeichen enthalten");
                }
                if(!password_repeat.equals(password)){
                    Toast.makeText(RegisterActivity.this, "Passwörter müssen übereinstimmen", Toast.LENGTH_LONG).show();
                }
                    firebaseAuth.createUserWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Account erflgreich erstellt", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else{
                                Toast.makeText(RegisterActivity.this, "Error"+task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        };

        btnRegister.setOnClickListener(createAccountListener);

    }
}
