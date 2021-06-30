package com.example.fiberbeamportal

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fiberbeamportal.adapter.PayBillAdapter
import com.example.fiberbeamportal.databinding.ActivityPayBillBinding
import com.example.fiberbeamportal.firebase.MyFirebaseFirestore
import com.example.fiberbeamportal.model.NewCustomer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.core.UserData
import java.text.SimpleDateFormat
import java.util.*

class PayBill : AppCompatActivity() {

    private lateinit var binding: ActivityPayBillBinding
    var index =-1
    var customer: MutableList<NewCustomer> = mutableListOf()
    lateinit var adapter: PayBillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBillBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(MyFirebaseFirestore.customers.isEmpty()) {
            getCustomers(this)
            Log.i("PayBillActivity","MyFirebaseFirestore.customers.isEmpty")

        }
        else{
            Log.i("PayBillActivity","MyFirebaseFirestore.customers.isNotEmpty")
            customer.removeAll{ true }
            customer.addAll(MyFirebaseFirestore.customers)
            adapter = PayBillAdapter(this,customer)
            updateUI(adapter)
        }
        binding.include.btnCancel.setOnClickListener {
            binding.rvPayBills.visibility = View.VISIBLE
            val layout = findViewById<View>(R.id.include)
            layout.visibility = View.GONE

        }

        binding.include.btnPaynConfirm.setOnClickListener {
            if (binding.include.dtCustomerBillUD.text.toString() == customer[index].bill){

                val currentCustomer = customer[index]
                var phone = ""
//                for( i in currentCustomer.phone.indices)
//                    phone+=currentCustomer.phone[i]
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())
                currentCustomer.dateofconnection =  currentDate.toString()
                currentCustomer.status = "Paid"

                MyFirebaseFirestore.database
                    .collection("Customers")
                    .document(currentCustomer.phone)
                    .set(currentCustomer)
                    .addOnSuccessListener {
                        Toast.makeText(this,"Bill Paid",Toast.LENGTH_SHORT).show()
                        checkAndAskForPermission()
                        //sendSMS("03170508334")
                    }.addOnCompleteListener {
                        //updateUI when bill is paid
                        getCustomers(this)

                        binding.rvPayBills.visibility = View.VISIBLE
                        val layout = findViewById<View>(R.id.include)
                        layout.visibility = View.GONE

                       // MyFirebaseFirestore.database.terminate()
                    }
                //TODO review and test above listeners
            }else{
                Toast.makeText(this,
                    "Please enter valid bill amount.",
                    Toast.LENGTH_SHORT).show()
            }

        }

        binding.include.btnEditCustomer.setOnClickListener {
            val docIDPhone = customer[index].phone
            editUserInDatabase(this,docIDPhone)
        }
        binding.include.btnDeleteCustomer.setOnClickListener {
            val docIDPhone = customer[index].phone
            deleteUserFromDatabase(this,docIDPhone)
        }
    }
private fun checkAndAskForPermission(){
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.SEND_SMS), 1)
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.SEND_SMS), 1)
        }
    }
}



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    sendSMS(customer[index].phone)

                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(this,
                        "SMS did not send Permission denied.",
                        Toast.LENGTH_SHORT)
                        .show()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun sendSMS(phoneNo:String){
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val sm = SmsManager.getDefault()
        val msg = "Your fiber internet bill has been paid on $currentDate."
        sm.sendTextMessage(phoneNo, null, msg, null, null)
    }

    private fun updateUI(adapter: PayBillAdapter) {
//        customer.removeAll{ true }
//        customer.addAll(MyFirebaseFirestore.customers)

        binding.rvPayBills.adapter = adapter
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickListener(object: PayBillAdapter.ClickListener {
            override fun onItemClick(view: View, position: Int) {
                hideRecyclerView()
                index = position
                binding.include.dtCustomerNameUD.setText(customer[position].name)
                //binding.include.dtCustomerNameUD.isEnabled = false
                binding.include.dtCustomerBillUD.setText(customer[position].bill)
                binding.include.dtCustomerAddressUD.setText(customer[position].adress)
                binding.include.dtCustomerPhoneUD.setText(customer[position].phone)
                binding.include.dtCustomerPhoneUD.isEnabled = false
                binding.include.dtCustomerDateOfPaymentUD.setText(customer[position].dateofconnection)
                binding.include.dtCustomerDesignationUD.setText(customer[position].designation)
                binding.include.dtCustomerStatusUD.setText(customer[position].status)
                binding.include.dtCustomerStatusUD.isEnabled = false
                binding.include.dtCustomerPackageUD.setText(customer[position].pkg)
            }
        })
    }

    private fun getCustomers(context: Context){
        var customer: NewCustomer?
        FirebaseFirestore.getInstance()
            .collection("Customers")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                this.customer.removeAll{ true }
                MyFirebaseFirestore.customers.removeAll { true }
                for( document in it) {
                    customer = document.toObject<NewCustomer>(NewCustomer::class.java)
                    MyFirebaseFirestore.customers.add(customer!!)
                    this.customer.add(customer!!)
                }
                Log.i("PayBillActivity","after bill paid customers ${MyFirebaseFirestore.customers.size}")

            }.addOnFailureListener {
                Toast.makeText(context,
                    "Database connection failure please check your internet connection",
                    Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener {
                this.customer.removeAll{ true }
                this.customer.addAll(MyFirebaseFirestore.customers)
                adapter = PayBillAdapter(this, this.customer)
                updateUI(adapter)

            }
    }

    private fun editUserInDatabase(activity: PayBill, docId: String) {

        val name =      binding.include.dtCustomerNameUD.text.toString()
        val bill =     binding.include.dtCustomerBillUD.text.toString()
        val phone =     binding.include.dtCustomerPhoneUD.text.toString()
        val date =  binding.include.dtCustomerDateOfPaymentUD.text.toString()
        val address =   binding.include.dtCustomerAddressUD.text.toString()
        val designation =        binding.include.dtCustomerDesignationUD.text.toString()
        val status =   binding.include.dtCustomerStatusUD.text.toString()
        val pkg =        binding.include.dtCustomerPackageUD.text.toString()

        val customer = NewCustomer(name,designation,bill,date,address,phone,status,pkg)
        val customer2: MutableMap<String,Any> = mutableMapOf()
        customer2["phone"] = customer.phone
        customer2["name"] = customer.name
        customer2["adress"] = customer.adress
        customer2["dateofconnection"] = customer.dateofconnection
        customer2["bill"] = customer.bill
        customer2["pkg"] = customer.pkg
        customer2["status"] = customer.status
        customer2["designation"] = customer.designation
        MyFirebaseFirestore.database
            .collection("Customers")
            .document(docId)
            .update(customer2)
            .addOnSuccessListener {
                Toast.makeText(
                    activity,
                    "Customer Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                getCustomers(activity)
                showRecyclerView()
            }
            .addOnFailureListener {
                Toast.makeText(
                    activity,
                    "Error Unable To Update Customer ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
//        if(customer.phone == this.customer[index].phone) {
/*            MyFirebaseFirestore.database
                .collection("Customers")
                .document(docId)
                .set(customer)
                .addOnSuccessListener {
                    Toast.makeText(
                        activity,
                        "Customer Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    getCustomers(activity)
                    showRecyclerView()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        activity,
                        "Error Unable To Update Customer ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }*/
//        }
        //        else{
//           val customer2: MutableMap<String,Any> = mutableMapOf()
//            customer2["phone"] = customer.phone
//            customer2["name"] = customer.name
//            customer2["adress"] = customer.adress
//            customer2["dateofconnection"] = customer.dateofconnection
//            customer2["bill"] = customer.bill
//            customer2["pkg"] = customer.pkg
//            customer2["status"] = customer.status
//            customer2["designation"] = customer.designation
//            MyFirebaseFirestore.database
//                .collection("Customers")
//                .document(docId)
//                .update(customer2)
//                .addOnSuccessListener {
//                    Toast.makeText(
//                        activity,
//                        "Customer Updated Successfully else",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    getCustomers(activity)
//                    showRecyclerView()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(
//                        activity,
//                        "Error Unable To Update Customer else ${it.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//        }
    }

    private fun deleteUserFromDatabase(context: Context, docId: String) {
        MyFirebaseFirestore.database
            .collection("Customers")
            .document(docId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Customer Deleted Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                getCustomers(context)
                showRecyclerView()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Error Unable To Delete Customer ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun hideRecyclerView() {
        binding.rvPayBills.visibility = View.GONE
        val layout = findViewById<View>(R.id.include)
        layout.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        val layout = findViewById<View>(R.id.include)
        layout.visibility = View.GONE
        binding.rvPayBills.visibility = View.VISIBLE

    }

    override fun onBackPressed() {

        if(binding.rvPayBills.visibility == View.VISIBLE) {
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