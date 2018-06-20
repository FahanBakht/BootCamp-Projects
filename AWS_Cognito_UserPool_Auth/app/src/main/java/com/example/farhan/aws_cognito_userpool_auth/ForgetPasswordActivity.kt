package com.example.farhan.aws_cognito_userpool_auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var etForgetPassEmail: EditText
    private lateinit var progressDialog: SpotsDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        etForgetPassEmail = findViewById(R.id.et_forgetPass_email)
        progressDialog = SpotsDialog(this@ForgetPasswordActivity, "Sending Verify Code To your Email....", R.style.CustomDialog)
        Utils.init(this@ForgetPasswordActivity)

        btn_forgetPass_send_verifyCode.setOnClickListener {

            val email = etForgetPassEmail.text.toString().trim()

            if (email.isNotEmpty()) {

                progressDialog.show()
                val handler = object : ForgotPasswordHandler {
                    override fun onSuccess() {
                        // Forgot password process completed successfully, new password has been successfully set
                        progressDialog.dismiss()
                        Utils.toast(this@ForgetPasswordActivity, "Your password has been change successfully")
                        startActivity(Intent(this@ForgetPasswordActivity, SignInActivity::class.java))
                        this@ForgetPasswordActivity.finish()
                    }

                    override fun getResetCode(continuation: ForgotPasswordContinuation) {
                        // A code will be sent, use the "continuation" object to continue with the forgot password process

                        // This will indicate where the code was sent
                        val codeSentHere = continuation.parameters
                        Utils.toast(this@ForgetPasswordActivity, "Verification code is sent to your $codeSentHere")
                        // Code to get the code from the user - user dialogs etc.

                        // If the program control has to exit this method, take the "continuation" object.
                        // "continuation" is the only possible way to continue with the process

                        // When the code is available
                        showVerificationDialog(continuation)
                    }

                    /**
                     * This is called for all fatal errors encountered during the password reset process
                     * Probe {@exception} for cause of this failure.
                     * @param exception
                     */
                    override fun onFailure(exception: Exception) {
                        // Forgot password processing failed, probe the exception for cause
                        progressDialog.dismiss()
                        Utils.toast(this@ForgetPasswordActivity, "Error While Forget Password ${exception.message}")
                    }
                }

                Utils.getUserPool()!!.getUser(email)?.forgotPasswordInBackground(handler)
            } else {
                etForgetPassEmail.error = "This field can't be empty"
            }
        }
    }

    private fun showVerificationDialog(continuation: ForgotPasswordContinuation) {
        progressDialog.dismiss()
        val view: View = LayoutInflater.from(this.applicationContext).inflate(R.layout.verify_forget_pass_dialog, null)
        val alertBox = AlertDialog.Builder(this)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()

        val newPassword: EditText = view.findViewById(R.id.et_forgetPass_verify_newPassword)
        val verificationCode: EditText = view.findViewById(R.id.et_forgetPass_verifyCode)

        val btnVerify: Button = view.findViewById(R.id.btn_verify_forgetPass)

        btnVerify.setOnClickListener {

            val password = newPassword.text.toString().trim()
            val verCode = verificationCode.text.toString().trim()

            if (password.isNotEmpty()) {
                if (password.length > 8) {
                    if (verCode.isNotEmpty()) {

                        // Set the new password
                        continuation.setPassword(password)

                        // Set the code to verify
                        continuation.setVerificationCode(verCode)

                        // Let the forgot password process proceed
                        continuation.continueTask()
                        dialog.dismiss()
                    } else {
                        verificationCode.error = "This field can't be empty"
                    }

                } else {
                    newPassword.error = "Password Length must be more then 8 digits"
                }

            } else {
                newPassword.error = "This field can't be empty"
            }

        }

        dialog.show()
    }
}
