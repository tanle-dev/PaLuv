package ca.tanle.paluv.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.paluv.R
import ca.tanle.paluv.data.models.PlaceItem
import ca.tanle.paluv.utils.OnPlaceItemClickListener

class PlaceListAdapter(
    private val listener: OnPlaceItemClickListener,
    private var data: ArrayList<PlaceItem>
): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placeName.text = data[position].place.title

        if(data[position].photo != null){
            holder.photo.setImageBitmap(data[position].photo)
        }else{
            holder.photo.setImageResource(R.drawable.place_placeholder)
        }

        holder.itemView.setOnClickListener {
            listener.onPlaceItemClick(data[position])
        }
    }

    fun updateDataChanged(data: ArrayList<PlaceItem>){
        this.data = data
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view){
    val photo = view.findViewById<ImageView>(R.id.placePhoto)
    val placeName = view.findViewById<TextView>(R.id.placeName)
}