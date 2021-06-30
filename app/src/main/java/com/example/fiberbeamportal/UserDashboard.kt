package com.example.fiberbeamportal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.fiberbeamportal.databinding.ActivityUserdashboardBinding

class UserDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityUserdashboardBinding
    private lateinit var sharedPref:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserdashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)

        binding.btnAddCustomer.setOnClickListener {
            val intent = Intent (this,AddCustomer::class.java )
            startActivity(intent)

        }
        binding.btnPaybill.setOnClickListener {
            val intent = Intent (this,PayBill::class.java )
            startActivity(intent)

        }
        binding.btnAddFreeCustomer.setOnClickListener {
            val intent = Intent (this,FreeCustomer::class.java )
            startActivity(intent)

        }
        val name2 = sharedPref.getString(getString(R.string.user_name),"")
        val address2 = sharedPref.getString(getString(R.string.user_address),"")

//        val name=intent.getStringExtra("name")
//        val adress=intent.getStringExtra("adress")
        binding.tvUserName.text=name2
        binding.tvAddress.text = address2

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_user_dashboard,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.user_logout->{
                Log.i("MyActivityDash",sharedPref.getString(getString(R.string.user_name),"").toString())

                sharedPref.edit()
                    .remove(getString(R.string.user_name))
                    .apply()

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}