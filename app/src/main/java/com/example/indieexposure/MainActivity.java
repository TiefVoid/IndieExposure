package com.example.indieexposure;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class MainActivity extends AppCompatActivity {
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StorageReference storageRef = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }
}