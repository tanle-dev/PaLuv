package ca.tanle.mapluv.ui.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.remote.Address

class SearchAddressAdapter(var data: ArrayList<Address>): RecyclerView.Adapter<SearchAddVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAddVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_address_item, parent, false)

        return SearchAddVH(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchAddVH, position: Int) {
        val name = data[position].name
        holder.nameTV.text = name
        holder.addDetailTV.text = data[position].formatted_address

        Log.d("size", data[position].toString())

        holder.addNextBtn.setOnClickListener {
            Log.d("Click", "111111")
        }
    }

    fun updateDataSet(data: ArrayList<Address>){
        this.data = data
        notifyDataSetChanged()
    }
}

class SearchAddVH(view: View): RecyclerView.ViewHolder(view){
    val nameTV = view.findViewById<TextView>(R.id.addressNameTV)
    val addDetailTV = view.findViewById<TextView>(R.id.addressDetailTV)
    val addNextBtn = view.findViewById<ImageButton>(R.id.addNextBtn)
}