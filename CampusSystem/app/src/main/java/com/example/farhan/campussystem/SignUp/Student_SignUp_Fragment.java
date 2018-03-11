package com.example.farhan.campussystem.SignUp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farhan.campussystem.LoginActivity;
import com.example.farhan.campussystem.Models.Student;
import com.example.farhan.campussystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Student_SignUp_Fragment extends Fragment {

    private EditText studentName;
    private EditText studentEmail;
    private EditText studentPassword;
    private EditText studentQualification;
    private EditText studentAge;
    private EditText studentCity;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;


    public Student_SignUp_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_student__sign_up_, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Creating account please wait");

        studentName = mainView.findViewById(R.id.et_SignUp_Student_Name);
        studentEmail = mainView.findViewById(R.id.et_SignUp_Student_Email);
        studentPassword = mainView.findViewById(R.id.et_SignUp_Student_Pass);
        studentQualification = mainView.findViewById(R.id.et_SignUp_Student_Qualification);
        studentAge = mainView.findViewById(R.id.et_SignUp_Student_Age);
        studentCity = mainView.findViewById(R.id.et_SignUp_Student_City);
        Button btnStudentSignUp = mainView.findViewById(R.id.btn_SignUp_Student);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Students");

        btnStudentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        return mainView;

    }

    private void signUp() {
        final String stdName = studentName.getText().toString();
        final String stdEmail = studentEmail.getText().toString();
        String stdPass = studentPassword.getText().toString();
        final String stdQualification = studentQualification.getText().toString();
        final String stdAge = studentAge.getText().toString();
        final String stdCity = studentCity.getText().toString();

        if (!stdName.isEmpty()) {
            if (!stdEmail.isEmpty()) {
                if (!stdPass.isEmpty()) {
                    if (!stdQualification.isEmpty()) {
                        if (!stdAge.isEmpty()) {
                            if (!stdCity.isEmpty()) {

                                progressDialog.show();
                                mAuth.createUserWithEmailAndPassword(stdEmail, stdPass)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    String stdUid = mAuth.getCurrentUser().getUid();
                                                    mReference.child(stdUid).setValue(new Student(stdName, stdEmail, stdQualification, stdAge, stdCity, stdUid));
                                                    Intent intent = new Intent(getContext(), LoginActivity.class);
                                                    mAuth.signOut();
                                                    progressDialog.cancel();
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                    Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getContext(), "Error while Registering User " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    progressDialog.dismiss();
                                                }
                                            }
                                        });
                            } else {
                                studentCity.setError("Please enter your city");
                            }
                        } else {
                            studentAge.setError("Please enter your age");
                        }
                    } else {
                        studentQualification.setError("Please enter your qualification");
                    }
                } else {
                    studentPassword.setError("Please enter your password");
                }

            } else {
                studentEmail.setError("Please enter your email");
            }
        } else {
            studentName.setError("Please enter your name");
        }
    }

}
