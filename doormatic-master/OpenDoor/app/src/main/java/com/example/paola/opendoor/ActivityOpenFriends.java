package com.example.paola.opendoor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ActivityOpenFriends extends AppCompatActivity {

    ListView list;
    //ricevo la lista dei nomi degli amici

    String[] names;
    String [] dates;
    CustomList adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_friends);
        ArrayList<String> fr = getIntent().getStringArrayListExtra("names");
        ArrayList<String> dat = getIntent().getStringArrayListExtra("dates");
        //ArrayList<User> fr =(ArrayList<User>)getIntent().getExtras().getSerializable("YourArrayList");

        names=new String[fr.size()];
        for(int i=0;i<fr.size();i++){
            names[i]=fr.get(i);
        }
        dates=new String[fr.size()];
        for(int i=0;i<dat.size();i++){
            dates[i]=dat.get(i);
        }

        adapter = new CustomList(this, names,dates);

        list = (ListView)findViewById(R.id.list2);
        list.setAdapter(adapter);
        list.setFocusable(false);
        list.setItemsCanFocus(false);

    }
    public class Opening extends AsyncTask<String, Void, String> {

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
