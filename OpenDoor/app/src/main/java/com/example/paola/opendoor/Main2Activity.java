package com.example.paola.opendoor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    public TextView tv;
    public ImageView iv;
   // private StorageReference mStorageRef;
    public Button share;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv=(TextView) findViewById(R.id.textView2);
        iv=(ImageView) findViewById(R.id.imageView);
        share=(Button) findViewById(R.id.button3);

        String nome = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String adress = getIntent().getStringExtra("adress");


        tv.setText(nome+"\n"+surname+"\n"+adress);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(nome);




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("friend").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> list = new ArrayList<>();
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            list.add(child.getValue(String.class));
                        }
                        Intent i = new Intent(getApplicationContext(), Main3Activity.class);
                        i.putExtra("friend",(ArrayList<String>) list);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }
        });


    //    mStorageRef = FirebaseStorage.getInstance().getReference();
/*
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(iv);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                //tv.setText("il tuo codice è ancora valido per: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tv.setText("done!");
            }
        }.start();
       // tv.setText(nome+"  "+surname+"  "+adress);
        File localFile = null;
        try {
            localFile = File.createTempFile("paola", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }


        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        StorageReference temp = taskSnapshot.getStorage();
                        Bitmap img = BitmapFactory.decodeFile(temp.getPath());
                        iv.setImageBitmap(img);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
*/


    }

}
