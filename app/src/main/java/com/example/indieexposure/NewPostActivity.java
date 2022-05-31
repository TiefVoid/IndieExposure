package com.example.indieexposure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewPostActivity extends AppCompatActivity {
    public static final int ACCION_SELECCION_IMAGEN = 42;
    public static final int ACCION_SELECCION_AUDIO = 24;
    private Button bNewPh, bNewAud, bPub;
    private EditText etDesc;
    private TextView selPhoto, selAudio;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseStorage storage;
    private String img="",aud="",user="",pfp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        bNewAud = findViewById(R.id.bNewAud);
        bNewPh = findViewById(R.id.bNewPh);
        bPub = findViewById(R.id.bPub);
        etDesc = findViewById(R.id.etDesc);
        selPhoto = findViewById(R.id.selPhoto);
        selAudio = findViewById(R.id.selAudio);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference();
        storage = FirebaseStorage.getInstance();

        bNewPh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });

        bNewAud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadAudio();
            }
        });
        
        bPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPost();
            }
        });

        configVar();
    }

    private void configVar() {
        Intent intent = getIntent();

        if(intent!=null){
            user = intent.getStringExtra(MainActivity.CURR_USER);
            pfp = intent.getStringExtra(MainActivity.CURR_PFP);
        }
    }

    private void newPost() {
        String desc = etDesc.getText().toString().trim();

        Post post = new Post();

        post.setAudio(aud);
        post.setImg(img);
        post.setDesc(desc);
        post.setFechaHora(System.currentTimeMillis());
        post.setUser(user);
        post.setPfp(pfp);

        myRef.child("posts").child(user+System.currentTimeMillis()).setValue(post);
        etDesc.setText("");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                finish();
            }
        }, 5000);
    }

    private void uploadAudio() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(intent, ACCION_SELECCION_AUDIO);
    }

    private void uploadPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(intent, ACCION_SELECCION_IMAGEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == ACCION_SELECCION_IMAGEN){
                Uri uri = data.getData();

                StorageReference storageRef = storage.getReference().child("images/" + uri.getLastPathSegment());

                storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                img = uri.toString();
                                selPhoto.setText(uri.getLastPathSegment());
                            }
                        });
                    }
                });
            }

            if(requestCode == ACCION_SELECCION_AUDIO){
                Uri uri = data.getData();

                StorageReference storageRef = storage.getReference().child("audio/" + uri.getLastPathSegment());

                storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                aud = uri.toString();
                                selAudio.setText(uri.getLastPathSegment());
                            }
                        });
                    }
                });
            }
        }
    }
}