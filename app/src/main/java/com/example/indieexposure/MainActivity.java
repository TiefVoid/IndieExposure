package com.example.indieexposure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class MainActivity extends AppCompatActivity {
    private FirebaseStorage storage;
    private FloatingActionButton fabNewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabNewPost = findViewById(R.id.fabNewPost);

        fabNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        StorageReference storageRef = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }
}