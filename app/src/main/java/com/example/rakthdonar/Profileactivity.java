package com.example.rakthdonar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Profileactivity extends AppCompatActivity {
    Intent intent;
    EditText bloodgroup, age, weight, rencentlydonated;
    String bloodp, ag, we, rdonated, gender, userid, email;
    ImageView imageView;
    RadioGroup sex;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    Button submit, image;
    RadioButton option;
    FirebaseAuth fauth;
    private FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        fauth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.profilepic);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        email = intent.getStringExtra("email");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_profileactivity);
        bloodgroup = findViewById(R.id.bloodgroup);
        image = findViewById(R.id.imgbutton);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);

            }

        });
        age = findViewById(R.id.age);
        weight = findViewById(R.id.weight);
        rencentlydonated = findViewById(R.id.recentlydonated);
        sex = findViewById(R.id.sex);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bloodp = bloodgroup.getText().toString();
                ag = age.getText().toString();
                we = weight.getText().toString();
                rdonated = rencentlydonated.getText().toString();
                int selectedID = sex.getCheckedRadioButtonId();
                option = findViewById(selectedID);
                gender = option.getText().toString();
                adddatatoFirebase(ag, bloodp, gender, rdonated, we, userid);
                uploadImage();



            }

            private void adddatatoFirebase(String a, String b, String g, String r, String w, String userid) {
                User user = new User(a, b, g, r, w, email);
                firebaseFirestore.collection("Users").document(userid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Profileactivity.this, "Succes", Toast.LENGTH_SHORT).show();
                        reload();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profileactivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }

    private void reload() {
        Intent intent = new Intent(getApplicationContext(), DonarActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            imageView.setImageURI(filePath);

        }

    }

    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref =storageReference.child("images/"+UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Profileactivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(Profileactivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int)progress + "%");
                            }


                    });


        }
    }
}