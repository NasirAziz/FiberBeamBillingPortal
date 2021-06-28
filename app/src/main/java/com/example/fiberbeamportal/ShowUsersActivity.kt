package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fiberbeamportal.adapter.UsersAdapter
import com.example.fiberbeamportal.databinding.ActivityShowUsersBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore

class ShowUsersActivity : AppCompatActivity() {
    private lateinit var adapter:UsersAdapter
    private lateinit var binding:ActivityShowUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_users)


        val users = MyFirebaseFirestore.users
        if(users.isNotEmpty()) {
            adapter = UsersAdapter(this, users = users)
            val rv = findViewById<RecyclerView>(R.id.rvShowUsers)
            rv.adapter = adapter

            adapter.setOnItemClickListener(object : UsersAdapter.ClickListener{
                override fun onItemClick(view: View, position: Int) {
                    TODO("Not yet implemented")
                }

            })
        }else{
            Toast.makeText(this,"No users to show",Toast.LENGTH_SHORT).show()
        }


    }
}