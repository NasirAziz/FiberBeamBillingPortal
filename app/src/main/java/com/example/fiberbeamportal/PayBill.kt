package com.example.fiberbeamportal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.fiberbeamportal.adapter.PayBillAdapter
import com.example.fiberbeamportal.databinding.ActivityPayBillBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.NewCustomer
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class PayBill : AppCompatActivity() {

    private lateinit var binding: ActivityPayBillBinding
    var position1 =-1
    var customer: MutableList<NewCustomer> = mutableListOf()
    lateinit var adapter: PayBillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBillBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(MyFirebaseFirestore.customers.isEmpty()) {
            getCustomers(this)
            Log.i("PayBillActivity","MyFirebaseFirestore.customers.isEmpty")

        }
        else{
            Log.i("PayBillActivity","MyFirebaseFirestore.customers.isNotEmpty")
            customer.removeAll{ true }

            customer.addAll(MyFirebaseFirestore.customers)
            adapter = PayBillAdapter(this,customer)
            updateUI(adapter)
        }
        binding.include.btnCancel.setOnClickListener {
            binding.rvPayBills.visibility = View.VISIBLE
            val layout = findViewById<View>(R.id.include)
            layout.visibility = View.GONE

        }

        binding.include.btnPaynConfirm.setOnClickListener {
            if (binding.include.dtCustomerBillUD.text.toString() == customer[position1].bill){

                val currentCustomer = customer[position1]
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())
                currentCustomer.dateofconnection =  currentDate.toString()
                currentCustomer.status = "Paid"

                MyFirebaseFirestore.database
                    .collection("Customers")
                    .document(currentCustomer.phone)
                    .set(currentCustomer)
                    .addOnSuccessListener {
                        Toast.makeText(this,"Bill Paid",Toast.LENGTH_SHORT).show()

                    }.addOnCompleteListener {
                        //updateUI when bill is paid
                        getCustomers(this)

                        binding.rvPayBills.visibility = View.VISIBLE
                        val layout = findViewById<View>(R.id.include)
                        layout.visibility = View.GONE

                       // MyFirebaseFirestore.database.terminate()
                    }
                //TODO review and test above listeners
            }else{
                Toast.makeText(this,
                    "Please enter valid bill amount.",
                    Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun updateUI(adapter: PayBillAdapter) {
        binding.rvPayBills.adapter = adapter
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickListener(object: PayBillAdapter.ClickListener {
            override fun onItemClick(view: View, position: Int) {
                binding.rvPayBills.visibility = View.GONE
                val layout = findViewById<View>(R.id.include)
                layout.visibility = View.VISIBLE
                position1 = position
                binding.include.dtCustomerNameUD.setText(customer[position].name)
                binding.include.dtCustomerNameUD.isEnabled = false

            }

        })

    }

    fun getCustomers(context: Context){
        var customer: NewCustomer?
        FirebaseFirestore.getInstance().collection("Customers")
            .get()
            .addOnSuccessListener {
                this.customer.removeAll{ true }
                for( document in it) {
                    customer = document.toObject<NewCustomer>(NewCustomer::class.java)
                    MyFirebaseFirestore.customers.add(customer!!)
                    this.customer.add(customer!!)
                }
                Log.i("PayBillActivity","after bill paid customers ${this.customer.size}")

            }.addOnFailureListener {
                Toast.makeText(context,
                    "Database connection failure please check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener {
                adapter = PayBillAdapter(this, this.customer)
                updateUI(adapter)

            }
    }

}