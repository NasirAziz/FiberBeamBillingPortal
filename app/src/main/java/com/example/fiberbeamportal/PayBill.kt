package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.fiberbeamportal.adapter.PayBillAdapter
import com.example.fiberbeamportal.databinding.ActivityPayBillBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore

class PayBill : AppCompatActivity() {
    private lateinit var binding: ActivityPayBillBinding
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
                binding.include.dtCustomerNameUD.setText(customer[position].name)
                binding.include.dtCustomerDateOfPaymentUD.setText(customer[position].dateofconnection)
                binding.include.dtCustomerBillUD.setText(customer[position].bill)

            }

        })

        binding.include.btnCancel.setOnClickListener {
            binding.rvPayBills.visibility = View.VISIBLE
            val layout = findViewById<View>(R.id.include)
            layout.visibility = View.GONE

        }

        binding.include.btnPaynConfirm.setOnClickListener {
            //TODO
            binding.rvPayBills.visibility = View.VISIBLE
            val layout = findViewById<View>(R.id.include)
            layout.visibility = View.GONE

        }
    }


}