package ca.tanle.paluv.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.tanle.paluv.R
import ca.tanle.paluv.databinding.ActivityMainBinding
import ca.tanle.paluv.ui.activities.viewmodels.PlacesViewModel
import ca.tanle.paluv.ui.activities.viewmodels.UserViewModel
import ca.tanle.paluv.ui.fragments.MapFragment
import ca.tanle.paluv.ui.fragments.PlacesFragment
import ca.tanle.paluv.ui.fragments.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.getUserName()

        ViewModelProvider(this)[PlacesViewModel::class.java]

        viewModel.userName.observe(this){
            onNavItemClicked()
        }
    }

    private fun onNavItemClicked(){
        val bundle = intent.extras?.getBoolean("addOrEdit") ?: false
        if(bundle){
            changeScreen(PlacesFragment())
            binding.bottomNavigationView.selectedItemId = R.id.places
        }else{
            changeScreen(MapFragment())
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.map -> {
                    changeScreen(MapFragment())
                    true
                }

                R.id.places -> {
                    changeScreen(PlacesFragment())
                    true
                }

                R.id.profile -> {
                    changeScreen(ProfileFragment())
                    true
                }

                else -> {
                    changeScreen(MapFragment())
                    true
                }
            }
        }
    }

    private fun changeScreen(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}