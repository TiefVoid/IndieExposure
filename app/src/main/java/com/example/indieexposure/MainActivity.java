package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener{
    public static final String POST_USER = "User";
    public static final String POST_AUDIO = "Audio";
    public static final String POST_DESC = "Desc";
    public static final String POST_DATE = "Date";
    public static final String POST_IMG = "Img";
    public static final String POST_PFP = "Pfp";
    public static final String CURR_USER = "Me";
    public static final String CURR_PFP = "welp";
    public static final String CURR_KEY = "key";
    private static final int LOGIN_ACT = 420;
    private String logged_user = "",logged_pfp = "",logged_mail = "", logged_key = "";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private FloatingActionButton fabNewPost;
    private RecyclerView rvMain;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabNewPost = findViewById(R.id.fabNewPost);
        rvMain = findViewById(R.id.rvMain);

        adapter = new PostAdapter(this,this);
        rvMain.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,true);
        rvMain.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference("posts");

        fabNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPostActivity.class);

                intent.putExtra(CURR_USER,logged_user);
                intent.putExtra(CURR_PFP,logged_pfp);

                startActivity(intent);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                try {
                    for(DataSnapshot one : snapshot.getChildren()){
                        Post p = one.getValue(Post.class);
                        adapter.add(p);
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

                rvMain.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });

        if(logged_mail.equals("") || logged_mail.equals("null")){
            Intent intern = new Intent(MainActivity.this,LoginActivity.class);
            //startActivity(intern);
            startActivityForResult(intern,LOGIN_ACT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==LOGIN_ACT){
                if (data != null) {
                    logged_mail = data.getStringExtra(LoginActivity.LOGGED_MAIL);
                    Log.i("logged mail",logged_mail);
                    userRef = database.getReference("users");
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                for(DataSnapshot one : snapshot.getChildren()){
                                    User use = one.getValue(User.class);
                                    use.setKey(one.getKey());
                                    if(use.getEmail().equals(logged_mail)){
                                        Log.i("key",use.getKey());
                                        logged_pfp = use.getProfile_picture();
                                        logged_user = use.getUser();
                                        logged_key = use.getKey();
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
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.opcProfile:
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);

                intent.putExtra(CURR_USER,logged_user);
                intent.putExtra(CURR_PFP,logged_pfp);
                intent.putExtra(CURR_KEY,logged_key);

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Post post = adapter.getPost(position);

        Intent intent = new Intent(MainActivity.this, PostActivity.class);

        intent.putExtra(CURR_USER,logged_user);
        intent.putExtra(CURR_PFP,logged_pfp);
        intent.putExtra(POST_USER,post.getUser());
        intent.putExtra(POST_AUDIO,post.getAudio());
        intent.putExtra(POST_DESC,post.getDesc());
        intent.putExtra(POST_DATE,post.getFechaHora());
        intent.putExtra(POST_IMG,post.getImg());
        intent.putExtra(POST_PFP,post.getPfp());

        startActivity(intent);
    }
}