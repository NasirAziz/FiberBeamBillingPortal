package com.example.fiberbeamportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fiberbeamportal.databinding.ActivityAdminDashboardBinding

class AdminDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAddNewUser.setOnClickListener {
            val intent = Intent(this, AddNewUser::class.java)
            startActivity(intent)
        }
        binding.btnAddNewCustomer.setOnClickListener {
            val intent = Intent(this, AddCustomer::class.java)
            startActivity(intent)
        }
        binding.btnFreeCustomer.setOnClickListener {
            val intent = Intent(this, AddCustomer::class.java)
            startActivity(intent)
        }
        binding.btnViewBills.setOnClickListener {
            val intent = Intent(this, AddCustomer::class.java)//TODO
            startActivity(intent)
        }
    }
}