package com.example.fiberbeamportal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fiberbeamportal.R
import com.example.fiberbeamportal.model.NewUser

class UsersAdapter(val context: Context, private val users: MutableList<NewUser>):
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    private var onClickListener: ClickListener? = null
    //private var filteredCustomers: MutableList<NewCustomer> = users

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

        val name:TextView = view.findViewById(R.id.tvUserName)
        val phoneNo:TextView = view.findViewById(R.id.tvUserPhoneNo)
        val address:TextView = view.findViewById(R.id.tvUserAddress)
        val paf:TextView = view.findViewById(R.id.tvPAF)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_rv_show_users, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(users.isNotEmpty()) {
            val data = users[position]
            holder.name.text = data.name
            holder.phoneNo.text = data.phone
            holder.paf.text = data.isPAF.toString()//TODO
            holder.address.text = data.address
        }
    }

    override fun getItemCount() = users.size


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
/*
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
*/

}