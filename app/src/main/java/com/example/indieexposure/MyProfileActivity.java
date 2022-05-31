package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MyProfileActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener{
    public static final String CURR_KEY = "key";
    public static final String CURR_PFP = "pfp";
    public static final String CURR_BIO = "bio";
    public static final String CURR_PSEUD = "pseud";
    public static final String CURR_USER = "user";
    public static final String POST_USER = "User";
    public static final String POST_AUDIO = "Audio";
    public static final String POST_DESC = "Desc";
    public static final String POST_DATE = "Date";
    public static final String POST_IMG = "Img";
    public static final String POST_PFP = "Pfp";
    public static final String POST_USER_KEY = "user_key";
    private ImageView ivMyPfp, ivMyFollowers;
    private RecyclerView rvMyPosts;
    private TextView tvMyBio, tvMyPseud, tvMyUser;
    private String pfp = "", bio = "", pseud = "", user = "", key = "";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference postRef;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ivMyPfp = findViewById(R.id.ivMyPfp);
        tvMyBio = findViewById(R.id.tvMyBio);
        tvMyPseud = findViewById(R.id.tvMyPseud);
        tvMyUser = findViewById(R.id.tvMyUser);
        ivMyFollowers = findViewById(R.id.ivMyFollowers);
        rvMyPosts = findViewById(R.id.rvMyPosts);

        adapter = new PostAdapter(this,this);
        rvMyPosts.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,true);
        rvMyPosts.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");
        postRef = database.getReference("posts");

        ivMyFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, FollowersActivity.class);

                intent.putExtra(CURR_KEY,key);

                startActivity(intent);
            }
        });

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                try {
                    for(DataSnapshot one : snapshot.getChildren()){
                        Post p = one.getValue(Post.class);
                        if(p.getUser_key().equals(key)){
                            adapter.add(p);
                        }
                    }
                }catch (Error error){
                    Log.i("TiefVoid", error.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                rvMyPosts.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });

        configUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        key = intent.getStringExtra(MainActivity.CURR_KEY);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot one : snapshot.getChildren()){
                    User use = one.getValue(User.class);
                    if(one.getKey().equals(key)){
                        pfp = use.getProfile_picture();
                        Picasso.get().load( pfp ).into( ivMyPfp );
                        user = use.getUser();
                        tvMyUser.setText(user);
                        pseud = use.getPseud();
                        tvMyPseud.setText(pseud);
                        bio = use.getBio();
                        tvMyBio.setText(bio);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(int position) {
        Post post = adapter.getPost(position);

        Intent intent = new Intent(MyProfileActivity.this, PostActivity.class);

        intent.putExtra(CURR_USER,user);
        intent.putExtra(CURR_PFP,pfp);
        intent.putExtra(CURR_KEY,key);
        intent.putExtra(POST_USER,post.getUser());
        intent.putExtra(POST_AUDIO,post.getAudio());
        intent.putExtra(POST_DESC,post.getDesc());
        intent.putExtra(POST_DATE,post.getFechaHora());
        intent.putExtra(POST_IMG,post.getImg());
        intent.putExtra(POST_PFP,post.getPfp());
        intent.putExtra(POST_USER_KEY,post.getUser_key());

        startActivity(intent);
    }
}