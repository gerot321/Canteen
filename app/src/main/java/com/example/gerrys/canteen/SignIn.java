package com.example.gerrys.canteen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerrys.canteen.Common.Common;
import com.example.gerrys.canteen.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText etPhone, etPassword;

    Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etPhone = (MaterialEditText)findViewById(R.id.etPhone);
        etPassword = (MaterialEditText)findViewById(R.id.etPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        // Initialize firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("loading...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if user exist in the database
                        if(dataSnapshot.child(etPhone.getText().toString()).exists()) {

                            mDialog.dismiss();

                            // Get user information
                            User user = dataSnapshot.child(etPhone.getText().toString()).getValue(User.class);
                            user.setPhone(etPhone.getText().toString()); //set phone

                            if (user.getPassword().equals(etPassword.getText().toString())) {

                                Intent intent = new Intent(SignIn.this, Home.class);
                                intent.putExtra("phoneId",etPhone.getText().toString() );
                                Common.currentUser = user;
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "Sign in failed!", Toast.LENGTH_SHORT).show();

                            }

                        }else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
