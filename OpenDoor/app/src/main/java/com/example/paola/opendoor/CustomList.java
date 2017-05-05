package com.example.paola.opendoor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by paola on 05/05/17.
 */


public class CustomList extends ArrayAdapter<String> {
    private final Activity activity;
    private final String[] nome;
    private final String[] date;
    private final Integer[] imageId;
    private int position=0;
    Context context;

    public CustomList(Activity activity_param, String[] nome, String[] date, Integer[] imageId) {
        super(activity_param, R.layout.activity_open_friends, nome);
        this.activity = activity_param;
        this.nome = nome;
        this.date=date;
        this.imageId = imageId;
    }
    public View getView(final int pos, View view, ViewGroup parent){

        position=pos;
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_friends, null);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView2);
        imageView.setImageResource(imageId[position]);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView7);
        txtTitle.setText(nome[position]);

        TextView txtDate = (TextView) rowView.findViewById(R.id.textView9);
        txtDate.setText(date[position]);
        return rowView;
    }
}
