package ca.tanle.mapluv.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.User
import ca.tanle.mapluv.databinding.FragmentAuthenticationBinding
import ca.tanle.mapluv.ui.activities.MainActivity
import ca.tanle.mapluv.ui.activities.viewmodels.UserViewModel

private const val STATUS = "login"

class AuthenFragment : Fragment() {
    private var status: String? = "login"
    private lateinit var binding: FragmentAuthenticationBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = it.getString(STATUS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAuthenticationBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        setUp()
        return binding.root
    }

    private fun setUp(){
        binding.signUpTv.setOnClickListener{
            viewModel.setAuthen("signup")
        }

        binding.logInTv.setOnClickListener {
            viewModel.setAuthen("login")
        }

        viewModel.authen.observe(requireActivity()){
            if(it == "login"){
                binding.userNameEt.visibility = View.GONE
                binding.reenterEt.visibility  = View.GONE
                binding.logInLayout.visibility = View.GONE
                binding.signUpLayout.visibility = View.VISIBLE

                binding.authenBtn.text = getString(R.string.sign_in_btn)
                binding.googleTv.text = getString(R.string.log_in_with_google)

                binding.authenBtn.setOnClickListener {
                    if (
                        validateEmail(binding.emailEt.text.toString()) &&
                        validateTextField(binding.pwEt.text.toString())
                    ){
                        viewModel.signIn(User(binding.emailEt.text.toString(), binding.pwEt.text.toString(), "")){ success ->
                            if (success) {
                                val intent = Intent(requireActivity(), MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(requireActivity(), "Log in failed with wrong email or password !", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(requireActivity(), "Please enter valid Email and Password !", Toast.LENGTH_SHORT).show()
                    }
                }
            }else if(it == "signup"){
                binding.userNameEt.visibility = View.VISIBLE
                binding.reenterEt.visibility  = View.VISIBLE
                binding.logInLayout.visibility = View.VISIBLE
                binding.signUpLayout.visibility = View.GONE

                binding.authenBtn.text = getString(R.string.sign_up_btn)
                binding.googleTv.text = getString(R.string.sign_up_with_google)

                binding.authenBtn.setOnClickListener {
                    if (
                        validateTextField(binding.userNameEt.text.toString()) &&
                        validateEmail(binding.emailEt.text.toString()) &&
                        validateTextField(binding.pwEt.text.toString()) &&
                        validatePwSignUp(binding.pwEt.text.toString(), binding.reenterEt.text.toString())
                    ){
                        viewModel.signUp(User(binding.emailEt.text.toString(), binding.pwEt.text.toString(), binding.userNameEt.text.toString())){ success ->
                            if (success) {
                                val intent = Intent(requireActivity(), MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(requireActivity(), "Sign-up failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(requireActivity(), "Please enter valid information !", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(status: String) =
            AuthenFragment().apply {
                arguments = Bundle().apply {
                    putString(STATUS, status)
                }
            }
    }

    private fun validateTextField(s: String): Boolean{
        return s.isNotEmpty()
    }

    private fun validateEmail(s: String): Boolean{
        return s.contains("@")
    }

    private fun validatePwSignUp(s1: String, s2: String): Boolean{
        return s1 == s2
    }
}