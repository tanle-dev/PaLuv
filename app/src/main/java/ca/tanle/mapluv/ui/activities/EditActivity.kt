package ca.tanle.mapluv.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.databinding.ActivityEditBinding
import ca.tanle.mapluv.databinding.ActivityMainBinding
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

//        val coordinate = intent.extras
        coordinatesViewModel = ViewModelProvider(this)[CoordinatesViewModel::class.java]
        val coordinate = coordinatesViewModel.coordinate

        binding.lat.text = coordinate.value?.latitude.toString()
        binding.lng.text = coordinate.value?.longitude.toString()

        binding.addPlaceBtn.setOnClickListener{
            val intent = Intent(this, AddPlaceActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)
        val placeList = viewModel.allPlaces

        placeList.observe(this){places ->
            binding.textView2.text = places.size.toString()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}