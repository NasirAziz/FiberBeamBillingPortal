package com.example.fiberbeamportal

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fiberbeamportal.databinding.ActivityAddCustomerBinding
import com.example.fiberbeamportal.model.NewCustomer
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class AddCustomer : AppCompatActivity() {
    private  lateinit var binding: ActivityAddCustomerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        binding.btncreatecustomer.setOnClickListener {

            if (validate()) {
                Toast.makeText(this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show()
            } else {
                val name = binding.edtname.text.toString()
                val adress = binding.edtname.text.toString()
                val designation = binding.edtdesignation.text.toString()
                val bill = binding.edtbill.text.toString()
                val phno = binding.edtphno.text.toString()
                val dateofconnection = binding.edtdateofconnection.text.toString()
                val customer = NewCustomer(name, adress, designation, bill, phno, dateofconnection)
                try {
                    FirebaseFirestore.getInstance().collection("Customers").document(phno)
                            .set(customer).addOnSuccessListener {
                                Toast.makeText(this, "Customer is created", Toast.LENGTH_SHORT).show()
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(this, "Network Or Other issue", Toast.LENGTH_SHORT).show()
                            }
                } catch (e: Exception) {
                    Toast.makeText(this, "Internet Connection Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validate():Boolean{
        return binding.edtname.text.toString().isNullOrEmpty() ||
                binding.edtadress.text.toString().isNullOrEmpty()||
                binding.edtbill.text.toString().isNullOrEmpty()||
                binding.edtdesignation.text.toString().isNullOrEmpty() ||
                binding.edtphno.text.toString().isNullOrEmpty() ||
                binding.edtdateofconnection.text.toString().isNullOrEmpty()

    }
}