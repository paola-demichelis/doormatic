package com.example.paola.opendoor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public String nome;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv=(TextView) findViewById(R.id.textView2);
        iv=(ImageView) findViewById(R.id.imageView);
        share=(Button) findViewById(R.id.button3);
        open=(Button) findViewById(R.id.button4);


        nome = getIntent().getStringExtra("name");
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
                    }
                });
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://10.25.91.119:8080/abrir";
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
