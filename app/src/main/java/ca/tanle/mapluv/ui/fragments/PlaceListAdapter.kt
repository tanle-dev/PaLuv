package ca.tanle.mapluv.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.Place

class PlaceListAdapter(val data: ArrayList<Place>): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view){
    val photo = view.findViewById<ImageView>(R.id.placePhoto)
    val placeName = view.findViewById<TextView>(R.id.placeName)
}