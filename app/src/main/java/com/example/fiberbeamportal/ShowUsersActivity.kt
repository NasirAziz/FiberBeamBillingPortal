package com.example.fiberbeamportal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.fiberbeamportal.adapter.UsersAdapter
import com.example.fiberbeamportal.databinding.ActivityShowUsersBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.NewUser
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class ShowUsersActivity : AppCompatActivity() {


    private lateinit var adapter:UsersAdapter
    private lateinit var binding: ActivityShowUsersBinding
    private val users: MutableList<NewUser?> = mutableListOf()
    private var index by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        users.addAll(MyFirebaseFirestore.users)
        if(users.isNotEmpty()) {
            adapter = UsersAdapter(this, users)
            updateUI(adapter)

            binding.includeEditUser.btnDeleteUser.setOnClickListener {
                val docId = users[index]?.email
                if (docId != null) {
                    deleteUserFromDatabase(this, docId)
                }
            }
            binding.includeEditUser.btnEditUser.setOnClickListener {
                val docId = users[index]?.email
                if (docId != null) {
                    Log.i("ShowUsers",docId)
                    editUserFromDatabase(this, docId)
                }
            }
        }else{
            Toast.makeText(this,"No users to show",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(adapter: UsersAdapter) {
        binding.rvShowUsers.adapter = adapter
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickListener(object : UsersAdapter.ClickListener{
            override fun onItemClick(view: View, position: Int) {
                index = position
                showDataOfUser(users, index)
                hideRecyclerView()
            }

        })
    }

    private fun getUsers(context: Context){
        var user: NewUser?
        MyFirebaseFirestore.database
            .collection("users")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                users.removeAll{ true }
                MyFirebaseFirestore.users.removeAll{true}
                for( document in it) {
                    user = document.toObject<NewUser>(NewUser::class.java)
                    MyFirebaseFirestore.users.add(user!!)
                    users.add(user!!)
                }

            }.addOnFailureListener {
                Toast.makeText(context,
                    "Database connection failure please check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener {
                adapter = UsersAdapter(this, users)
                updateUI(adapter)

            }
    }

    private fun editUserFromDatabase(showUsersActivity: ShowUsersActivity, docId: String) {

        val name =  binding.includeEditUser.etUserName.text.toString()
        val email =  binding.includeEditUser.etUserEmailName.text.toString()
        val phone =  binding.includeEditUser.etUserPhone.text.toString()
        val password =  binding.includeEditUser.etUserPass.text.toString()
        val address =  binding.includeEditUser.etUserAddress.text.toString()
        val paf =  binding.includeEditUser.etUserPAF.text.toString()

        val user = NewUser(name,email,phone,password,address,paf)
        val user2: MutableMap<String,Any> = mutableMapOf()
        user2["phone"] = user.phone
        user2["name"] = user.name
        user2["email"] = user.email
        user2["address"] = user.address
        user2["password"] = user.password
        user2["isPaf"] = user.isPAF

        MyFirebaseFirestore.database
            .collection("users")
            .document(docId)
            .update(user2)
            .addOnSuccessListener {
                Toast.makeText(
                    showUsersActivity,
                    "User Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                getUsers(showUsersActivity)
                showRecyclerView()
            }
            .addOnFailureListener {
                Toast.makeText(
                    showUsersActivity,
                    "Error User Could Not Be Updated ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun deleteUserFromDatabase(context: Context, docId: String) {
        MyFirebaseFirestore.database
            .collection("users")
            .document(docId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "User Deleted Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                getUsers(context)
                showRecyclerView()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Error User Could Not Be Deleted ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun hideRecyclerView() {
        binding.rvShowUsers.visibility = View.GONE
        val layout = findViewById<View>(R.id.include_edit_user)
        layout.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        val layout = findViewById<View>(R.id.include_edit_user)
        layout.visibility = View.GONE
        binding.rvShowUsers.visibility = View.VISIBLE

    }


    private fun showDataOfUser(
        users: MutableList<NewUser?>,
        position: Int
    ) {
        binding.includeEditUser.etUserName.setText(users[position]?.name)
        binding.includeEditUser.etUserPhone.setText(users[position]?.phone)
        binding.includeEditUser.etUserAddress.setText(users[position]?.address)
        binding.includeEditUser.etUserEmailName.setText(users[position]?.email)
        binding.includeEditUser.etUserEmailName.isEnabled = false
        binding.includeEditUser.etUserPass.setText(users[position]?.password)
        binding.includeEditUser.etUserPAF.setText(users[position]?.isPAF)
    }

    override fun onBackPressed() {

        if(binding.rvShowUsers.visibility == View.VISIBLE) {
            finish()
        }
        else {
            onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showRecyclerView()
                }
            })
        }

        super.onBackPressed()
    }


}