package ca.tanle.mapluv.ui.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.databinding.ActivityAddPlaceBinding
import ca.tanle.mapluv.network.AddressRepository
import ca.tanle.mapluv.network.IAddressRepository
import ca.tanle.mapluv.network.RetrofitProvider
import ca.tanle.mapluv.ui.activities.viewmodels.AddressViewModel
import ca.tanle.mapluv.ui.activities.viewmodels.PlacesViewModel
import ca.tanle.mapluv.utils.DatePickerFragment
import ca.tanle.mapluv.utils.IDate
import ca.tanle.mapluv.utils.ITime
import ca.tanle.mapluv.utils.TimePickerFragment
import java.util.Date

class AddPlaceActivity : AppCompatActivity(), IDate, ITime {
    lateinit var binding: ActivityAddPlaceBinding
    private lateinit var viewModel: AddressViewModel
    private lateinit var placeViewModel: PlacesViewModel
    private lateinit var addressRepository: IAddressRepository
    private lateinit var mode: String
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AddressViewModel::class.java]
        placeViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]

        addressRepository = AddressRepository()

        val bundle = intent.extras!!
        mode = bundle.getString("mode") ?: ""

        when (mode) {
            "cdn" -> {
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
            }

            "add" -> {
                val name = bundle.getString("name")
                val address = bundle.getString("address")
                val id = bundle.getString("id")
                val lat = bundle.getDouble("lat")
                val lng = bundle.getDouble("lng")

                if (!address.isNullOrEmpty()){
                    placeViewModel.addPlaceAddress(address)
                    binding.addressTextView.text = address
                }
                if (id?.isNotEmpty() == true){
                    placeViewModel.addPlaceId(id)
                }
                if (!name.isNullOrEmpty()){
                    binding.placeNameEditText.setText(name)
                }

                if (!lat.isNaN() && !lng.isNaN()){
                    placeViewModel.addLatLng(lat, lng)
                }

                getPhotoLink()
            }

            "edit" -> {
                binding.saveBtn.text = getString(R.string.update)
                val place = bundle.getSerializable("place")
                if(place != null) setUpEditPlace(place as Place)
                placeViewModel.setPlace(place as Place)
                Log.d("Place-Item", place.toString())
            }
        }

        setUpLayout()
    }

    private fun handleSaveBtnClicked(){
        if(placeViewModel.place.value?.title == ""){
            placeViewModel.addPlaceName(binding.placeNameEditText.text.toString())
        }
        if(mode == "edit") {
            placeViewModel.updatePlace(placeViewModel.place.value!!)
            Log.d("place", placeViewModel.place.value.toString())
        }
        else placeViewModel.addNewPlace(placeViewModel.place.value!!)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("addOrEdit", true)
        }
        startActivity(intent)
        finish()
    }

    private fun getPhotoLink(){
        viewModel.getPhotoLink(RetrofitProvider.retrofit, addressRepository, placeViewModel.place.value!!.id)
        viewModel.link.observe(this){
            placeViewModel.addPhotoLink(it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpEditPlace(place: Place){
        binding.addressTextView.text = place.address
        binding.placeNameEditText.setText(place.title)
        if (place.visited) binding.visitedCheckBox.isChecked = true
        when(place.rate){
            1 -> binding.star1.setImageResource(R.drawable.star_filled_icon)
            2 -> {
                binding.star1.setImageResource(R.drawable.star_filled_icon)
                binding.star2.setImageResource(R.drawable.star_filled_icon)
            }
            3 -> {
                binding.star1.setImageResource(R.drawable.star_filled_icon)
                binding.star2.setImageResource(R.drawable.star_filled_icon)
                binding.star3.setImageResource(R.drawable.star_filled_icon)
            }
            4 -> {
                binding.star1.setImageResource(R.drawable.star_filled_icon)
                binding.star2.setImageResource(R.drawable.star_filled_icon)
                binding.star3.setImageResource(R.drawable.star_filled_icon)
                binding.star4.setImageResource(R.drawable.star_filled_icon)
            }
            5 -> {
                binding.star1.setImageResource(R.drawable.star_filled_icon)
                binding.star2.setImageResource(R.drawable.star_filled_icon)
                binding.star3.setImageResource(R.drawable.star_filled_icon)
                binding.star4.setImageResource(R.drawable.star_filled_icon)
                binding.star5.setImageResource(R.drawable.star_filled_icon)
            }
        }

        binding.commentEditText.setText(place.comment)
        binding.phoneEditText.setText(place.phoneNumber)
        binding.linkEditText.setText(place.webLink)
        if(place.reminderTitle.isNotEmpty()) {
            binding.reminderSwitch.isChecked = true
            binding.reminderLayout.visibility = View.VISIBLE
        }
        binding.reminderTitleEditText.setText(place.reminderTitle)
        binding.datePickerBtn.setText(place.reminderDate)
        binding.timePickerBtn.setText(place.reminderTime)
    }

    private fun setUpLayout() {
        val alertConfirm = AlertDialog.Builder(this)
        alertConfirm.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        alertConfirm.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            handleSaveBtnClicked()
        })
        alertConfirm.setNegativeButton("No", DialogInterface.OnClickListener() { dialog, which ->
            dialog.cancel()
        });

        val alertValidate = AlertDialog.Builder(this)
        alertValidate.setMessage(R.string.name_is_needed).setTitle(R.string.name_is_needed_title)
        alertValidate.setPositiveButton( "OK", DialogInterface.OnClickListener() { dialog, which ->
            dialog.cancel()
        })

        binding.saveBtn.setOnClickListener() {
            if(binding.placeNameEditText.text.toString() == ""){
                alertValidate.create().show()
            }else{
                alertConfirm.create().show()
            }
        }

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

        binding.datePickerBtn.text =
            getString(R.string.date_text_btn, Date().date, Date().month + 1, Date().year + 1900)
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