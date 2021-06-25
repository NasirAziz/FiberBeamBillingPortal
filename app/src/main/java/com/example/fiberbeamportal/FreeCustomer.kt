package com.example.fiberbeamportal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.SupportActionModeWrapper
import com.example.fiberbeamportal.adapter.FreeCustomerAdapter
import com.example.fiberbeamportal.adapter.PayBillAdapter
import com.example.fiberbeamportal.databinding.ActivityFreeCustomerBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.NewCustomer
import com.example.fiberbeamportal.model.freecustomer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FreeCustomer : AppCompatActivity() {

    private lateinit var binding:ActivityFreeCustomerBinding
    private val freeCustomers:MutableList<freecustomer> = mutableListOf()
    lateinit var adapter: FreeCustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title="Add Free Customer"
        binding= ActivityFreeCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(intent.hasExtra("key")){
            binding.freeCustomerLl.visibility = View.GONE
            val layout = findViewById<View>(R.id.free_rv)
            layout.visibility = View.VISIBLE

            getFreeCustomers(this)
        }

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
                        Toast.makeText(this,"Server Failed",Toast.LENGTH_SHORT).show()
                    }

        }



    }

    private fun getFreeCustomers(context: Context) {
        var freeCustomer: freecustomer?
        FirebaseFirestore.getInstance().collection("freeuser")
            .get()
            .addOnSuccessListener {

                for (document in it) {
                    freeCustomer = document.toObject<freecustomer>(freecustomer::class.java)
                    MyFirebaseFirestore.freeCustomers.add(freeCustomer!!)
                }
                Log.i("FreeCustomer","$freeCustomers")
                freeCustomers.addAll(MyFirebaseFirestore.freeCustomers)
                Log.i("FreeCustomer","$freeCustomers 22")
                adapter = FreeCustomerAdapter(this, freeCustomers)
                updateUI(adapter)


            }.addOnFailureListener {
                Toast.makeText(context,
                    "Database connection failure please check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUI(adapter: FreeCustomerAdapter) {
        binding.freeRv.rvViewFreeBills.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}