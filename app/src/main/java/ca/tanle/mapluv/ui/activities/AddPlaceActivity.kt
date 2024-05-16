package ca.tanle.mapluv.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.ColumnInfo
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.databinding.ActivityAddPlaceBinding
import ca.tanle.mapluv.databinding.ActivityEditBinding

class AddPlaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddPlaceBinding
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        val toolbar: Toolbar = binding.toolbar;
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

        binding.saveBtn.setOnClickListener(){
            val newPlace = Place(title = "Tan House", visited = true, lat = 0.0, lng = 0.0, address =  "177 Britannia", comment = "", rate =  5, reminderTitle =  "", reminderDate = "", reminderTime = "")
            (viewModel as PlacesViewModel).addNewPlace(newPlace)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}