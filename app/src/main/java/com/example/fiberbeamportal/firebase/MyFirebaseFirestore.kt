package com.example.fiberbeamportal.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.fiberbeamportal.model.NewCustomer
import com.example.fiberbeamportal.model.NewUser
import com.example.fiberbeamportal.model.FreeCustomer
import com.google.firebase.firestore.FirebaseFirestore

class MyFirebaseFirestore {
   private val _database = FirebaseFirestore.getInstance()

    companion object{
         val database get() = MyFirebaseFirestore()._database
         val users: MutableList<NewUser?> = mutableListOf()
         val customers: MutableList<NewCustomer> = mutableListOf()
         val FREE_CUSTOMERS: MutableList<FreeCustomer> = mutableListOf()

        fun getUsers(context:Context){
            var user:NewUser?
            database.collection("users")
                .get()
                .addOnSuccessListener {
                    for( document in it) {
                        user = document.toObject<NewUser>(NewUser::class.java)
                        users.add(user)
                    }
                }.addOnFailureListener {
                    Toast.makeText(context,
                        "Database connection failure please check your internet connection",
                        Toast.LENGTH_SHORT).show()
                }
            //database.terminate()
        }

    }

    fun updateCustomers(context: Context){
        var customer: NewCustomer?
        database.collection("Customers")
            .get()
            .addOnSuccessListener {
                customers.removeAll{ true }
                for( document in it) {
                    customer = document.toObject<NewCustomer>(NewCustomer::class.java)
                    customers.add(customer!!)
                }
                Log.i("UpdateCustomer","after bill paid customers ${customers.size}")

            }.addOnFailureListener {
                Toast.makeText(context,
                    "Database connection failure please check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }
    }
}