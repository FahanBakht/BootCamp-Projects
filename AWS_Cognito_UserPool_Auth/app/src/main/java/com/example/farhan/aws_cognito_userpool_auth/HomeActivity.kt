package com.example.farhan.aws_cognito_userpool_auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler


class HomeActivity : AppCompatActivity() {

    var tvHomeUserEmail: TextView? = null
    var tvHomeFirstName: TextView? = null
    var tvHomeLastName: TextView? = null
    var tvHomeStatus: TextView? = null

    lateinit var dataArrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        loadDate()

    }

  /*  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemThatWasClickedId = item!!.itemId
        if (itemThatWasClickedId == R.id.home_logout) {
            // This has cleared all tokens and this user will have to go through the authentication process to get tokens.
            Utils.getUserPool()?.currentUser!!.signOut()
            Utils.clearUserPool()
            startActivity(Intent(this@HomeActivity, SignInActivity::class.java))
            this@HomeActivity.finish()
        }
        return super.onOptionsItemSelected(item)
    }
*/
    private fun loadDate() {

        // Implement callback handler for getting details
        val getDetailsHandler = object : GetDetailsHandler {
            override fun onSuccess(cognitoUserDetails: CognitoUserDetails) {
                // The user detail are in cognitoUserDetails
                Utils.toast(this@HomeActivity, "User Details Found Successfully")

                for ((k, v) in cognitoUserDetails.attributes.attributes) {
                    println("value of K" + k.toString())
                    println("value of V" + v.toString())
                    println("----------")
                    dataArrayList.add(v)

                }
                tvHomeUserEmail?.text = dataArrayList[0]
                tvHomeFirstName?.text = dataArrayList[1]
                tvHomeLastName?.text = dataArrayList[3]
                tvHomeStatus?.text = dataArrayList[4]

            }

            override fun onFailure(exception: Exception) {
                // Fetch user details failed, check exception for the cause
                Utils.toast(this@HomeActivity, "Error While getting User details ${exception.message}")
            }
        }

        // Fetch the user details
        Utils.getUserPool()?.currentUser?.getDetailsInBackground(getDetailsHandler)
    }

    private fun initView() {
        tvHomeUserEmail = findViewById(R.id.tv_home_userName)
        tvHomeFirstName = findViewById(R.id.tv_home_firstName)
        tvHomeLastName = findViewById(R.id.tv_home_lastName)
        tvHomeStatus = findViewById(R.id.tv_home_status)
        dataArrayList = arrayListOf()
        Utils.init(this@HomeActivity)
    }
}
