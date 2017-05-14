package com.example.paola.opendoor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzs.TAG;


public class MainActivity extends Activity {

    public EditText nome;
    public EditText code;
    public Button open;
    public TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        open = (Button) findViewById(R.id.button);
        nome = (EditText) findViewById(R.id.editText);
        code = (EditText) findViewById(R.id.editText2);
        tx=(TextView) findViewById(R.id.textView4);

        //myRef.child("online").setValue("true");


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = nome.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(id);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.child("password").getValue(String.class);
                        String result = code.getText().toString();
                        if(value!=null){
                        if (value.compareTo(result) == 0) {
                            Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                            String surname = dataSnapshot.child("surname").getValue(String.class);
                            String adress = dataSnapshot.child("adress").getValue(String.class);
                            i.putExtra("surname", surname);
                            i.putExtra("adress",adress);
                            i.putExtra("name", nome.getText().toString());
                            startActivity(i);
                        }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


            }
        });


    }}




        //myRef.setValue(user1);
/*
        myRef.child("01234").child("surname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                textView.setText((String)snapshot.getValue());

                // myRef.child("01234").child("surname")
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
*/