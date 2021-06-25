package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.view.SupportActionModeWrapper
import com.example.fiberbeamportal.databinding.ActivityFreeCustomerBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.freecustomer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FreeCustomer : AppCompatActivity() {
    private lateinit var binding:ActivityFreeCustomerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title="Add Free Customer"
        binding= ActivityFreeCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnaddfreecustomer.setOnClickListener {
            val customer = freecustomer(binding.edtname.text.toString(),binding.edtdesignation.text.toString()
                    ,binding.edtdateofconnection.text.toString(),
                    binding.edtadress.text.toString(),
                    binding.edtphno.text.toString(),
                    "Free")

            MyFirebaseFirestore.database.collection("freeuser")
                    .document(binding.edtphno.text.toString())
                    .set(customer).addOnSuccessListener {
                        Toast.makeText(this,"Free Customer Added",Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener{
                        Toast.makeText(this,"Server Faild",Toast.LENGTH_SHORT).show()
                    }

        }



    }
}