package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FollowersActivity extends AppCompatActivity implements ProfileAdapter.OnProfileClickListener{
    public static final String CURR_KEY = "llave";
    public static final String USER_KEY = "clave";
    private RecyclerView rvFollowers;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private ProfileAdapter adapter;
    private String key="",logged_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        rvFollowers = findViewById(R.id.rvFollowers);

        adapter = new ProfileAdapter(this,this);
        rvFollowers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvFollowers.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");
        userRef = database.getReference("users");

        configUI();
    }

    private void configUI() {
        Intent intent = getIntent();
        logged_key = intent.getStringExtra(MyProfileActivity.CURR_KEY);
        key = "";
        myRef.child(logged_key).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                for (DataSnapshot one : snapshot.getChildren()) {
                    key = one.getKey();
                    findUser(key);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error1) {

            }
        });
    }

    private void findUser(String key) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                for (DataSnapshot two : snapshot2.getChildren()) {
                    User use = two.getValue(User.class);
                    String hold = two.getKey();
                    if (hold.equals(key)) {
                        use.setKey(hold);
                        adapter.add(use);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error2) {

            }
        });
    }


    @Override
    public void onClick(int position) {
        User use = adapter.getProfile(position);

        Intent intent = new Intent(FollowersActivity.this, ProfileFromFollowActivity.class);

        intent.putExtra(CURR_KEY,logged_key);
        intent.putExtra(USER_KEY,use.getKey());

        startActivity(intent);
    }
}