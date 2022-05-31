package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends AppCompatActivity {
    public static final String CURR_KEY = "key";
    public static final String CURR_PFP = "pfp";
    public static final String CURR_BIO = "bio";
    public static final String CURR_PSEUD = "pseud";
    public static final String CURR_USER = "user";
    private ImageView ivMyPfp;
    private TextView tvMyBio, tvMyPseud, tvMyUser;
    String pfp = "", bio = "", pseud = "", user = "", key = "";
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ivMyPfp = findViewById(R.id.ivMyPfp);
        tvMyBio = findViewById(R.id.tvMyBio);
        tvMyPseud = findViewById(R.id.tvMyPseud);
        tvMyUser = findViewById(R.id.tvMyUser);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");
        configUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.opcEditMyProf:
                Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);

                intent.putExtra(CURR_KEY,key);
                intent.putExtra(CURR_USER,user);
                intent.putExtra(CURR_PFP,pfp);
                intent.putExtra(CURR_BIO,bio);
                intent.putExtra(CURR_PSEUD,pseud);

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configUI() {
        Intent intent = getIntent();

        pfp = intent.getStringExtra(MainActivity.CURR_PFP);
        Picasso.get().load(pfp).into(ivMyPfp);

        user = intent.getStringExtra(MainActivity.CURR_USER);
        tvMyUser.setText(user);

        key = intent.getStringExtra(MainActivity.CURR_KEY);

        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("pseud")){
                    User temp = snapshot.getValue(User.class);
                    pseud = temp.getPseud();
                    tvMyBio.setText(pseud);
                }else{
                    tvMyPseud.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("bio")){
                    User temp = snapshot.getValue(User.class);
                    bio = temp.getBio();
                    tvMyBio.setText(bio);
                }else{
                    tvMyBio.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}