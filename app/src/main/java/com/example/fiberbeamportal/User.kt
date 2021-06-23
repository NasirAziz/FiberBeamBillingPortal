package com.example.fiberbeamportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.fiberbeamportal.databinding.ActivityAddCustomerBinding
import com.example.fiberbeamportal.databinding.ActivityAddNewUserBinding
import com.example.fiberbeamportal.databinding.ActivityUserBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore

class User : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar?.title = "User Login"
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnuserLogin.setOnClickListener {

           for (user in MyFirebaseFirestore.users)
           {
               val email = user?.email
               val password = user?.password
               if (binding.editTextTextPersonName3.text.toString() == email && binding.editTextTextPassword.text.toString() == password) {
                   val intent = Intent(this, Userdashboard::class.java)
                   startActivity(intent)
                   finish()

               }
               // TODO
            }

        }

    }
}