package com.example.gerrys.canteen;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerrys.canteen.Common.Common;
import com.example.gerrys.canteen.Database.Database;
import com.example.gerrys.canteen.Model.Order;
import com.example.gerrys.canteen.Model.Request;
import com.example.gerrys.canteen.ViewHolder.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Checkout extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;

    AppCompatTextView Address;
    CardView Ovo;
    String Status = " ";
    List<Order> cart = new ArrayList<>();
    String PriCode, orderId;
    int total = 0;
    CartAdapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        Address = (AppCompatTextView)findViewById(R.id.address);
        Ovo = (CardView)findViewById(R.id.ovoPayment);



        cart = new Database(this).getCarts();
        for (Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

        Ovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new request
                Status = "Waiting";
                final Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        Address.getText().toString(),
                        String.valueOf(total),
                        cart,
                        Status,
                        "OVO"
                );
                Random r = new Random();
                int unic = r.nextInt(80 - 1) + 1;
                PriCode = Common.currentUser.getPhone();
                orderId = PriCode+Integer.toString(unic);
                final ProgressDialog mDialog = new ProgressDialog(Checkout.this);
                mDialog.setMessage("loading...");
                mDialog.show();
                requests.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if already user phone
                        if(dataSnapshot.child(orderId.toString()).exists()){
                            mDialog.dismiss();

                            Toast.makeText(Checkout.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                        }else {
                            mDialog.dismiss();

                            requests.child(orderId)
                                    .setValue(request);
                            Toast.makeText(Checkout.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                // Submit to Firebase


                //Clear cart
                new Database(getBaseContext()).clearCart();
                Toast.makeText(Checkout.this, "Thank you, your order has been placed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }




}
