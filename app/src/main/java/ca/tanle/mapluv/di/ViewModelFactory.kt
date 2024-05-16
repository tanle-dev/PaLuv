package ca.tanle.mapluv.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.ui.activities.PlacesViewModel

class ViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlacesViewModel::class.java)){
            return PlacesViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}