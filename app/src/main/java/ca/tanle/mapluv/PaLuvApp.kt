package ca.tanle.mapluv

import android.app.Application
import android.util.Log
import ca.tanle.mapluv.utils.Graph
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PaLuvApp: Application() {
    override fun onCreate() {

        Log.d("AAA", "Hi Tan")
        super.onCreate()
    }
}