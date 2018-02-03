package com.example.farhan.loginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int Google_RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    GoogleSignInClient mGoogleSignInClient;
    TwitterAuthClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_logOut = findViewById(R.id.btn_logOut);

        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();

        // Facebook Button OnClick Listener which Calls Facebook Login Fragment
        Button btn_faceBook = findViewById(R.id.btn_facebook);
        btn_faceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCallbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Log.e(TAG, "Facebook OnSuccess:" + loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Login Canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(MainActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                });
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }
        });

        // Google Button OnClick Listener which Calls Google Login
        Button btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                googleSignIn();
            }
        });

        Button btn_twitter = findViewById(R.id.btn_twitter);
        btn_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // init Twitter
                Twitter.initialize(MainActivity.this);

                // Client for requesting authorization and email from the user.
                client = new TwitterAuthClient();

                //make the call to login
                client.authorize(MainActivity.this, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        Log.e(TAG, "twitterLogin:successssssss");
                        handleTwitterSession(result.data);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        //feedback
                        Toast.makeText(getApplicationContext(), "Login Cancel Failure" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "SignOut from Current Auth", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Google_RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Google_RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e);

            }
        } else if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            // Pass the activity result to the login button.
            client.onActivityResult(requestCode, resultCode, data);
        } else {
            //If not request code is RC_SIGN_IN it must be facebook
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.e(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "" + user.getDisplayName() + "\n" + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "" + user.getDisplayName() + "\n" + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void handleTwitterSession(TwitterSession session) {
        Log.e(TAG, "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "" + user.getDisplayName() + "\n" + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
