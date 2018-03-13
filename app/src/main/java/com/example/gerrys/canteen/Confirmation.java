package com.example.gerrys.canteen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Confirmation extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference orderList;
    String[] tempOrder,Order;
    String ID,keys;
    Spinner oID;
    int count = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        database = FirebaseDatabase.getInstance();
        orderList = database.getReference("Requests");
        tempOrder = new String[20];
        final List<String> list = new ArrayList<String>();
        ID= getIntent().getStringExtra("userID");

        orderList.orderByChild("phone").equalTo(ID).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    list.add(key);
                    count++;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Order = new String[count];
       for(int i=0;i<count;i++){
           Order[i]=tempOrder[i];
       }

       oID = (Spinner)findViewById(R.id.orderList);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        oID.setAdapter(dataAdapter);

    }

}
