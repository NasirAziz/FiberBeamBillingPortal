package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.example.fiberbeamportal.adapter.PayBillAdapter
import com.example.fiberbeamportal.databinding.ActivityPayBillBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.Customer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class PayBill : AppCompatActivity() {
    private lateinit var binding: ActivityPayBillBinding
    var position1 =-1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPayBillBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val customer = MyFirebaseFirestore.customers
        val adapter = PayBillAdapter(this, customer)
        binding.rvPayBills.adapter = adapter

        adapter.setOnItemClickListener(object: PayBillAdapter.ClickListener {
            override fun onItemClick(view: View, position: Int) {
                binding.rvPayBills.visibility = View.GONE
                val layout = findViewById<View>(R.id.include)
                layout.visibility = View.VISIBLE
                position1 = position
                binding.include.dtCustomerNameUD.setText(customer[position].name)

            }

        })

        binding.include.btnCancel.setOnClickListener {
            binding.rvPayBills.visibility = View.VISIBLE
            val layout = findViewById<View>(R.id.include)
            layout.visibility = View.GONE

        }

        binding.include.btnPaynConfirm.setOnClickListener {
            if (binding.include.dtCustomerBillUD.text.toString() == customer[position1].bill){
                Toast.makeText(this,"Bill Paid",Toast.LENGTH_SHORT).show()

                val customer = customer[position1]
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(Date())
                customer.dateofconnection =  currentDate.toString()
                customer.status = "Paid"
                FirebaseFirestore.getInstance()
                    .collection("Customers")
                    .document(customer.phone)
                    .set(customer)

                binding.rvPayBills.visibility = View.VISIBLE
                val layout = findViewById<View>(R.id.include)
                layout.visibility = View.GONE
            }

        }
    }


}