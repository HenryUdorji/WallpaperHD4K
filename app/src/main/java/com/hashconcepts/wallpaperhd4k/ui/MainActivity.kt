package com.hashconcepts.wallpaperhd4k.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.databinding.ActivityMainBinding
import com.hashconcepts.wallpaperhd4k.extentions.hide
import com.hashconcepts.wallpaperhd4k.extentions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Handle the splash screen transition.
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.detailsFragment,
                R.id.listFragment -> {
                    binding.bottomNav.hide()
                    binding.appBar.hide()
                }
                else -> {
                    binding.bottomNav.show()
                    binding.appBar.show()
                }
            }
        }
    }
}