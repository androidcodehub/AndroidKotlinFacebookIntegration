package com.androidcodehub.androidkotlinfacebookintegration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import org.json.JSONObject

/**
 * Created by gulgulu on 10-01-2018.
 */

class UserActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user)

        val intent = intent

        if (getIntent().extras != null) {

            val jsondata = intent.getStringExtra("userProfile")

            val user_name = findViewById<View>(R.id.UserName) as TextView
            val user_picture = findViewById<View>(R.id.pic) as ImageView
            val user_email = findViewById<View>(R.id.email) as TextView
            try {
                val response = JSONObject(jsondata)
                user_email.text = response.get("email").toString()
                user_name.text = response.get("name").toString()
                val profile_pic = JSONObject(response.get("picture").toString())

                val profile_url = JSONObject(profile_pic.getString("data"))
                Picasso.with(this).load(profile_url.getString("url"))
                        .into(user_picture)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
