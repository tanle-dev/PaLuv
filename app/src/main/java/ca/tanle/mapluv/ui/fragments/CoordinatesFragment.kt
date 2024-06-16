package ca.tanle.mapluv.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import ca.tanle.mapluv.R
import ca.tanle.mapluv.databinding.FragmentCoordinatesBinding
import ca.tanle.mapluv.ui.activities.AddPlaceActivity
import ca.tanle.mapluv.ui.activities.CoordinatesViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CoordinatesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCoordinatesBinding
    private lateinit var coordinatesViewModel: CoordinatesViewModel

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
        binding = FragmentCoordinatesBinding.inflate(inflater)
        coordinatesViewModel = ViewModelProvider(requireActivity())[CoordinatesViewModel::class.java]

        setUpCoordinateFragment()

        return binding.root
    }

    private fun setUpCoordinateFragment(){
        binding.tvLat.text = "Latitude: " + String.format("%.6f", coordinatesViewModel.coordinate.value?.latitude)
        binding.tvLng.text = "Longitude: " + String.format("%.6f", coordinatesViewModel.coordinate.value?.longitude)

        binding.addPlaceBtn.setOnClickListener{
            val bundle = Bundle()
            bundle.putDouble("lat", coordinatesViewModel.coordinate.value!!.latitude)
            bundle.putDouble("lng", coordinatesViewModel.coordinate.value!!.longitude)
            val intent = Intent(requireActivity(), AddPlaceActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoordinatesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoordinatesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}