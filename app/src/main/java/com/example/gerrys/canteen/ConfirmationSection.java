package com.example.gerrys.canteen;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerrys.canteen.Model.Confirmation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationSection extends AppCompatActivity  {
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseDatabase database;
    DatabaseReference orderList;
    DatabaseReference confirmations;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private ImageView mImageView;
    private Uri mImageUri;
    private Button mButtonChooseImage, submit;
    private ProgressBar mProgressBar;
    private EditText name,no;


    String ID,keys;
    Spinner oID;
    int count = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        mImageView = (ImageView) findViewById(R.id.image_view);
        name = (EditText) findViewById(R.id.editName);
        no = (EditText) findViewById(R.id.editNo);
        mButtonChooseImage = (Button) findViewById(R.id.button_choose_image);
        submit = (Button) findViewById(R.id.button_upload);
        database = FirebaseDatabase.getInstance();
        orderList = database.getReference("Requests");
        confirmations = database.getReference("Confirmation");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final List<String> list = new ArrayList<String>();
        ID= getIntent().getStringExtra("userID");

        orderList.orderByChild("phone").equalTo(ID).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    list.add(child.getKey());
                    oID = (Spinner)findViewById(R.id.orderList);

                    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ConfirmationSection.this,android.R.layout.simple_spinner_item, list);
                    oID.setAdapter(dataAdapter);
                    oID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(ConfirmationSection.this, "Selected "+ dataAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //set the view for the Drop down list

        //set the ArrayAdapter to the spinner

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 500);
                                    Toast.makeText(ConfirmationSection.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    confirmations.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Check if already user phone
                                            if (dataSnapshot.child(oID.getSelectedItem().toString()).exists()) {
                                                // mDialog.dismiss();

                                                //Toast.makeText(addProduct.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //mDialog.dismiss();

                                                Confirmation products = new Confirmation(oID.getSelectedItem().toString(),no.getText().toString(), name.getText().toString(),"no Confirmed", taskSnapshot.getDownloadUrl().toString());
                                                confirmations.child(oID.getSelectedItem().toString()).setValue(products);
                                                // Toast.makeText(addProduct.this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ConfirmationSection.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar.setProgress((int) progress);
                                }
                            });
                } else {

                    Toast.makeText(ConfirmationSection.this, "No file selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}
