package com.example.househuntadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    private int PICK;

    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextRating,editTextSpace,editTextPrice,editTextphoneNumber,editTextdes;

    CheckBox exclusive;
    Switch rent;

    ImageView imageView;

    private Button add;



    private FirebaseFirestore db;
    private CollectionReference houseRef;

    StorageReference reference;

    Uri imageUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PICK=1;

        editTextName = findViewById(R.id.edit_text_name);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextSpace = findViewById(R.id.edit_text_space);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextphoneNumber = findViewById(R.id.edit_text_phone);

        editTextRating = findViewById(R.id.edit_text_rating);

        editTextdes = findViewById(R.id.edit_text_des);
        rent = findViewById(R.id.Rent);
        exclusive = findViewById(R.id.exclusive);


        imageView = findViewById(R.id.imageView);

        add = (Button) findViewById(R.id.addBtn);

        db = FirebaseFirestore.getInstance();
        houseRef = db.collection("House");

        reference = FirebaseStorage.getInstance().getReference("House");

    }

    public void chooseImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK);
    }


    public void add(View view) {

        if(imageUri != null)
        {
            add.setEnabled(false);

            ContentResolver cr = getContentResolver();
            MimeTypeMap mine = MimeTypeMap.getSingleton();

            final StorageReference mref = reference.child(System.currentTimeMillis() + "."+mine.getExtensionFromMimeType(cr.getType(imageUri)));

            mref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();

                    mref.getDownloadUrl().addOnCompleteListener(
                            new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete( Task<Uri> task) {
                            String downloadUrl=task.getResult().toString();


                            String name = editTextName.getText().toString();
                            String address = editTextAddress.getText().toString();
                            String price = editTextPrice.getText().toString();
                            String space = editTextSpace.getText().toString();
                            String phoneNumber = editTextphoneNumber.getText().toString();
                            String rating = editTextRating.getText().toString();
                            String des = editTextdes.getText().toString();

                            String rentS;
                            if(rent.isChecked())
                            {
                                rentS = "true";
                            }
                            else
                            {
                                rentS = "false";
                            }

                            String exclusiveS;
                            if(exclusive.isChecked())
                            {
                                exclusiveS = "true";
                            }
                            else
                            {
                                exclusiveS = "false";
                            }


                            House note;
                            note = new House(name,address,price,space,phoneNumber,rating,downloadUrl,rentS,exclusiveS,des);

                            houseRef.add(note).addOnCompleteListener(
                                    new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(Task<DocumentReference> task) {
                                    Toast.makeText(MainActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                                    add.setEnabled(true);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                    add.setEnabled(true);
                                }
                            });
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    Toast.makeText(MainActivity.this, "Upload Fail", Toast.LENGTH_SHORT).show();
                    add.setEnabled(true);
                }
            });
        }




    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK && resultCode == RESULT_OK && data != null && data.getData() != null )
        {

            imageUri = data.getData();

            imageView.setImageURI(data.getData());
        }

    }


}
