package com.example.fiberbeamportal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.UserDataReader
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

        admin.setOnClickListener {
            val intent = Intent(this, AdminDashboard::class.java)
            startActivity(intent)
        }

        val user:Button = findViewById(R.id.btn_user)
        user.setOnClickListener {
            val intent = Intent(this, Userdashboard::class.java)
            startActivity(intent)
        }

    }
}