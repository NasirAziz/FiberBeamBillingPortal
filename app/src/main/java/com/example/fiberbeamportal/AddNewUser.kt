package com.example.fiberbeamportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.fiberbeamportal.databinding.ActivityAddNewUserBinding
import com.example.fiberbeamportal.model.NewUser
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class AddNewUser : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Add New User"

        binding.btncreateuser.setOnClickListener {
            if (!validateFields()){

                val name =  binding.edtname.text.toString()
                val email =  binding.edtusername.text.toString()
                val phone =  binding.edtphone.text.toString()
                val password =  binding.edtpassword.text.toString()
                val address =  binding.edtadress.text.toString()
                val pflist: Spinner =binding.pfNopfUser
                ArrayAdapter.createFromResource(this,R.array.pf,android.R.layout.simple_spinner_item)
                        .also { adapter->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            pflist.adapter=adapter
                        }
                val user = NewUser(name, email, phone, password, address,pflist.selectedItem.toString())

                try{
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(email)
                        .set(user).addOnSuccessListener {
                            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed ${it.message}", Toast.LENGTH_SHORT).show()
                        }

                }catch(e:Exception){
                    Toast.makeText(this,
                        "Failed please check your internet connection.",
                        Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,
                    "Please Fill all fields",
                    Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun validateFields():Boolean {
        return binding.edtname.text.isNullOrEmpty() ||
                binding.edtadress.text.isNullOrEmpty() ||
                binding.edtpassword.text.isNullOrEmpty() ||
                binding.edtphone.text.isNullOrEmpty() ||
                binding.edtusername.text.isNullOrEmpty()
    }
}