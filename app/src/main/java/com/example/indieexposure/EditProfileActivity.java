package com.example.indieexposure;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfileActivity extends AppCompatActivity {
    private static final int ACCION_SELECCION_IMAGEN = 34;
    private EditText etPseud, etBio;
    private TextView tvNewPfp;
    private Button bNewPfp, bSaveEdit;
    private String pseud = "", bio = "", pfp = "", user = "", key = "", new_pfp = "";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etPseud = findViewById(R.id.etPseud);
        etBio = findViewById(R.id.etBio);
        tvNewPfp = findViewById(R.id.tvNewPfp);
        bNewPfp = findViewById(R.id.bNewPfp);
        bSaveEdit = findViewById(R.id.bSaveEdit);

        database = FirebaseDatabase.getInstance("https://proyecto-final-6dd98-default-rtdb.firebaseio.com/");
        myRef = database.getReference("users");
        storage = FirebaseStorage.getInstance();

        bSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

        bNewPfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPfp();
            }
        });

        configUI();
    }

    private void uploadPfp() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(intent, ACCION_SELECCION_IMAGEN);
    }

    private void saveChanges() {
        myRef.child(key).child("pseud").setValue(etPseud.getText());
        myRef.child(key).child("bio").setValue(etBio.getText());
        if(!new_pfp.equals("")){
            myRef.child(key).child("profile_picture").setValue(new_pfp);
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                finish();
            }
        }, 5000);
    }

    private void configUI() {
        Intent intent = getIntent();

        pseud = intent.getStringExtra(MyProfileActivity.CURR_PSEUD);
        etPseud.setText(pseud);

        bio = intent.getStringExtra(MyProfileActivity.CURR_BIO);
        etBio.setText(bio);

        pfp = intent.getStringExtra(MyProfileActivity.CURR_PFP);

        user = intent.getStringExtra(MyProfileActivity.CURR_USER);

        key = intent.getStringExtra(MyProfileActivity.CURR_KEY);
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
                                new_pfp = uri.toString();
                                tvNewPfp.setText(uri.getLastPathSegment());
                            }
                        });
                    }
                });
            }
        }
    }
}