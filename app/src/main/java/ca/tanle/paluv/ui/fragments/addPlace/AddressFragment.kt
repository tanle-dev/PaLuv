package ca.tanle.paluv.ui.fragments.addPlace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.paluv.databinding.FragmentAddressBinding
import ca.tanle.paluv.network.AddressRepository
import ca.tanle.paluv.network.RetrofitProvider
//import ca.tanle.mapluv.ui.fragments.ARG_PARAM1
//import ca.tanle.mapluv.ui.fragments.ARG_PARAM2
import ca.tanle.paluv.ui.fragments.adapters.SearchAddressAdapter

class AddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAddressBinding
    private lateinit var searchAddressViewModel: SearchAddressViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var researchAddAdapter: SearchAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(inflater)
        searchAddressViewModel = ViewModelProvider(this)[SearchAddressViewModel::class.java]
        val addressRepository = AddressRepository()

        researchAddAdapter = SearchAddressAdapter(arrayListOf())
        recyclerView = binding.searchAddRC
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.adapter = researchAddAdapter

        binding.etName.addTextChangedListener(){
            searchAddressViewModel.updateSearchText(it.toString())
        }

        binding.searchBtn.setOnClickListener {
            searchAddressViewModel.getSearchAddress(RetrofitProvider.retrofit, addressRepository)
        }

        searchAddressViewModel.addresses.observe(requireActivity()){
            researchAddAdapter.updateDataSet(it.results)
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
         * @return A new instance of fragment AddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddressFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}