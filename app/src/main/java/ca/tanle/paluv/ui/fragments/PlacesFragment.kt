package ca.tanle.paluv.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.paluv.R
import ca.tanle.paluv.data.models.PlaceItem
import ca.tanle.paluv.databinding.FragmentPlacesBinding
import ca.tanle.paluv.network.AddressRepository
import ca.tanle.paluv.network.RetrofitProvider
import ca.tanle.paluv.ui.activities.viewmodels.AddressViewModel
import ca.tanle.paluv.ui.activities.viewmodels.PlacesViewModel
import ca.tanle.paluv.ui.activities.viewmodels.UserViewModel
import ca.tanle.paluv.ui.fragments.adapters.PlaceListAdapter
import ca.tanle.paluv.utils.OnPlaceItemClickListener
import ca.tanle.paluv.utils.OnRemoveItemUpdateListener

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
            binding.placeListRCV.visibility = View.GONE
            binding.loadingTV.visibility = View.VISIBLE
            addressViewModel.getPlaceList(RetrofitProvider.retrofit, repository, it)
        }

        addressViewModel.placeList.observe(viewLifecycleOwner){
            binding.loadingTV.visibility = View.GONE
            binding.placeListRCV.visibility = View.VISIBLE
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