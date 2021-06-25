package com.example.fiberbeamportal.adapter

import com.example.fiberbeamportal.model.NewCustomer

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fiberbeamportal.R

class PayBillAdapter(val context: Context, private val customers: MutableList<NewCustomer>):
    RecyclerView.Adapter<PayBillAdapter.MyViewHolder>() {

    private var onClickListener: ClickListener? = null
    private var filteredCustomers: MutableList<NewCustomer> = customers

    interface ClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.onClickListener = clickListener
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { v ->
                if (v != null)
                    onClickListener?.onItemClick(v, adapterPosition)
            }
        }

        val name:TextView = view.findViewById(R.id.tvCustomerName)
        val phoneNo:TextView = view.findViewById(R.id.tvPhoneNo)
        val status:TextView = view.findViewById(R.id.tvStatus)
        val date:TextView = view.findViewById(R.id.tvDateOfBill)
        val bill:TextView = view.findViewById(R.id.tvPaymentBill)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_rv_pay_bill, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(filteredCustomers.isNotEmpty()) {
            val data = customers[position]
            holder.name.text = data.name
            holder.phoneNo.text = data.phone
            holder.status.text = data.status
            holder.date.text = data.dateofconnection
            holder.bill.text = data.bill
            if(data.status == "Paid")
                holder.status.setTextColor(Color.GREEN)
            else
                holder.status.setTextColor(Color.RED)

        }
    }

    override fun getItemCount() = customers.size


    /**
     *
     * Returns a filter that can be used to constrain data with a filtering
     * pattern.
     *
     *
     * This method is usually implemented by [Adapter]
     * classes.
     *
     * @return a filter used to constrain data
     */
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSequenceString = constraint.toString()
                //  Log.i("performFiltering","$constraint,, ")
                if (charSequenceString.isEmpty()) {
                    filteredCustomers = customers as MutableList<NewCustomer>
                    //  Log.i("performFiltering","$constraint,, isempty")
                } else {
                    //  Log.i("performFiltering","$constraint,,nope ")
                    val filteredList: MutableList<NewCustomer> = mutableListOf()
                    for (customer in customers) {
                        if (customer.name.contains(charSequenceString, true)) {
                            filteredList.add(customer)
                            Log.i("performFiltering", "$constraint,,$filteredList ")
                        }
                        //Log.i("performFiltering","$constraint,before, $filteredProducts")
                        filteredCustomers = filteredList
                        // Log.i("performFiltering","$constraint,after,$filteredProducts ")

                    }
                }
                val results = FilterResults()
                results.values = filteredCustomers
                //  Log.i("performFiltering","$constraint,result.values,${results.count} ")
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                filteredCustomers = results.values as MutableList<NewCustomer>
                //    Log.i("performFilteringresult", "$constraint,results.values, ${results.values}")

                notifyDataSetChanged()
            }
        }
    }

}