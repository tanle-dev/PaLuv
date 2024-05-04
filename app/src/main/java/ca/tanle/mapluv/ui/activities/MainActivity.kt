package ca.tanle.mapluv.ui.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ca.tanle.mapluv.R
import ca.tanle.mapluv.databinding.ActivityMainBinding
import ca.tanle.mapluv.ui.fragments.MapFragment
import ca.tanle.mapluv.ui.fragments.PlacesFragment
import ca.tanle.mapluv.ui.fragments.ProfileFragment
import ca.tanle.mapluv.utils.LocationUtils

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onNavItemClicked()
    }

    private fun onNavItemClicked(){
        changeScreen(MapFragment(this))
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.map -> {
                    changeScreen(MapFragment(this))
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
                    changeScreen(MapFragment(this))
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