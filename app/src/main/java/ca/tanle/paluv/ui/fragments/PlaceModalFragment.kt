package ca.tanle.paluv.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ca.tanle.paluv.R
import ca.tanle.paluv.data.models.PlaceItem
import ca.tanle.paluv.databinding.FragmentPlaceModalBinding
import ca.tanle.paluv.ui.activities.AddPlaceActivity
import ca.tanle.paluv.ui.activities.viewmodels.PlacesViewModel
import ca.tanle.paluv.utils.OnRemoveItemUpdateListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PlaceModalFragment(val placeItem: PlaceItem) : DialogFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPlaceModalBinding
    private lateinit var placeViewModel: PlacesViewModel
    private lateinit var onRemovePlaceListener: OnRemoveItemUpdateListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        onRemovePlaceListener = parentFragment as OnRemoveItemUpdateListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlaceModalBinding.inflate(inflater)
        placeViewModel = ViewModelProvider(requireParentFragment())[PlacesViewModel::class.java]

        setUpLayout()

        return binding.root
    }

    private fun setUpLayout() {
         binding.placeTitle.text = placeItem.place.title
         binding.placeAdd.text = placeItem.place.address
         binding.placePhone.text = placeItem.place.phoneNumber
         binding.placeWeb.text = placeItem.place.webLink
         binding.placeComment.text = placeItem.place.comment
         binding.cancelBtnDetail.setOnClickListener {
             this.dismiss()
         }
         binding.closeBtn.setOnClickListener {
             this.dismiss()
         }

         binding.removePlaceBtn.setOnClickListener {
             placeViewModel.deletePlace(placeItem.place)
             this.dismiss()
         }

        if(placeItem.place.visited){
            binding.checkIcon.visibility = View.VISIBLE
            binding.visitedTV.visibility = View.VISIBLE
        }

        binding.editBtn.setOnClickListener {
            val intent = Intent(requireActivity(), AddPlaceActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("place", placeItem.place)
            bundle.putString("mode", "edit")
            intent.putExtras(bundle)
            startActivity(intent)
        }

         if(placeItem.photo != null){
             binding.placeImage.setImageBitmap(placeItem.photo)
         }else{
             binding.placeImage.setImageResource(R.drawable.place_placeholder)
         }

        when (placeItem.place.rate) {
            1 -> binding.star15.setImageResource(R.drawable.star_filled_50x50)
            2 -> {
                binding.star15.setImageResource(R.drawable.star_filled_50x50)
                binding.star25.setImageResource(R.drawable.star_filled_50x50)
            }
            3 -> {
                binding.star15.setImageResource(R.drawable.star_filled_50x50)
                binding.star25.setImageResource(R.drawable.star_filled_50x50)
                binding.star35.setImageResource(R.drawable.star_filled_50x50)
            }
            4 -> {
                binding.star15.setImageResource(R.drawable.star_filled_50x50)
                binding.star25.setImageResource(R.drawable.star_filled_50x50)
                binding.star35.setImageResource(R.drawable.star_filled_50x50)
                binding.star45.setImageResource(R.drawable.star_filled_50x50)
            }
            5 ->{
                binding.star15.setImageResource(R.drawable.star_filled_50x50)
                binding.star25.setImageResource(R.drawable.star_filled_50x50)
                binding.star35.setImageResource(R.drawable.star_filled_50x50)
                binding.star45.setImageResource(R.drawable.star_filled_50x50)
                binding.star55.setImageResource(R.drawable.star_filled_50x50)
            }
        }
    }
}