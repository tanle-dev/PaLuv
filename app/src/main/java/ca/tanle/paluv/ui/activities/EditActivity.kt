package ca.tanle.paluv.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.tanle.paluv.R
import ca.tanle.paluv.databinding.ActivityEditBinding
import ca.tanle.paluv.ui.activities.viewmodels.CoordinatesViewModel
import ca.tanle.paluv.ui.fragments.addPlace.AddressFragment
import ca.tanle.paluv.ui.fragments.addPlace.CoordinatesFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private lateinit var coordinatesViewModel: CoordinatesViewModel
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