package com.example.indieexposure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGGED_USER = "user";
    private static final String APP_SHARED_PREFS = "preferences";
    public static final String LOGGED_PFP = "pfp";
    public static final String LOGGED_MAIL = "mail";
    private EditText etUsername, etpassword;
    private TextView tvRegistro;
    private Button bLogin, bregistro;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        mAuth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.etUsername);
        etpassword = findViewById(R.id.etpassword);

        bLogin = findViewById(R.id.bLogin);
        bregistro = findViewById(R.id.bregistro);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
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
                        Intent intent = getIntent();

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String mail = currentUser.getEmail();
                        intent.putExtra(LOGGED_MAIL,mail);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }


}
