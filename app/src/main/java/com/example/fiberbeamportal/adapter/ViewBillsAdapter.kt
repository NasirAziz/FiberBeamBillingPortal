package com.example.fiberbeamportal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fiberbeamportal.R
import com.example.fiberbeamportal.model.Customer

class ViewBillsAdapter(val context: Context, private val customers:List<Customer>):
    RecyclerView.Adapter<ViewBillsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name:TextView = view.findViewById(R.id.tvCustomerName)
        val refNo:TextView = view.findViewById(R.id.tvRefNo)
        val status:TextView = view.findViewById(R.id.tvStatus)
        val date:TextView = view.findViewById(R.id.tvDateOfBill)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_rv_bill, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(customers != null) {
            val data = customers[position]
            holder.name.text = data.name
            holder.refNo.text = data.refNo
            holder.status.text = data.status
            holder.date.text = data.dateOfBill
        }
    }

    override fun getItemCount() = customers.size
}