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
import ca.tanle.mapluv.network.IAddressRepository
import ca.tanle.mapluv.network.RetrofitProvider
import ca.tanle.mapluv.utils.DatePickerFragment
import ca.tanle.mapluv.utils.IDate
import ca.tanle.mapluv.utils.ITime
import ca.tanle.mapluv.utils.TimePickerFragment

class AddPlaceActivity : AppCompatActivity(), IDate, ITime {
    lateinit var binding: ActivityAddPlaceBinding
    private lateinit var viewModel: AddressViewModel
    private lateinit var placeViewModel: PlacesViewModel
    private lateinit var addressRepository: IAddressRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AddressViewModel::class.java]
        placeViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]

        addressRepository = AddressRepository()

        val bundle = intent.extras!!
        val mode = bundle.getString("mode")

        if(mode == "cdn"){
            val latLng = bundle.getDouble("lat").toString()+","+bundle.getDouble("lng").toString()
            placeViewModel.addLatLng(bundle.getDouble("lat"), bundle.getDouble("lng"))
            viewModel.getAddress(RetrofitProvider.retrofit, addressRepository, latLng)

            viewModel.address.observe(this){
                binding.addressTextView.text = it
                placeViewModel.addPlaceAddress(it)
            }

            viewModel.id.observe(this){
                placeViewModel.addPlaceId(it)
                getPhotoLink()
            }
        }else if(mode == "add"){
            val name = bundle.getString("name")
            val address = bundle.getString("address")
            val id = bundle.getString("id")

            binding.addressTextView.text = address
            binding.placeNameEditText.setText(name)
            placeViewModel.addPlaceId(id!!)

            getPhotoLink()
        }


        binding.saveBtn.setOnClickListener() {
            placeViewModel.addNewPlace(placeViewModel.place.value!!)
        }

        setUpLayout()

        placeViewModel.getAllPlaces()
        placeViewModel.places.observe(this){
            Log.d("Place List", it.toString())
        }
    }

    private fun getPhotoLink(){
        viewModel.getPhotoLink(RetrofitProvider.retrofit, addressRepository, placeViewModel.place.value!!.id)
        viewModel.link.observe(this){
            placeViewModel.addPhotoLink(it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
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