package ca.tanle.paluv.ui.fragments.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.paluv.R
import ca.tanle.paluv.data.models.Address
import ca.tanle.paluv.ui.activities.AddPlaceActivity

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

        holder.addNextBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", data[position].name)
            bundle.putString("address", data[position].formatted_address)
            bundle.putString("id", data[position].place_id)
            bundle.putString("mode", "add")
            bundle.putDouble("lat", data[position].geometry.location.lat)
            bundle.putDouble("lng", data[position].geometry.location.lng)

            val intent = Intent(it.context, AddPlaceActivity::class.java).apply {
                putExtras(bundle)
            }

            it.context.startActivity(intent)
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