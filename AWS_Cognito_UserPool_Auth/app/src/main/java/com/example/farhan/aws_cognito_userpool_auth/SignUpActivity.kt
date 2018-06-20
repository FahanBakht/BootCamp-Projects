package com.example.farhan.aws_cognito_userpool_auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var etSignUpEmail: EditText
    private lateinit var etSignUpPass: EditText
    private lateinit var etSignUpFirstName: EditText
    private lateinit var etSignUpLastName: EditText
    private lateinit var confirmationCallback: GenericHandler
    private lateinit var progressDialog: SpotsDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()

        btn_SignUp.setOnClickListener {
            signUp()
        }

        // Callback handler for confirmSignUp API
        confirmationCallback = object : GenericHandler {

            override fun onSuccess() {
                // User was successfully confirmed
                Utils.toast(this@SignUpActivity, "Your account is verified Successfully")
                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                this@SignUpActivity.finish()
            }

            override fun onFailure(exception: Exception) {
                // User confirmation failed. Check exception for the cause.
                Utils.toast(this@SignUpActivity, "Sign up fails ${exception.message}")
            }
        }
    }

    private fun signUp() {
        progressDialog.show()
        val email = etSignUpEmail.text.toString().trim()
        val password = etSignUpPass.text.toString().trim()
        val firstName = etSignUpFirstName.text.toString().trim()
        val lastName = etSignUpLastName.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                if (password.length > 8) {
                    if (firstName.isNotEmpty()) {
                        if (lastName.isNotEmpty()) {


                            // Create a CognitoUserAttributes object and add user attributes
                            val userAttributes = CognitoUserAttributes()

                            // Add the user attributes. Attributes are added as key-value pairs
                            // Adding user's given name.
                            // Note that the key is "given_name" which is the OIDC claim for given name
                            userAttributes.addAttribute("email", email)

                            // Adding user's phone number
                            userAttributes.addAttribute("custom:firstname", firstName)

                            // Adding user's email address

                            userAttributes.addAttribute("custom:lastname", lastName)


                            val signupCallback = object : SignUpHandler {

                                override fun onSuccess(cognitoUser: CognitoUser, userConfirmed: Boolean, cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails) {
                                    // Sign-up was successful
                                    Utils.toast(this@SignUpActivity, "Sign up Successfully")

                                    // Check if this user (cognitoUser) needs to be confirmed
                                    if (!userConfirmed) {
                                        // This user must be confirmed and a confirmation code was sent to the user
                                        // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                                        // Get the confirmation code from user
                                        Utils.toast(this@SignUpActivity, "Verification code is sent to your ${cognitoUserCodeDeliveryDetails.deliveryMedium}")

                                        showVerificationDialog(cognitoUser)

                                    } else {
                                        // The user has already been confirmed
                                        progressDialog.dismiss()
                                    }
                                }

                                override fun onFailure(exception: Exception) {
                                    // Sign-up failed, check exception for the cause
                                    progressDialog.dismiss()
                                    Utils.toast(this@SignUpActivity, "Sign up fails ${exception.message}")
                                }
                            }

                            Utils.getUserPool()?.signUpInBackground(email, password, userAttributes, null, signupCallback)

                        } else {
                            progressDialog.dismiss()
                            etSignUpLastName.error = "This Field can't be Empty"
                        }
                    } else {
                        progressDialog.dismiss()
                        etSignUpFirstName.error = "This Field can't be Empty"
                    }
                } else {
                    progressDialog.dismiss()
                    etSignUpPass.error = "Password Length must be more then 8 digits"
                }

            } else {
                progressDialog.dismiss()
                etSignUpPass.error = "This Field can't be Empty"
            }
        } else {
            progressDialog.dismiss()
            etSignUpEmail.error = "This Field can't be Empty"
        }
    }

    private fun showVerificationDialog(cognitoUser: CognitoUser) {
        progressDialog.dismiss()
        val view: View = LayoutInflater.from(this.applicationContext).inflate(R.layout.verify_sign_up_dialog, null)
        val alertBox = AlertDialog.Builder(this)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()

        val verificationCode: EditText = view.findViewById(R.id.et_verify_singUp_code)

        val btnVerify: Button = view.findViewById(R.id.btn_SignUp_Verify)
        btnVerify.setOnClickListener {
            dialog.dismiss()

            val forcedAliasCreation = false
            cognitoUser.confirmSignUpInBackground(verificationCode.text.toString().trim(), forcedAliasCreation, confirmationCallback)

        }

        dialog.show()
    }

    private fun initViews() {
        etSignUpEmail = findViewById(R.id.et_signUp_email)
        etSignUpPass = findViewById(R.id.et_signUp_pass)
        etSignUpFirstName = findViewById(R.id.et_signUp_firstName)
        etSignUpLastName = findViewById(R.id.et_signUp_lastName)
        progressDialog = SpotsDialog(this@SignUpActivity, "Creating Account Please Wait....", R.style.CustomDialog)
        Utils.init(this@SignUpActivity)
    }
}
