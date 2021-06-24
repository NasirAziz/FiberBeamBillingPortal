package com.example.fiberbeamportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fiberbeamportal.databinding.ActivityAdminDashboardBinding
import com.example.fiberbeamportal.databinding.ActivityUserBinding
import com.example.fiberbeamportal.databinding.ActivityUserdashboardBinding

class Userdashboard : AppCompatActivity() {
    private lateinit var binding: ActivityUserdashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdashboard)
        binding = ActivityUserdashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnAddCustomer.setOnClickListener {
            val intent = Intent (this,AddCustomer::class.java )
            startActivity(intent)

        }
        binding.btnPaybill.setOnClickListener {
            val intent = Intent (this,PayBill::class.java )
            startActivity(intent)

        }

    }
}