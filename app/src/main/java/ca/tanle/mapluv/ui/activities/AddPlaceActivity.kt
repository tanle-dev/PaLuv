package ca.tanle.mapluv.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.ColumnInfo
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.databinding.ActivityAddPlaceBinding
import ca.tanle.mapluv.databinding.ActivityEditBinding
import ca.tanle.mapluv.network.AddressRepository
import ca.tanle.mapluv.network.RetrofitProvider

class AddPlaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddPlaceBinding
    private lateinit var viewModel: AddressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AddressViewModel::class.java]
        val addressRepository = AddressRepository()

        val bundle = intent.extras!!
        val latLng = bundle.getDouble("lat").toString()+","+bundle.getDouble("lng").toString()
        viewModel.getAddress(RetrofitProvider.retrofit, addressRepository, latLng)

        viewModel.address.observe(this){
            binding.addressTextView.text = it
            Log.d("AAA", it + "yftyfy")
        }

        binding.saveBtn.setOnClickListener() {
            val newPlace = Place(
                title = "Tan House",
                visited = true,
                lat = 0.0,
                lng = 0.0,
                address = "177 Britannia",
                comment = "",
                rate = 5,
                reminderTitle = "",
                reminderDate = "",
                reminderTime = ""
            )
            (viewModel as PlacesViewModel).addNewPlace(newPlace)
        }
        setUpLayout()
    }

    private fun setUpLayout() {
        binding.star1.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_plain_icon)
            binding.star3.setImageResource(R.drawable.star_plain_icon)
            binding.star4.setImageResource(R.drawable.star_plain_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)
        }

        binding.star2.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_plain_icon)
            binding.star4.setImageResource(R.drawable.star_plain_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)
        }

        binding.star3.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_filled_icon)
            binding.star4.setImageResource(R.drawable.star_plain_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)
        }

        binding.star4.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_filled_icon)
            binding.star4.setImageResource(R.drawable.star_filled_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)
        }

        binding.star5.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_filled_icon)
            binding.star4.setImageResource(R.drawable.star_filled_icon)
            binding.star5.setImageResource(R.drawable.star_filled_icon)
        }

        binding.removeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}