package ca.tanle.mapluv.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.databinding.ActivityEditBinding
import ca.tanle.mapluv.databinding.ActivityMainBinding
import ca.tanle.mapluv.ui.fragments.AddressFragment
import ca.tanle.mapluv.ui.fragments.CoordinatesFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private lateinit var coordinatesViewModel: CoordinatesViewModel
    private lateinit var viewModel: PlacesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Call the action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        coordinatesViewModel = ViewModelProvider(this)[CoordinatesViewModel::class.java]

//        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)
//        val placeList = viewModel.allPlaces
        val bundle = intent.extras!!
        coordinatesViewModel.setCoordinate(LatLng(bundle.getDouble("lat"), bundle.getDouble("lng")))

        changeScreen(CoordinatesFragment())
        onOptionClicked()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun changeScreen(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView2, fragment)
        fragmentTransaction.commit()
    }

    private fun onOptionClicked(){
        binding.ivCoordinate.setOnClickListener{
            changeScreen(CoordinatesFragment())
            binding.ivCoordinate.setImageResource(R.drawable.add_marker_active)
            binding.ivAddress.setImageResource(R.drawable.add_current_inactive)
        }
        binding.ivAddress.setOnClickListener{
            changeScreen(AddressFragment())
            binding.ivCoordinate.setImageResource(R.drawable.add_marker_inactive)
            binding.ivAddress.setImageResource(R.drawable.add_current_active)
        }
    }
}