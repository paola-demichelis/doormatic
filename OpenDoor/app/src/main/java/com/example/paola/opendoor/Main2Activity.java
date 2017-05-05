package com.example.paola.opendoor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Main2Activity extends AppCompatActivity {

    public TextView tv;
    public ImageView iv;
    public Button share;
    public Button open;
    public Button openFriends;

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
        openFriends=(Button) findViewById(R.id.button2);


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






/*
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new MyAsyncTask().execute(nome);
                    new MyAsyncTask().postData(nome);

            }
        });

*/

    }
/*
    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData(params[0]);
            return null;
        }

        protected void onPostExecute(Double result){
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }
        protected void onProgressUpdate(Integer... progress){
        }

        public void postData(String valueIWantToSend) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://indirizzo del rasberry");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nome", valueIWantToSend));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

    }*/
}
