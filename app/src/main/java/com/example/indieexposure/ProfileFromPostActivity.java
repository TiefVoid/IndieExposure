package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFromPostActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener{
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
    private ImageView ivTheirPfp, ivFollow;
    private TextView tvTheirBio, tvTheirPseud, tvTheirUser;
    private RecyclerView rvTheirPosts;
    private String pfp = "", bio = "", pseud = "", user = "", key = "", logged_key = "";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private DatabaseReference postRef;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_from_post);

        ivTheirPfp = findViewById(R.id.ivTheirPfp);
        tvTheirBio = findViewById(R.id.tvTheirBio);
        tvTheirPseud = findViewById(R.id.tvTheirPseud);
        tvTheirUser = findViewById(R.id.tvTheirUser);
        ivFollow = findViewById(R.id.ivFollow);
        rvTheirPosts = findViewById(R.id.rvTheirPosts);

        adapter = new PostAdapter(this,this);
        rvTheirPosts.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,true);
        rvTheirPosts.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference();
        userRef = database.getReference("users");
        postRef = database.getReference("posts");

        ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followUser();
            }
        });

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                try {
                    for(DataSnapshot one : snapshot.getChildren()){
                        Post p = one.getValue(Post.class);
                        if(p != null){
                            String x = p.getUser_key();
                            if(x!=null){
                                if(p.getUser_key().equals(key)){
                                    adapter.add(p);
                                }
                            }
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

                rvTheirPosts.smoothScrollToPosition(adapter.getItemCount() - 1);
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
        myRef.child("users").child(logged_key).child("following").child(String.valueOf(key)).setValue(key);
        myRef.child("users").child(key).child("followers").child(String.valueOf(logged_key)).setValue(logged_key);
        recreate();
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

        userRef.child(logged_key).child("following").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    ivFollow.setVisibility(View.GONE);
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

        Intent intent = new Intent(ProfileFromPostActivity.this, PostActivity.class);

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