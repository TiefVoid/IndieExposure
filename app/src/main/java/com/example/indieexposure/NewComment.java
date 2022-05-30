package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewComment extends AppCompatActivity {
    private Button bComentar;
    private EditText etDescComment;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private String user="",pfp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        //Button
        bComentar = findViewById(R.id.bComentar);
        //Edit Text
       etDescComment = findViewById(R.id.etDescComment);

        //Database config
        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference();

        bComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newComment();
            }
        });

        configVar();
    }

    private void configVar() {
        Intent intent = getIntent();
        if(intent!=null){
            user = intent.getStringExtra(MainActivity.CURR_USER);
            pfp = intent.getStringExtra(MainActivity.CURR_PFP);

        }
    }

    private void newComment() {
        String desc = etDescComment.getText().toString().trim();

        Comment ahh = new Comment();

        ahh.setDesc(desc);
        ahh.setFechaHora(System.currentTimeMillis());
        ahh.setUser(user);
        ahh.setPfp(pfp);

        myRef.child("comment").child(user+System.currentTimeMillis()).setValue(ahh);
        etDescComment.setText("");
        finish();
    }




}