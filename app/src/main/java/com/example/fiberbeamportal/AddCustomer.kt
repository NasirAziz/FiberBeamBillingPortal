package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fiberbeamportal.databinding.ActivityAddCustomerBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.NewCustomer
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddCustomer : AppCompatActivity() {
    private  lateinit var binding: ActivityAddCustomerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.btncreatecustomer.setOnClickListener {

            if (validate()) {
                Toast.makeText(this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show()
            } else {
                val name = binding.edtname.text.toString()
                val address = binding.edtadress.text.toString()
                val designation = binding.edtdesignation.text.toString()
                val bill = binding.edtbill.text.toString()
                val phno = binding.edtphno.text.toString()
                val dateOfConnection = binding.edtdateofconnection.text.toString()

                val customer:NewCustomer

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())
                val halfMonthDate = 15
                val currentDay = "${currentDate[0]}"+"${currentDate[1]}"

                customer = if(currentDay.toInt() < halfMonthDate)
                    NewCustomer(name,designation,bill,dateOfConnection,address,phno,"Unpaid")
                else
                    NewCustomer(name,designation,bill,dateOfConnection,address,phno,"Paid")
                //TODO review Above added code logic
                try {
                    FirebaseFirestore.getInstance().collection("Customers").document(phno)
                            .set(customer).addOnSuccessListener {
                                Toast.makeText(this, "Customer is created", Toast.LENGTH_SHORT).show()
                                finish()
                            MyFirebaseFirestore().updateCustomers(this)
                            }.addOnFailureListener {
                                Toast.makeText(this, "Network Or Other issue", Toast.LENGTH_SHORT).show()
                            }
                } catch (e: Exception) {
                    Toast.makeText(this, "Internet Connection or Server Error", Toast.LENGTH_SHORT).show()
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