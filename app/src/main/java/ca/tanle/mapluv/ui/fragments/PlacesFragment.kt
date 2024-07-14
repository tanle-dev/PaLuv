package ca.tanle.mapluv.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.PlaceItem
import ca.tanle.mapluv.databinding.FragmentPlacesBinding
import ca.tanle.mapluv.network.AddressRepository
import ca.tanle.mapluv.network.RetrofitProvider
import ca.tanle.mapluv.ui.activities.viewmodels.AddressViewModel
import ca.tanle.mapluv.ui.activities.viewmodels.PlacesViewModel
import ca.tanle.mapluv.ui.activities.viewmodels.UserViewModel
import ca.tanle.mapluv.ui.fragments.adapters.PlaceListAdapter
import ca.tanle.mapluv.utils.OnPlaceItemClickListener
import ca.tanle.mapluv.utils.OnRemoveItemUpdateListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PlacesFragment : Fragment(), OnPlaceItemClickListener, OnRemoveItemUpdateListener {
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

        placeViewModel = ViewModelProvider(requireActivity())[PlacesViewModel::class.java]
        addressViewModel = ViewModelProvider(requireActivity())[AddressViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPlacesBinding.inflate(inflater)

        val repository = AddressRepository()
        placeViewModel.getAllPlaces()

        val placeListAdapter = PlaceListAdapter(this, arrayListOf())
        recyclerView = binding.placeListRCV
        recyclerView.layoutManager = GridLayoutManager(activity, 2)

        recyclerView.adapter = placeListAdapter
        placeViewModel.places.observe(viewLifecycleOwner){
            addressViewModel.getPlaceList(RetrofitProvider.retrofit, repository, it)
        }

        addressViewModel.placeList.observe(viewLifecycleOwner){
            placeListAdapter.updateDataChanged(it)
        }

        val viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        viewModel.userName.observe(requireActivity()){
            binding.userNameTV.text = getString(R.string.username, it)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlacesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPlaceItemClick(placeItem: PlaceItem) {
        val fragment = PlaceModalFragment(placeItem)
        fragment.show(childFragmentManager, "Fragment Modal")
    }

    override fun onPlaceItemRemove() {
        placeViewModel.getAllPlaces()
    }
}