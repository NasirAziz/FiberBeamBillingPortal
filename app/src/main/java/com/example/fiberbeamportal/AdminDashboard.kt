package com.example.fiberbeamportal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.fiberbeamportal.databinding.ActivityAdminDashboardBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.NewCustomer
import com.example.fiberbeamportal.model.NewUser
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDashboardBinding

    private fun getCustomers(context: Context) {
        var customer: NewCustomer?

       // Log.i("AdminDashboard","$paid")
        if(MyFirebaseFirestore.customers.isEmpty()) {
            FirebaseFirestore.getInstance().collection("Customers")
                .get()
                .addOnSuccessListener {
                    MyFirebaseFirestore.customers.removeAll{true}
                   // this.customers.removeAll{true}
                    for (document in it) {
                        customer = document.toObject<NewCustomer>(NewCustomer::class.java)
                        MyFirebaseFirestore.customers.add(customer!!)
                    }

                    updateUI()

                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Database connection failure please check your internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }else{
            updateUI()
        }
    }

    private fun updateUI() {
        var paid:Int = 0
        var unpaid:Int = 0

        val totalcustomer = MyFirebaseFirestore.customers.size.toString()
        val totaluser = MyFirebaseFirestore.users.size.toString()
        for (customer in MyFirebaseFirestore.customers) {
            if (customer.status == "Paid")
                paid++
            else
                unpaid++
        }
        binding.tvTotalCustomers.text = totalcustomer
        binding.tvTotalPaid.text = paid.toString()
        binding.tvTotalUnpaid.text = unpaid.toString()
        binding.tvTotalUser.text = totaluser
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.options_menu_admin_dashboard,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh->{
                MyFirebaseFirestore.getUsers(this)
                getCustomers(this)
                updateUI()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getUsers(this)
        getCustomers(this)


        binding.btnAddNewUser.setOnClickListener {
            val intent = Intent(this, AddNewUser::class.java)
            startActivity(intent)
        }
        binding.btnAddNewCustomer.setOnClickListener {
            val intent = Intent(this, AddCustomer::class.java)
            startActivity(intent)
        }
        binding.btnAddFreeCustomer.setOnClickListener {
            val intent = Intent(this, FreeCustomer::class.java)
            startActivity(intent)
        }
        binding.btnViewBills.setOnClickListener {
            val intent = Intent(this, PayBill::class.java)
            startActivity(intent)
        }
        binding.btnViewFreeCustomers.setOnClickListener {
            val intent = Intent(this, FreeCustomer::class.java)
            intent.putExtra("key","view")
            startActivity(intent)
        }
        binding.btnViewUsers.setOnClickListener {
            val intent = Intent(this, ShowUsersActivity::class.java)
            intent.putExtra("key","view")
            startActivity(intent)
        }
    }

    private fun getUsers(context:Context){
        var user: NewUser?
        MyFirebaseFirestore.database.collection("users")
            .get()
            .addOnSuccessListener {
                MyFirebaseFirestore.users.removeAll { true }
                for( document in it) {
                    user = document.toObject<NewUser>(NewUser::class.java)
                    MyFirebaseFirestore.users.add(user)
                }
                updateUI()
            }.addOnFailureListener {
                Toast.makeText(context,
                    "Database connection failure please check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }

    }

    override fun onResume() {
        super.onResume()
        getUsers(this)
        getCustomers(this)
        updateUI()
    }
}