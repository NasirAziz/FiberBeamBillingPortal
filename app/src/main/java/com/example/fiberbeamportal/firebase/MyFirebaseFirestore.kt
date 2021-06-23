package com.example.fiberbeamportal.firebase

import android.content.Context
import android.widget.Toast
import com.example.fiberbeamportal.model.NewUser
import com.google.firebase.firestore.FirebaseFirestore

class MyFirebaseFirestore {
    companion object{
         val users: MutableList<NewUser?> = mutableListOf()

        fun getUserCredentials(context:Context){
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
    }


}