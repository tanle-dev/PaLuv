package ca.tanle.mapluv.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import ca.tanle.mapluv.R
import ca.tanle.mapluv.databinding.FragmentMapBinding
import ca.tanle.mapluv.utils.LocationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment(val acontext: Context) : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var locationUtils: LocationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        locationUtils = LocationUtils(acontext)


        val fragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        fragment.getMapAsync(this)

        binding.addBtn.setOnClickListener{
            Toast.makeText(activity, "Add Click Btn", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    @SuppressLint("MissingPermission")
    fun enableMapFeatures(){
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.let {
            it.isZoomControlsEnabled= true
            it.isMyLocationButtonEnabled= true
            it.isCompassEnabled = true
        }
        getDeviceLocation()
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        if(locationUtils.hasLocationPermission()){
            // enable some features for map
            enableMapFeatures()
            // Load data and markers to the map
        }else{
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(){
        if(locationUtils.hasLocationPermission()){
            locationUtils.fusedLocationClient.lastLocation.addOnSuccessListener {
                val location = LatLng(it.latitude, it.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
            }
        }
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){permission ->
        if(
            permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
            &&
            permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
        ){
            // enable some features for map
            enableMapFeatures()
            // Load data and add markers to map.
        }else{
            // Display toast about permission not granted.
            Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}