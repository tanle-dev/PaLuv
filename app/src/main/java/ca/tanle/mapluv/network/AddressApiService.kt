package ca.tanle.mapluv.network

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.IOException
import java.util.Locale

class AddressApiService {

    fun getLocationAddress(context: Context, lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addressText = ""

        val address: List<Address>? = geocoder.getFromLocation(
            lat,
            lng,
            1,
        )

//        try {
//            val addresses: List<Address>? = geocoder.getFromLocation(
//                location.latitude.toDouble(),
//                location.longtitude.toDouble(),
//                1
//            )
//
//            if (!addresses.isNullOrEmpty()) {
//                val address = addresses[0]
//                val sb = StringBuilder()
//                for (i in 0..address.maxAddressLineIndex) {
//                    sb.append(address.getAddressLine(i)).append("\n")
//                }
//                addressText = sb.toString()
//            }
//        } catch (ioException: IOException) {
//            ioException.printStackTrace()
//        } catch (e: IllegalArgumentException) {
//            e.printStackTrace()
//        }


        return ""
    }
}