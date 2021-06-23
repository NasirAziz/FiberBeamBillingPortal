package com.example.fiberbeamportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fiberbeamportal.databinding.ActivityAdminBinding

class Admin : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    val ADMIN_NAME = "Haseeb"
    val PASSWORD = "Pakistan92"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Login"

        binding.btnAdminLogin.setOnClickListener {
            val name = binding.editTextTextPersonName3.text.toString()
            val pass = binding.editTextTextPassword.text.toString()
            if(name == ADMIN_NAME && pass == PASSWORD) {
                val intent = Intent(this, AdminDashboard::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}