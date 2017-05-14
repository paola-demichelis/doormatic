package com.example.paola.opendoor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by paola on 05/05/17.
 */


public class CustomList extends ArrayAdapter<String> {
    private final Activity activity;
    private final String[] nome;
    private final String[] date;
    private int position=0;
    private StorageReference mStorageRef;

    Context context;

    public CustomList(Activity activity_param, String[] nome, String[] date) {
        super(activity_param, R.layout.activity_open_friends, nome);
        this.activity = activity_param;
        this.nome = nome;
        this.date=date;
    }
    public View getView(final int pos, View view, ViewGroup parent){

        position=pos;
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_friends, null);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView2);
        //imageView.setImageResource(imageId[position]);

        final TextView txtTitle = (TextView) rowView.findViewById(R.id.textView7);
        txtTitle.setText(nome[position]);

        final TextView txtDate = (TextView) rowView.findViewById(R.id.textView9);
        txtDate.setText(date[position]);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference storageReference = mStorageRef.child("images/"+nome[position]+".png");

        Glide.with(getContext() )
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);




        Button button = (Button) rowView.findViewById(R.id.button5);
        button.setText("Open " + nome[position] + "'s door");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.25.91.26:8080/abrir";
                String name = String.valueOf(txtTitle.getText());
                String date = String.valueOf(txtDate.getText());
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = new Date();
                try{
                    startDate = dateFormat.parse(date);
                    String newDate = dateFormat.format(startDate);
                    System.out.println(startDate);
                } catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(startDate);

                Date actualdate = new Date();

                if(actualdate.before(startDate)) {
                    Toast.makeText(getContext(), "Opening " + name + "'s door", Toast.LENGTH_SHORT).show();
                    new Opening().execute(url);
                }else{
                    Toast.makeText(getContext(), "Key is expired", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (position % 2 == 0) {
            rowView.setBackgroundColor(Color.rgb(211,211,211));
        }else{
            rowView.setBackgroundColor(Color.rgb(192,192,192));
        }


        return rowView;
    }

    private class Opening extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String...params){
            postData(params[0]);
            return "successfully";
        }

        protected void onPostExecute(String result){
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
