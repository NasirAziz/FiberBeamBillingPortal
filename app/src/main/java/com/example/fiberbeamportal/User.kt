package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore

class User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar?.title = "User Login"

    }
}