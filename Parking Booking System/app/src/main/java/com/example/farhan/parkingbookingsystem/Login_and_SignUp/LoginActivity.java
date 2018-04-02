package com.example.farhan.parkingbookingsystem.Login_and_SignUp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.parkingbookingsystem.Admin.AdminActivity;
import com.example.farhan.parkingbookingsystem.Users.UserActivity;
import com.example.farhan.parkingbookingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private ProgressDialog progressDialog;
    private EditText email;
    private EditText pass;
    private String currentLoginUid;
    private ScrollView loginRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        if (isNetworkAvailable()) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging please wait");

            loginRootView = findViewById(R.id.login_Root_View);

            mAuth = FirebaseAuth.getInstance();
            mReference = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("RegisteredAuth");

            checkAuth();

            email = findViewById(R.id.et_Login_Email);
            pass = findViewById(R.id.et_Login_Pass);
            Button btnLogin = findViewById(R.id.btn_Login);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });

            TextView signUp = findViewById(R.id.navigate_To_SignUp);
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            TextView networkError = findViewById(R.id.login_Network_Error);
            networkError.setVisibility(View.VISIBLE);
        }
    }

    private void login() {
        String etEmailStr = email.getText().toString();
        String etPasswordStr = pass.getText().toString();

        if (!etEmailStr.isEmpty()) {
            if (!etPasswordStr.isEmpty()) {
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(etEmailStr, etPasswordStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    currentLoginUid = task.getResult().getUser().getUid();
                                    checkLogin();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressDialog.cancel();
                                }
                            }
                        });

            } else {
                pass.setError("Password Required");
            }
        } else {
            email.setError("Email Required");
        }
    }

    private void checkAuth() {
        // Checking if user is Sign-in or not
        if (mAuth.getCurrentUser() != null) {
            progressDialog.show();
            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        if (dataSnapshot.child("Users").hasChild(mAuth.getCurrentUser().getUid())) {
                            progressDialog.cancel();
                            finish();
                            startActivity(new Intent(LoginActivity.this, UserActivity.class));
                        } else if (dataSnapshot.child("Admin").hasChild(mAuth.getCurrentUser().getUid())) {
                            progressDialog.cancel();
                            finish();
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        } else {
                            progressDialog.cancel();
                            loginRootView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.cancel();
                }
            });
        } else {
            loginRootView.setVisibility(View.VISIBLE);
        }
    }

    private void checkLogin() {
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.child("Users").hasChild(currentLoginUid)) {
                        progressDialog.cancel();

                        startActivity(new Intent(LoginActivity.this, UserActivity.class));
                    } else if (dataSnapshot.child("Admin").hasChild(currentLoginUid)) {
                        progressDialog.cancel();

                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    } else {
                        progressDialog.cancel();
                        Toast.makeText(LoginActivity.this, "Error: User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.cancel();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
