package com.example.fiberbeamportal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fiberbeamportal.databinding.ActivityUserBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore

class User : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    init {
        MyFirebaseFirestore.getUsers(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar?.title = "User Login"
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnUserLogin.setOnClickListener {
           var isUserFound = false

           for (user in MyFirebaseFirestore.users) {
               val email = user?.email
               val password = user?.password
               val name:String
               val address: String

               if (binding.editTextTextPersonName3.text.toString() == email
                   && binding.editTextTextPassword.text.toString() == password) {

                   isUserFound = true
                   name=user.name
                   address=user.address

                   val sharedPref = getSharedPreferences(getString(R.string.shared_pref_name),
                       Context.MODE_PRIVATE)
                   with (sharedPref.edit()) {
                       putString(getString(R.string.user_name), name)
                       putString(getString(R.string.user_address), address)
                       apply()
                   }
                   Log.i("MyActivityAdmin",sharedPref.getString(getString(R.string.user_name),"").toString())
                   val intent = Intent(this, UserDashboard::class.java)
                   intent.putExtra("name" ,name)
                   intent.putExtra("adress" ,address)

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