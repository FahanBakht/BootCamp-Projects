package com.example.farhan.parkingbookingsystem.Login_and_SignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farhan.parkingbookingsystem.Models.User;
import com.example.farhan.parkingbookingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userEmail;
    private EditText userPassword;
    private EditText userCnicNumber;
    private EditText userCarName;
    private EditText userCarLicensePlateNumber;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Creating account please wait");

        userName = findViewById(R.id.et_SignUp_User_Name);
        userEmail = findViewById(R.id.et_SignUp_User_Email);
        userPassword = findViewById(R.id.et_SignUp_User_Pass);
        userCnicNumber = findViewById(R.id.et_SignUp_User_Cnic_Number);
        userCarName = findViewById(R.id.et_SignUp_User_Car_Name);
        userCarLicensePlateNumber = findViewById(R.id.et_SignUp_User_car_license_plate_number);
        Button btnStudentSignUp = findViewById(R.id.btn_SignUp_User);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("RegisteredAuth");

        btnStudentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {
        final String uName = userName.getText().toString();
        final String uEmail = userEmail.getText().toString();
        String uPass = userPassword.getText().toString();
        final String uCnicNumber = userCnicNumber.getText().toString();
        final String uCarName = userCarName.getText().toString();
        final String uCarLicensePlateNumber = userCarLicensePlateNumber.getText().toString();

        if (!uName.isEmpty()) {
            if (!uEmail.isEmpty()) {
                if (!uPass.isEmpty()) {
                    if (!uCnicNumber.isEmpty()) {
                        if (!uCarName.isEmpty()) {
                            if (!uCarLicensePlateNumber.isEmpty()) {

                                progressDialog.show();
                                mAuth.createUserWithEmailAndPassword(uEmail, uPass)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    String userUid = mAuth.getCurrentUser().getUid();
                                                    mReference.child("Users").child(userUid).setValue(new User(uName, uEmail, uCnicNumber, uCarName, uCarLicensePlateNumber, userUid));
                                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                    mAuth.signOut();
                                                    progressDialog.cancel();
                                                    startActivity(intent);
                                                    finish();
                                                    Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Error while Registering User " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    progressDialog.dismiss();
                                                }
                                            }
                                        });
                            } else {
                                userCarLicensePlateNumber.setError("Please enter your Car License Plate Number");
                            }
                        } else {
                            userCarName.setError("Please enter your car name");
                        }
                    } else {
                        userCnicNumber.setError("Please enter your CNIC Number");
                    }
                } else {
                    userPassword.setError("Please enter your password");
                }

            } else {
                userEmail.setError("Please enter your email");
            }
        } else {
            userName.setError("Please enter your name");
        }
    }
}
