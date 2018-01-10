package com.androidcodehub.androidkotlinfacebookintegration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    internal lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callbackManager = CallbackManager.Factory.create()
        val loginButton = findViewById<View>(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                getUserDetails(loginResult)
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    protected fun getUserDetails(loginResult: LoginResult) {
        val data_request = GraphRequest.newMeRequest(
                loginResult.accessToken) { json_object, response ->
            val intent = Intent(this@MainActivity, UserActivity::class.java)
            intent.putExtra("userProfile", json_object.toString())
            startActivity(intent)
        }
        val permission_param = Bundle()
        permission_param.putString("fields", "id,name,email,picture.width(140).height(140)")
        data_request.parameters = permission_param
        data_request.executeAsync()

    }

    override fun onResume() {
        super.onResume()
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this)
    }

    override fun onPause() {
        super.onPause()
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this)
    }
}
