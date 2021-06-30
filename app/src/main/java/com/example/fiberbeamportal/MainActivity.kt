package com.example.fiberbeamportal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.google.firebase.firestore.DocumentReference
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    init {

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val billResetDate = "${currentDate[0]}"+"${currentDate[1]}"

        if( billResetDate == "10"){
            resetCustomerBills()
        }
    }


    private fun resetCustomerBills() {
        val allCustomers: MutableList<DocumentReference> = mutableListOf()
        MyFirebaseFirestore.database.collection("Customers").get()
            .addOnSuccessListener { value ->

                if (value != null) {
                    for (doc in value)
                        allCustomers.add(doc.reference)

                    MyFirebaseFirestore.database.runBatch {
                        for (customer in allCustomers)
                            it.update(customer, "status", "Unpaid")
                    }
                } else {
                    Log.i("MainActivity", "query is NULL")
                }

            }.addOnFailureListener {
                Log.i("MainActivity", "Failed ${it.message} ")
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val admin: Button = findViewById(R.id.btn_admin)
        val sharedPref = getSharedPreferences(getString(R.string.shared_pref_name),Context.MODE_PRIVATE)

        admin.setOnClickListener {
            Log.i("MyActivityMain",sharedPref.getString(getString(R.string.admin_name),"null").toString())
            if(sharedPref.contains(getString(R.string.admin_name))) {
                val intent = Intent(this, AdminDashboard::class.java)
                startActivity(intent)
            }else{
                Log.i("MyActivityMaine",sharedPref.getString(getString(R.string.admin_name),"null").toString())

                val intent = Intent(this, Admin::class.java)
                startActivity(intent)
            }
        }

        val user:Button = findViewById(R.id.btn_user)
        user.setOnClickListener {
            if(sharedPref.contains(getString(R.string.user_name))) {
                val intent = Intent(this, UserDashboard::class.java)
                startActivity(intent)
            }else{
                Log.i("MyActivityMaine",sharedPref.getString(getString(R.string.user_name),"null").toString())

                val intent = Intent(this, User::class.java)
                startActivity(intent)
            }


        }

    }

//    companion object{
//        val sharedPref: SharedPreferences = MainActivity().getPreferences(Context.MODE_PRIVATE)
//
//    }
}