package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PostActivity extends AppCompatActivity {
    public static final String CURR_USER = "Yo";
    public static final String CURR_PFP = "yop";
    public static final String CURR_KEY = "llave";
    public static final String POST_TIME = "ahorita";
    public static final String POST_USER = "este vato";
    public static final String POST_USER_KEY = "clave";
    private TextView pPos,pDur,tvPost,tvDesc;
    private SeekBar sBar;
    private ImageView btR, btPl, btPs, btF, ivPic, ivPostUser;
    private FloatingActionButton fabNewComment;
    private RecyclerView rvComm;
    private LinearLayout playBarLayout,buttonsLayout;
    MediaPlayer mp;
    Handler handel = new Handler();
    Runnable run;
    private CommentAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String logged_user = "", logged_pfp = "", post_user = "", logged_key = "",post_user_key="";
    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        pPos = findViewById(R.id.player_pos);
        pDur = findViewById(R.id.player_duration);
        sBar = findViewById(R.id.seek_bar);
        btR = findViewById(R.id.bt_rw);
        btPl = findViewById(R.id.bt_play);
        btPs = findViewById(R.id.bt_pause);
        btF = findViewById(R.id.bt_ff);
        tvPost = findViewById(R.id.tvPost);
        tvDesc = findViewById(R.id.tvDesc);
        ivPic = findViewById(R.id.ivPic);
        fabNewComment = findViewById(R.id.fabNewComment);

        //Recicly View Comment
        rvComm = findViewById(R.id.rvComm);
        //Se pone el adapter
        adapter = new CommentAdapter(this);
        rvComm.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvComm.setLayoutManager(layoutManager);
        //Firebase
        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference("comment");
        //Fin de lo de Recycle

        ivPostUser = findViewById(R.id.ivPostUser);
        playBarLayout = findViewById(R.id.playBarLayout);
        buttonsLayout = findViewById(R.id.buttonsLayout);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                try {
                    for(DataSnapshot one : snapshot.getChildren()){
                        Comment p = one.getValue(Comment.class);
                        String post_id = post_user + String.valueOf(time);
                        String post = p.getPost();
                        if(post.equals(post_id)){
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

        fabNewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, NewComment.class);

                intent.putExtra(CURR_USER,logged_user);
                intent.putExtra(CURR_PFP,logged_pfp);
                intent.putExtra(CURR_KEY,logged_key);
                intent.putExtra(POST_TIME,time);
                intent.putExtra(POST_USER,post_user);

                startActivity(intent);
            }
        });

        ivPostUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, ProfileFromPostActivity.class);

                intent.putExtra(CURR_KEY,logged_key);
                intent.putExtra(POST_USER_KEY,post_user_key);

                startActivity(intent);
            }
        });

        configUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void configUI() {
        Intent intent = getIntent();

        logged_user = intent.getStringExtra(MainActivity.CURR_USER);
        logged_pfp = intent.getStringExtra(MainActivity.CURR_PFP);
        logged_key = intent.getStringExtra(MainActivity.CURR_KEY);
        post_user_key = intent.getStringExtra(MainActivity.POST_USER_KEY);

        String p = tvPost.getText().toString();
        String postUser = intent.getStringExtra(MainActivity.POST_USER);
        post_user = postUser;
        p = p.replace("User",postUser);

        long fechaHora = intent.getLongExtra(MainActivity.POST_DATE,0);
        time = fechaHora;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, hh:mm a", new Locale( "ES"));
        p = p.replace("Date",simpleDateFormat.format( new Timestamp(fechaHora)));

        tvPost.setText(p);

        String desc = intent.getStringExtra(MainActivity.POST_DESC);
        tvDesc.setText(desc);

        String img = intent.getStringExtra(MainActivity.POST_IMG);
        if(!img.equals("")){
            Picasso.get().load( img ).into( ivPic );
            ivPic.setVisibility( View.VISIBLE );
        }else{
            ivPic.setImageResource(0);
            ivPic.setVisibility( View.GONE );
        }

        String pfp = intent.getStringExtra(MainActivity.POST_PFP);
        if(!pfp.equals("")){
            Picasso.get().load( pfp ).into( ivPostUser );
        }

        String audio = intent.getStringExtra(MainActivity.POST_AUDIO);
        if(!audio.equals("")){
            playBarLayout.setVisibility(View.VISIBLE);
            buttonsLayout.setVisibility(View.VISIBLE);
            configAudio(audio);
        }else{
            playBarLayout.setVisibility(View.GONE);
            buttonsLayout.setVisibility(View.GONE);
        }
    }

    private void configAudio(String audio) {
        Uri uri = Uri.parse(audio);
        mp = MediaPlayer.create(this,uri);
        run = new Runnable() {
            @Override
            public void run() {
                sBar.setProgress(mp.getCurrentPosition());
                handel.postDelayed(this,500);
            }
        };

        int dur = mp.getDuration();
        String sDur = convertFormat(dur);
        pDur.setText(sDur);

        btPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btPl.setVisibility(View.GONE);
                btPs.setVisibility(View.VISIBLE);
                mp.start();
                sBar.setMax(mp.getDuration());
                handel.postDelayed(run,0);
            }
        });

        btPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btPs.setVisibility(View.GONE);
                btPl.setVisibility(View.VISIBLE);
                mp.pause();
                handel.removeCallbacks(run);
            }
        });

        btF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPos = mp.getCurrentPosition();
                int dur = mp.getDuration();
                if(mp.isPlaying() && dur != curPos){
                    curPos += 5000;
                    pPos.setText(convertFormat(curPos));
                    mp.seekTo(curPos);
                }
            }
        });

        btR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPos = mp.getCurrentPosition();
                if(mp.isPlaying() && curPos > 5000){
                    curPos -= 5000;
                    pPos.setText(convertFormat(curPos));
                    mp.seekTo(curPos);
                }
            }
        });

        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mp.seekTo(i);
                }
                pPos.setText(convertFormat(mp.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btPs.setVisibility(View.GONE);
                btPl.setVisibility(View.VISIBLE);
                mp.seekTo(0);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int dur) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(dur),
                TimeUnit.MILLISECONDS.toSeconds(dur) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dur)));
    }
}