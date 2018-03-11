package com.example.farhan.campussystem.Company;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farhan.campussystem.Models.CompanyProfile;
import com.example.farhan.campussystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateCompanyProfile extends AppCompatActivity {
    private static final String TAG = "CreateCompanyActivity";
    private TextView imgError;
    private ImageView companyImage;
    private EditText companyName;
    private EditText companyAboutUs;
    private EditText companyContact;
    private EditText companyEmail;
    private EditText companyLocation;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_LOAD_IMAGE_PREVIOUS = 2;
    Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company_profile);

        progressDialog = new ProgressDialog(CreateCompanyProfile.this);
        progressDialog.setMessage("Loading please wait");

        imgError = findViewById(R.id.txt_Error_Insert_Image);
        companyImage = findViewById(R.id.company_Profile_Image);
        companyName = findViewById(R.id.et_Profile_Company_Name);
        companyAboutUs = findViewById(R.id.et_Profile_Company_AboutUs);
        companyContact = findViewById(R.id.et_Profile_Company_Contact);
        companyEmail = findViewById(R.id.et_Profile_Company_Email);
        companyLocation = findViewById(R.id.et_Profile_Company_Location);
        Button btnSetProfile = findViewById(R.id.btn_Create_Company_Profile);
        ImageButton selectCompanyImage = findViewById(R.id.btn_Company_Profile_Add_Image);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child("CompanyProfile").child(mAuth.getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    CompanyProfile companyProfile = dataSnapshot.getValue(CompanyProfile.class);
                    if (companyProfile != null) {
                        companyName.setText(companyProfile.getCompanyName());
                        Glide.with(CreateCompanyProfile.this).load(companyProfile.getCompanyProfileImage()).into(companyImage);
                        companyAboutUs.setText(companyProfile.getCompanyAboutUs());
                        companyContact.setText(companyProfile.getCompanyContact());
                        companyEmail.setText(companyProfile.getCompanyEmail());
                        companyLocation.setText(companyProfile.getCompanyLocation());
                        setTitle(companyProfile.getCompanyName());
                        //selectedImageUri = Uri.parse(companyProfile.getCompanyProfileImageUri());
                        progressDialog.cancel();
                        Log.e(TAG, "onDataChange:selectedImageUri " + selectedImageUri);
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CreateCompanyProfile.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        selectCompanyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        btnSetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile();
            }
        });
    }

    private void setProfile() {
        progressDialog.show();

        final String compName = companyName.getText().toString();
        final String compAboutUs = companyAboutUs.getText().toString();
        final String compContact = companyContact.getText().toString();
        final String compEmail = companyEmail.getText().toString();
        final String compLocation = companyLocation.getText().toString();

        if (selectedImageUri != null) {
            if (!compName.isEmpty()) {
                if (!compAboutUs.isEmpty()) {
                    if (!compContact.isEmpty()) {
                        if (!compEmail.isEmpty()) {
                            if (!compLocation.isEmpty()) {

                                final FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference imgRef = storage.getReference().child(System.currentTimeMillis() + ".jpg");
                                UploadTask uploadTask = imgRef.putFile(selectedImageUri);
                                Log.e(TAG, "setProfile: selectedImageUri" + selectedImageUri);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(CreateCompanyProfile.this, "Error while Uploading Image: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                        String downloadUrlStr = downloadUrl.toString();

                                        CompanyProfile companyProfile = new CompanyProfile(downloadUrlStr, compName, compAboutUs, compContact, compEmail, compLocation, mAuth.getCurrentUser().getUid(), selectedImageUri.toString());
                                        reference.setValue(companyProfile);

                                        progressDialog.dismiss();
                                        Toast.makeText(CreateCompanyProfile.this, "Post Added Successfully", Toast.LENGTH_SHORT).show();

                                        companyImage.setImageResource(0);
                                        companyName.setText("");
                                        companyAboutUs.setText("");
                                        companyContact.setText("");
                                        companyEmail.setText("");
                                        companyLocation.setText("");
                                    }
                                });

                            } else {
                                companyLocation.setError("Please enter Location");
                            }
                        } else {
                            companyEmail.setError("Please enter Email");
                        }
                    } else {
                        companyContact.setError("Please enter Contact");
                    }
                } else {
                    companyAboutUs.setError("Please enter About Us");
                }
            } else {
                companyName.setError("Please enter companyName");
            }

        } else {
            imgError.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
    }

   /* private void getImageByUri() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(selectedImageUri, "image*//*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE_PREVIOUS);
    }*/

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                // Get the url from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Set the image in ImageView
                    imgError.setVisibility(View.GONE);
                    companyImage.setImageURI(selectedImageUri);
                }
            } else if (resultCode == RESULT_OK) {
                if (requestCode == RESULT_LOAD_IMAGE_PREVIOUS) {
                    selectedImageUri = data.getData();
                }
            }
        }
    }

}
