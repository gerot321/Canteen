package com.example.gerrys.canteen;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.gerrys.canteen.Database.Database;
import com.example.gerrys.canteen.Model.Order;
import com.example.gerrys.canteen.Model.Shoe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ShoeDetail extends AppCompatActivity {

    TextView shoeName, shoePrice, shoeSize, shoeDescription;
    ImageView shoeImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String productId = "";

    FirebaseDatabase database;
    DatabaseReference shoes;

    Shoe currentShoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_detail);

        // Firebase
        database = FirebaseDatabase.getInstance();
        shoes = database.getReference("Product");

        // Init view
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        productId,
                        currentShoe.getName(),
                        numberButton.getNumber(),
                        currentShoe.getPrice(),
                        currentShoe.getStock()
                ));

                Toast.makeText(ShoeDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        shoeDescription = (TextView)findViewById(R.id.shoe_description);
        shoeName = (TextView)findViewById(R.id.shoe_name);
        shoePrice = (TextView)findViewById(R.id.shoe_price);
        shoeSize = (TextView)findViewById(R.id.shoe_size);

        shoeImage = (ImageView)findViewById(R.id.img_shoe);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        // Get shoe id from intent
        if (getIntent() != null)
            productId = getIntent().getStringExtra("ShoeId");
        if (!productId.isEmpty()){
            getDetailShoe(productId);
        }


    }

    private void getDetailShoe(String shoeId) {
        shoes.child(shoeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentShoe = dataSnapshot.getValue(Shoe.class);

                // Set image
                Picasso.with(getBaseContext()).load(currentShoe.getImage())
                        .into(shoeImage);

                collapsingToolbarLayout.setTitle(currentShoe.getName());

                shoeSize.setText(currentShoe.getStock());

                shoeName.setText(currentShoe.getName());

                shoePrice.setText(currentShoe.getPrice());

                shoeDescription.setText(currentShoe.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
