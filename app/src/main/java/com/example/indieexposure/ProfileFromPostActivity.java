package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFromPostActivity extends AppCompatActivity {
    public static final String CURR_KEY = "key";
    public static final String CURR_PFP = "pfp";
    public static final String CURR_BIO = "bio";
    public static final String CURR_PSEUD = "pseud";
    public static final String CURR_USER = "user";
    private ImageView ivTheirPfp, ivFollow;
    private TextView tvTheirBio, tvTheirPseud, tvTheirUser;
    private String pfp = "", bio = "", pseud = "", user = "", key = "", logged_key = "";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_from_post);

        ivTheirPfp = findViewById(R.id.ivTheirPfp);
        tvTheirBio = findViewById(R.id.tvTheirBio);
        tvTheirPseud = findViewById(R.id.tvTheirPseud);
        tvTheirUser = findViewById(R.id.tvTheirUser);
        ivFollow = findViewById(R.id.ivFollow);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference();
        userRef = database.getReference("users");

        ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followUser();
            }
        });

        configUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void followUser() {
        myRef.child("users").child(logged_key).child("following").push().setValue(key);
        myRef.child("users").child(key).child("followers").push().setValue(logged_key);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configUI();
    }

    private void configUI() {
        Intent intent = getIntent();
        logged_key = intent.getStringExtra(PostActivity.CURR_KEY);
        key = intent.getStringExtra(PostActivity.POST_USER_KEY);

        if(logged_key.equals(key)){
            ivFollow.setVisibility(View.GONE);
        }

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot one : snapshot.getChildren()){
                    User use = one.getValue(User.class);
                    if(one.getKey().equals(key)){
                        pfp = use.getProfile_picture();
                        Picasso.get().load( pfp ).into( ivTheirPfp );
                        user = use.getUser();
                        tvTheirUser.setText(user);
                        pseud = use.getPseud();
                        tvTheirPseud.setText(pseud);
                        bio = use.getBio();
                        tvTheirBio.setText(bio);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}