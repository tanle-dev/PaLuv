package ca.tanle.mapluv.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.ColumnInfo
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.databinding.ActivityAddPlaceBinding
import ca.tanle.mapluv.databinding.ActivityEditBinding
import ca.tanle.mapluv.network.AddressRepository
import ca.tanle.mapluv.network.RetrofitProvider
import ca.tanle.mapluv.utils.DatePickerFragment
import ca.tanle.mapluv.utils.IDate
import ca.tanle.mapluv.utils.ITime
import ca.tanle.mapluv.utils.TimePickerFragment

class AddPlaceActivity : AppCompatActivity(), IDate, ITime {
    lateinit var binding: ActivityAddPlaceBinding
    private lateinit var viewModel: AddressViewModel
    private lateinit var placeViewModel: PlacesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AddressViewModel::class.java]
        placeViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]

        val addressRepository = AddressRepository()

        val bundle = intent.extras!!
        val latLng = bundle.getDouble("lat").toString()+","+bundle.getDouble("lng").toString()
        placeViewModel.addLatLng(bundle.getDouble("lat"), bundle.getDouble("lng"))
        viewModel.getAddress(RetrofitProvider.retrofit, addressRepository, latLng)

        viewModel.address.observe(this){
            binding.addressTextView.text = it
            placeViewModel.addPlaceAddress(it)
        }

        binding.saveBtn.setOnClickListener() {
            Log.d("Place", placeViewModel.place.value.toString())
            placeViewModel.addNewPlace(placeViewModel.place.value!!)
        }
        setUpLayout()

        placeViewModel.getAllPlaces()

        placeViewModel.places.observe(this){
            Log.d("Place List", it.toString())
        }
    }

    private fun setUpLayout() {
        binding.placeNameEditText.addTextChangedListener(){
            placeViewModel.addPlaceName(it.toString())
        }

        binding.commentEditText.addTextChangedListener(){
            placeViewModel.addComment(it.toString())
        }

        binding.phoneEditText.addTextChangedListener(){
            placeViewModel.addPhoneNumber(it.toString())
        }

        binding.linkEditText.addTextChangedListener(){
            placeViewModel.addWebLink(it.toString())
        }

        binding.visitedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) placeViewModel.isVisited(true)
            else placeViewModel.isVisited(false)
        }

        binding.star1.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_plain_icon)
            binding.star3.setImageResource(R.drawable.star_plain_icon)
            binding.star4.setImageResource(R.drawable.star_plain_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)

            placeViewModel.ratingPlace(1)
        }

        binding.star2.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_plain_icon)
            binding.star4.setImageResource(R.drawable.star_plain_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)

            placeViewModel.ratingPlace(2)
        }

        binding.star3.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_filled_icon)
            binding.star4.setImageResource(R.drawable.star_plain_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)

            placeViewModel.ratingPlace(3)
        }

        binding.star4.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_filled_icon)
            binding.star4.setImageResource(R.drawable.star_filled_icon)
            binding.star5.setImageResource(R.drawable.star_plain_icon)

            placeViewModel.ratingPlace(4)
        }

        binding.star5.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_filled_icon)
            binding.star2.setImageResource(R.drawable.star_filled_icon)
            binding.star3.setImageResource(R.drawable.star_filled_icon)
            binding.star4.setImageResource(R.drawable.star_filled_icon)
            binding.star5.setImageResource(R.drawable.star_filled_icon)

            placeViewModel.ratingPlace(5)
        }



        binding.removeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

//        Set up reminder
        binding.reminderSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked) binding.reminderLayout.visibility = View.VISIBLE
            else binding.reminderLayout.visibility = View.GONE
        }

        binding.reminderTitleEditText.addTextChangedListener(){
            placeViewModel.addReTitle(it.toString())
        }

        binding.datePickerBtn.setOnClickListener{
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }

        binding.timePickerBtn.setOnClickListener{
            val newFragment = TimePickerFragment()
            newFragment.show(supportFragmentManager, "timePicker")
        }
    }

    override fun setDate(date: String) {
        binding.datePickerBtn.text = date
        placeViewModel.addReDate(date)
    }

    override fun setTime(time: String) {
        binding.timePickerBtn.text = time
        placeViewModel.addReTime(time)
    }
}