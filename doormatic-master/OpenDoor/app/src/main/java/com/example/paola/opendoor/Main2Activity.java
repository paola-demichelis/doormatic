package com.example.paola.opendoor;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    public TextView tv;
    public ImageView iv;
    public Button share;
    public Button open;
    public Button openFriends;

    public String nome;
    DatabaseReference myRef;
    FirebaseDatabase database;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv=(TextView) findViewById(R.id.textView2);
        iv=(ImageView) findViewById(R.id.imageView);

        open=(Button) findViewById(R.id.button4);
        openFriends=(Button) findViewById(R.id.button2);


        nome = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String adress = getIntent().getStringExtra("adress");

        tv.setText(nome+"\n"+surname+"\n"+adress);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(nome);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference storageReference = mStorageRef.child("images/"+nome+".png");

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(iv);


        openFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("friend").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> list = new ArrayList<>();
                        //HashMap<String,User> list2=new HashMap<String, User>();
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            User user = child.getValue(User.class);
                            list.add(user);

                            //list.add(child.getValue(User.class));
                        }
                        Intent intent = new Intent(getApplicationContext(),ActivityOpenFriends.class);
                        ArrayList<String> nomi=new ArrayList<String>();
                        ArrayList<String> date=new ArrayList<String>();

                        for(int i=0;i<list.size();i++){
                            nomi.add(list.get(i).getName());
                        }
                        for(int i=0;i<list.size();i++){
                            date.add(list.get(i).getFecha());
                        }
                        //intent.putExtra("friends",(ArrayList<User>) list);
                        intent.putExtra("names",nomi);
                        intent.putExtra("dates",date);

                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://10.25.91.26:8080/abrir";
                Toast.makeText(getApplicationContext(), "Opening door", Toast.LENGTH_LONG).show();
                new Opening().execute(url);
                //new Opening().postData(url);

            }
        });

    }
    private class Opening extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String...params){
            postData(params[0]);
            return "successfully";
        }

        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(), "Door closed " + result, Toast.LENGTH_LONG).show();

        }

        public void postData(String web) {
            try {
                URL url = new URL(web);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
