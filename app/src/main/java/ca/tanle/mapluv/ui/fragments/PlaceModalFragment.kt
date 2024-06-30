package ca.tanle.mapluv.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.PlaceItem
import ca.tanle.mapluv.databinding.FragmentPlaceModalBinding
import ca.tanle.mapluv.databinding.FragmentPlacesBinding
import ca.tanle.mapluv.ui.activities.PlacesViewModel
import ca.tanle.mapluv.utils.OnPlaceItemClickListener
import ca.tanle.mapluv.utils.OnRemoveItemUpdateListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PlaceModalFragment(val placeItem: PlaceItem) : DialogFragment() {
    // TODO: Rename and change types of parameters
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
             onRemovePlaceListener.onPlaceItemRemove()
//             placeViewModel.getAllPlaces()
             this.dismiss()
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

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PlaceModalFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PlaceModalFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}