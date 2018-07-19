package com.example.farhan.aws_cognito_userpool_auth

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.regions.Regions


class Utils {

    companion object {

        @SuppressLint("StaticFieldLeak")
        var cognitoUserPool: CognitoUserPool? = null
        var user: String = ""
        fun init(context: Context) {

            val USER_POOL_ID = "Your User Pool Id"
            val APP_CLIENT_ID = "Your user app client id"
            val COGNITO_REGION = You region
            val APP_SECRET = null

            var utils: Utils? = null

            if (utils != null && cognitoUserPool != null) {
                return
            }

            if (utils == null) {
                utils = Utils()
            }

            if (cognitoUserPool == null) {

                // Create a user pool with default ClientConfiguration
                cognitoUserPool = CognitoUserPool(context, USER_POOL_ID, APP_CLIENT_ID, APP_SECRET, COGNITO_REGION)

            }
        }

        /*fun clearUserPool() {
            cognitoUserPool = null
        }

        fun getCurrentUser(): String {

            user = cognitoUserPool!!.currentUser.userId
            return user
        }*/

        fun getUserPool(): CognitoUserPool? {
            return cognitoUserPool
        }

        fun toast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

    }
}
