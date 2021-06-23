package com.example.fiberbeamportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
           var isUserFound = false
           for (user in MyFirebaseFirestore.users) {
               val email = user?.email
               val password = user?.password

               if (binding.editTextTextPersonName3.text.toString() == email
                   && binding.editTextTextPassword.text.toString() == password) {
                   isUserFound = true
                   val intent = Intent(this, Userdashboard::class.java)
                   startActivity(intent)
                   finish()
               }
           }
            if(!isUserFound){
                Toast.makeText(this,"User not found!",Toast.LENGTH_SHORT).show()
            }
        }

    }
}