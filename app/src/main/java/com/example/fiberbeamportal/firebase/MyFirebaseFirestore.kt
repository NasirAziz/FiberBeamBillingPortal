package com.example.fiberbeamportal.firebase

import android.content.Context
import android.widget.Toast
import com.example.fiberbeamportal.model.NewCustomer
import com.example.fiberbeamportal.model.NewUser
import com.google.firebase.firestore.FirebaseFirestore

class MyFirebaseFirestore {
    companion object{
         val users: MutableList<NewUser?> = mutableListOf()
         val customers: MutableList<NewCustomer> = mutableListOf()

        fun getUsers(context:Context){
            var user:NewUser?
            FirebaseFirestore.getInstance().collection("users")
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
        }

        fun getCustomers(context:Context){
            var customer: NewCustomer?
            FirebaseFirestore.getInstance().collection("Customers")
                .get()
                .addOnSuccessListener {
                    for( document in it) {
                        customer = document.toObject<NewCustomer>(NewCustomer::class.java)
                        customers.add(customer!!)
                    }
                }.addOnFailureListener {
                    Toast.makeText(context,
                        "Database connection failure please check your internet connection",
                        Toast.LENGTH_SHORT).show()
                }
        }
    }


}