package ca.tanle.paluv.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.tanle.paluv.R
import ca.tanle.paluv.data.models.User
import ca.tanle.paluv.databinding.FragmentAuthenticationBinding
import ca.tanle.paluv.ui.activities.MainActivity
import ca.tanle.paluv.ui.activities.viewmodels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException


private const val STATUS = "login"

class AuthenFragment : Fragment() {
    private var status: String? = "login"
    private lateinit var binding: FragmentAuthenticationBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = it.getString(STATUS)
        }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("967564892573-nckf5dkum0ppr06vp2n2h2medhtvu6q6.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
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

    private fun checkGooglePlayServices(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(requireActivity())
        return if (status == ConnectionResult.SUCCESS) {
            true
        } else {
            googleApiAvailability.getErrorDialog(this, status, 0)?.show()
            false
        }
    }

    override fun onStart() {
        super.onStart()

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignIn.getLastSignedInAccount(requireActivity())
    }

    private fun setUp(){
        binding.signUpTv.setOnClickListener{
            viewModel.setAuthen("signup")
        }

        binding.logInTv.setOnClickListener {
            viewModel.setAuthen("login")
        }

        binding.googleBtn.setOnClickListener {
            signInWithGoogle()
        }

        viewModel.authen.observe(requireActivity()){
            if(it == "login"){
                binding.userNameEt.visibility = View.GONE
                binding.reenterEt.visibility  = View.GONE
                binding.logInLayout.visibility = View.GONE
                binding.signUpLayout.visibility = View.VISIBLE

                binding.authenBtn.text = getString(R.string.sign_in_btn)

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

        const val RC_SIGN_IN = 1
        @JvmStatic
        fun newInstance(status: String) =
            AuthenFragment().apply {
                arguments = Bundle().apply {
                    putString(STATUS, status)
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            if(resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val idToken = task.getResult(ApiException::class.java).idToken
                handleSignInResult(idToken)
            }
        }
    }

    private fun handleSignInResult(idToken: String?){
        when {
            idToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                viewModel.signIntWithGoogle(idToken){success ->
                    if (success){
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(requireActivity(), "Sign-in with Google failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> {
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