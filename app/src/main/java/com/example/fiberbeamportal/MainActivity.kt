package com.example.fiberbeamportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val admin: Button = findViewById(R.id.btn_admin)

        MyFirebaseFirestore.getUserCredentials(this)

        admin.setOnClickListener {
            val intent = Intent(this,AdminDashboard::class.java)
            startActivity(intent)
        }

        val user:Button = findViewById(R.id.btn_user)
        user.setOnClickListener {
            val intent = Intent(this,User::class.java)
            startActivity(intent)
        }
    }
}