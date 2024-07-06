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
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.databinding.FragmentMapBinding
import ca.tanle.mapluv.ui.activities.viewmodels.CoordinatesViewModel
import ca.tanle.mapluv.ui.activities.EditActivity
import ca.tanle.mapluv.utils.LocationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, OnMapClickListener, OnMarkerClickListener, OnMyLocationButtonClickListener{
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var locationUtils: LocationUtils
    private lateinit var coordinatesViewModel: CoordinatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        coordinatesViewModel = ViewModelProvider(requireActivity()).get(CoordinatesViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater)
        locationUtils = LocationUtils(requireActivity())

        val fragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        fragment.getMapAsync(this)

        return binding.root
    }

    @SuppressLint("MissingPermission")
    fun enableMapFeatures(){
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.let {
            it.isZoomControlsEnabled= true
            it.isMyLocationButtonEnabled= true
            it.isCompassEnabled = true
        }
        getDeviceLocation()

        // Change map type
        binding.mapTypeNormalBtn.setOnClickListener{
            binding.mapTypeNormalBtn.setImageResource(R.drawable.normal_map_active);
            binding.mapTypeSatelliteBtn.setImageResource(R.drawable.satellite_map_inactive);
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL;
        }

        binding.mapTypeSatelliteBtn.setOnClickListener{
            binding.mapTypeSatelliteBtn.setImageResource(R.drawable.satellite_map_active);
            binding.mapTypeNormalBtn.setImageResource(R.drawable.normal_map_inactive);
            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE;
        }

        // Handle add new fav place
        binding.addBtn.setOnClickListener{
            val coordinate = coordinatesViewModel.coordinate.value
            if(coordinate != null){
                val bundle = Bundle()
                bundle.putDouble("lat", coordinate.latitude)
                bundle.putDouble("lng", coordinate.longitude)
                val intent = Intent(requireActivity(), EditActivity::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            }
        }
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
                coordinatesViewModel.setCoordinate(location)
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

    override fun onMapClick(p0: LatLng) {
        val icon = BitmapDescriptorFactory.fromResource(R.drawable.marker)
        val markerOptions = MarkerOptions().position(p0).icon(icon).title("Tan Le")

        coordinatesViewModel.setCoordinate(p0)

        googleMap.clear()
        googleMap.addMarker(markerOptions)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(p0))
        googleMap.setOnMarkerClickListener(this)

    }

    override fun onMarkerClick(p0: Marker): Boolean {
        p0.remove();
        return  true
    }

    override fun onMyLocationButtonClick(): Boolean {
        getDeviceLocation()
        googleMap.clear()
        return true
    }
}