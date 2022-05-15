package com.example.indieexposure;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class PostActivity extends AppCompatActivity {
    private TextView pPos,pDur;
    private SeekBar sBar;
    private ImageView btR, btPl, btPs, btF;
    MediaPlayer mp;
    Handler handel = new Handler();
    Runnable run;

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

        mp = MediaPlayer.create(this,R.raw.grizabella);
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