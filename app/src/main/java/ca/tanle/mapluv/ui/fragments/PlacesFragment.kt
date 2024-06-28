package ca.tanle.mapluv.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.mapluv.R
import ca.tanle.mapluv.databinding.FragmentPlacesBinding
import ca.tanle.mapluv.network.AddressRepository
import ca.tanle.mapluv.network.RetrofitProvider
import ca.tanle.mapluv.ui.activities.AddressViewModel
import ca.tanle.mapluv.ui.activities.PlacesViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PlacesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var placeViewModel: PlacesViewModel
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        placeViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]
        binding = FragmentPlacesBinding.inflate(inflater)

        addressViewModel = ViewModelProvider(this)[AddressViewModel::class.java]
        val repository = AddressRepository()
        placeViewModel.getAllPlaces()

        val placeListAdapter = PlaceListAdapter(arrayListOf())
        recyclerView = binding.placeListRCV
        recyclerView.layoutManager = GridLayoutManager(activity, 2)

        recyclerView.adapter = placeListAdapter

        placeViewModel.places.observe(requireActivity()){
            addressViewModel.getPlaceList(RetrofitProvider.retrofit, repository, it)
        }

        addressViewModel.placeList.observe(requireActivity()){
            placeListAdapter.updateDataChanged(it)
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlacesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlacesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}