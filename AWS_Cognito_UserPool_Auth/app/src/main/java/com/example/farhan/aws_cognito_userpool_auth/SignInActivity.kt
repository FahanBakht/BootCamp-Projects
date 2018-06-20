package com.example.farhan.aws_cognito_userpool_auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private lateinit var etSignInEmail: EditText
    private lateinit var etSignInPass: EditText
    private lateinit var progressDialog: SpotsDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        /*if (Utils.getUserPool() != null) {
            println("SignIn Activity Log"+Utils.getUserPool()?.currentUser)
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            this@SignInActivity.finish()
        }*/

        initViews()

        btn_navigateTo_SignUp.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }

        btn_navigateTo_ForgetPass.setOnClickListener {
            startActivity(Intent(this@SignInActivity, ForgetPasswordActivity::class.java))
        }

        btn_signIn.setOnClickListener {
            singIn()
        }
    }

    private fun singIn() {
        progressDialog.show()
        val email = etSignInEmail.text.toString().trim()
        val password = etSignInPass.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {

                // Callback handler for the sign-in process
                val authenticationHandler = object : AuthenticationHandler {
                    override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                        // Sign-in was successful, cognitoUserSession will contain tokens for the user
                        progressDialog.dismiss()
                        Utils.toast(this@SignInActivity, "Sign in Successful")
                        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                        this@SignInActivity.finish()
                    }

                    override fun authenticationChallenge(continuation: ChallengeContinuation?) {

                    }

                    override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation, userId: String) {
                        // The API needs user sign-in credentials to continue
                        val authenticationDetails = AuthenticationDetails(email, password, null)

                        // Pass the user sign-in credentials to the continuation
                        authenticationContinuation.setAuthenticationDetails(authenticationDetails)

                        // Allow the sign-in to continue
                        authenticationContinuation.continueTask()
                    }

                    override fun getMFACode(multiFactorAuthenticationContinuation: MultiFactorAuthenticationContinuation) {

                    }

                    override fun onFailure(exception: Exception) {
                        progressDialog.dismiss()
                        Utils.toast(this@SignInActivity, "Sign in fails ${exception.message}")
                    }
                }

                // Sign in the user
                Utils.getUserPool()?.getUser(email)?.getSessionInBackground(authenticationHandler)
            } else {
                progressDialog.dismiss()
                etSignInPass.error = "This field can't be empty"
            }
        } else {
            progressDialog.dismiss()
            etSignInEmail.error = "This field can't be empty"
        }
    }

    private fun initViews() {
        etSignInEmail = findViewById(R.id.et_signIn_email)
        etSignInPass = findViewById(R.id.et_signIn_pass)
        progressDialog = SpotsDialog(this@SignInActivity, "Authenticating Please Wait....", R.style.CustomDialog)
        Utils.init(this@SignInActivity)
    }
}
