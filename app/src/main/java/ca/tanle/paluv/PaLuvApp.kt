package ca.tanle.paluv

import android.app.Application
import ca.tanle.paluv.utils.Graph
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PaLuvApp: Application() {
    override fun onCreate() {
        Graph.provide(this)
        super.onCreate()
    }
}