package ca.tanle.mapluv.utils

import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.models.PlaceItem

interface OnPlaceItemClickListener{
    fun onPlaceItemClick(placeItem: PlaceItem)
}

interface OnRemoveItemUpdateListener{
    fun onPlaceItemRemove()
}