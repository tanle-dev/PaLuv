package ca.tanle.mapluv.ui.activities

import android.Manifest
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.tanle.mapluv.R
import ca.tanle.mapluv.data.models.User
import ca.tanle.mapluv.databinding.ActivityMainBinding
import ca.tanle.mapluv.ui.activities.viewmodels.UserViewModel
import ca.tanle.mapluv.ui.fragments.MapFragment
import ca.tanle.mapluv.ui.fragments.PlacesFragment
import ca.tanle.mapluv.ui.fragments.ProfileFragment
import ca.tanle.mapluv.utils.Graph
import ca.tanle.mapluv.utils.LocationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onNavItemClicked()
    }

    private fun onNavItemClicked(){
        val bundle = intent.extras?.getBoolean("addOrEdit") ?: false
        if(bundle){
            changeScreen(PlacesFragment())
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