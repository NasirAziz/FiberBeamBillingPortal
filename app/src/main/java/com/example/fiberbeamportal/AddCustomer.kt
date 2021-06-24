package com.example.fiberbeamportal

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fiberbeamportal.databinding.ActivityAddCustomerBinding

class AddCustomer : AppCompatActivity() {
    private  lateinit var binding: ActivityAddCustomerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)



    }
    private fun validate():Boolean{
        return binding.edtname.text.toString().isNullOrEmpty() &&
                binding.edtadress.text.toString().isNullOrEmpty()&&
                binding.edtbill.text.toString().isNullOrEmpty()&&
                binding.edtdesignation.text.toString().isNullOrEmpty() &&
                binding.edtphno.text.toString().isNullOrEmpty()

    }
}