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
import com.example.farhan.campussystem.Models.Company;
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
public class Company_SignUp_Fragment extends Fragment {

    private EditText companyName;
    private EditText companyEmail;
    private EditText companyPassword;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    public Company_SignUp_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_company__sign_up_, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Creating account please wait");

        companyName = mainView.findViewById(R.id.et_SignUp_Company_Name);
        companyEmail = mainView.findViewById(R.id.et_SignUp_Company_Email);
        companyPassword = mainView.findViewById(R.id.et_SignUp_Company_Pass);
        Button btnCompanySignUp = mainView.findViewById(R.id.btn_SignUp_Company);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Companies");

        btnCompanySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });


        return mainView;
    }

    private void signUp() {
        final String compName = companyName.getText().toString();
        final String compEmail = companyEmail.getText().toString();
        String compPass = companyPassword.getText().toString();

        if (!compName.isEmpty()) {
            if (!compEmail.isEmpty()) {
                if (!compPass.isEmpty()) {

                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(compEmail, compPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String compUid = mAuth.getCurrentUser().getUid();
                                        mReference.child(compUid).setValue(new Company(compName, compEmail, compUid));
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
                    companyPassword.setError("Please enter password");
                }
            } else {
                companyEmail.setError("Please enter company email");
            }
        } else {
            companyName.setError("Please enter company name");
        }

    }

}
