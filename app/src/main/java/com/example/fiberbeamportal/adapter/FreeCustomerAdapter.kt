package com.example.fiberbeamportal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fiberbeamportal.R
import com.example.fiberbeamportal.model.freecustomer

class FreeCustomerAdapter(val context: Context, private val customers:MutableList<freecustomer>):
    RecyclerView.Adapter<FreeCustomerAdapter.MyViewHolder>() {

    private var filteredCustomers: MutableList<freecustomer> = customers as MutableList<freecustomer>

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name:TextView = view.findViewById(R.id.tvCustomerName)
        val phoneno:TextView = view.findViewById(R.id.tvPhoneNo)
        val status:TextView = view.findViewById(R.id.tvStatus)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_rv_view_bill, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(filteredCustomers != null) {
            val data = filteredCustomers[position]
            holder.name.text = data.name
            holder.phoneno.text = data.phone
            holder.status.text = data.status

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
//    fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence): FilterResults {
//                val charSequenceString = constraint.toString()
//                //  Log.i("performFiltering","$constraint,, ")
//                if (charSequenceString.isEmpty()) {
//                    filteredCustomers = customers as MutableList<freecustomer>
//                    //  Log.i("performFiltering","$constraint,, isempty")
//                } else {
//                    //  Log.i("performFiltering","$constraint,,nope ")
//                    val filteredList: MutableList<Customer> = mutableListOf()
//                    for (product in customers) {
//                        if (product.name.contains(charSequenceString, true)) {
//                            filteredList.add(product)
//                            Log.i("performFiltering", "$constraint,,$filteredList ")
//                        }
//                        //Log.i("performFiltering","$constraint,before, $filteredProducts")
//                        filteredCustomers = filteredList
//                        // Log.i("performFiltering","$constraint,after,$filteredProducts ")
//
//                    }
//                }
//                val results = FilterResults()
//                results.values = filteredCustomers
//                //  Log.i("performFiltering","$constraint,result.values,${results.count} ")
//                return results
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
//                filteredCustomers = results.values as MutableList<freecustomer>
//                //    Log.i("performFilteringresult", "$constraint,results.values, ${results.values}")
//
//                notifyDataSetChanged()
//            }
//        }
    }

