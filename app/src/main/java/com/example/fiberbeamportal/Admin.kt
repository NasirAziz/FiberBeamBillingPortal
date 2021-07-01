package com.example.fiberbeamportal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fiberbeamportal.databinding.ActivityAdminBinding


class Admin : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    val ADMIN_NAME = "Delta.1992@yahoo.com"
    val PASSWORD = "freakingmaniac21"

    init {
    }
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

                val sharedPref = getSharedPreferences(getString(R.string.shared_pref_name),Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString(getString(R.string.admin_name), name)
                    apply()
                }
                Log.i("MyActivityAdmin",sharedPref.getString(getString(R.string.admin_name),"").toString())

                val intent = Intent(this, AdminDashboard::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Invalid username or password. Please try again",Toast.LENGTH_SHORT).show()
            }
        }
    }


}