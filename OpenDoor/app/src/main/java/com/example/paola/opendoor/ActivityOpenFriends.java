package com.example.paola.opendoor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityOpenFriends extends AppCompatActivity {

    ListView list;
    //ricevo la lista dei nomi degli amici

    String[] names;
    String [] dates;
    Integer[] imageId;
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


   //     friend = getIntent().getStringArrayExtra("friends");
        imageId = new Integer[]{
                R.drawable.logo,
                R.drawable.logo,
        };


        adapter = new CustomList(this, names,dates, imageId);

        list = (ListView)findViewById(R.id.list2);
        list.setAdapter(adapter);
        list.setFocusable(false);
        list.setItemsCanFocus(false);

    }
}
