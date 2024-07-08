package ca.tanle.mapluv.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.User
import ca.tanle.mapluv.databinding.FragmentProfileBinding
import ca.tanle.mapluv.ui.activities.viewmodels.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding

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
        binding = FragmentProfileBinding.inflate(inflater)
        val viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]



        binding.signUpBtn.setOnClickListener {
            val user = User("user2@gmail.com", "123456", "user2")
            viewModel.signUp(user){ success ->
                if (success) {
                    Toast.makeText(requireActivity(), "Sign-up successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "Sign-up failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.signInBtn.setOnClickListener {
            val user = User("user2@gmail.com", "123456", "user2")
            viewModel.signIn(user) { success ->
                if (success) {
                    Toast.makeText(requireActivity(), "Sign-in successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "Sign-in failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.logOutBtn.setOnClickListener {
            viewModel.logOut{
                if(it){
                    Toast.makeText(requireActivity(), "Sign-out successful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireActivity(), "Sign-out successful", Toast.LENGTH_SHORT).show()
                }
            }
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}