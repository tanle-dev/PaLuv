package ca.tanle.paluv.utils

import ca.tanle.paluv.data.models.PlaceItem

interface OnPlaceItemClickListener{
    fun onPlaceItemClick(placeItem: PlaceItem)
}

interface OnRemoveItemUpdateListener{
    fun onPlaceItemRemove()
}