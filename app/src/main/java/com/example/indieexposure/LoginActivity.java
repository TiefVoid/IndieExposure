package com.example.indieexposure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etpassword;
    private TextView tvRegistro;
    private Button bLogin, bregistro;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        mAuth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.etUsername);
        etpassword = findViewById(R.id.etpassword);

        bLogin = findViewById(R.id.bLogin);
        bregistro = findViewById(R.id.bregistro);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        bregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

    }

    private void login() {
        String email = etUsername.getText().toString().trim();
        String pass = etpassword.getText().toString().trim();


        if(email.isEmpty()){
            etUsername.setError("Email can not be empty");
        }
        if(pass.isEmpty()){
            etpassword.setError("Password can not be empty");
        }
        else{
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Login Succesful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }


}
