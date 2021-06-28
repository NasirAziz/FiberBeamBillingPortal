package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.fiberbeamportal.adapter.UsersAdapter
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore

class ShowUsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_users)

        val users = MyFirebaseFirestore.users
        val adapter = UsersAdapter(this, users = users)
        val rv = findViewById<RecyclerView>(R.id.rvShowUsers)
        rv.adapter = adapter
    }
}