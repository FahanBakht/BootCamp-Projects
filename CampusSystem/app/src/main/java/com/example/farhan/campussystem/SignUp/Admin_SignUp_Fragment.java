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
import com.example.farhan.campussystem.Models.Admin;
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
public class Admin_SignUp_Fragment extends Fragment {
    private EditText adminName;
    private EditText adminEmail;
    private EditText adminPassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    public Admin_SignUp_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_admin__sign_up_, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Creating account please wait");

        adminName = mainView.findViewById(R.id.et_SignUp_Admin_Name);
        adminEmail = mainView.findViewById(R.id.et_SignUp_Admin_Email);
        adminPassword = mainView.findViewById(R.id.et_SignUp_Admin_Pass);
        Button btnAdminSignUp = mainView.findViewById(R.id.btn_SignUp_Admin);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Admins");

        btnAdminSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        return mainView;
    }

    private void signUp() {
        final String name = adminName.getText().toString();
        final String email = adminEmail.getText().toString();
        String pass = adminPassword.getText().toString();

        if (!name.isEmpty()) {
            if (!email.isEmpty()) {
                if (!pass.isEmpty()) {

                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String adminUid = mAuth.getCurrentUser().getUid();
                                        mReference.child(adminUid).setValue(new Admin(name, email, adminUid));
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
                    adminPassword.setError("Please enter password");
                }
            } else {
                adminEmail.setError("Please enter company email");
            }
        } else {
            adminName.setError("Please enter company name");
        }

    }

}
