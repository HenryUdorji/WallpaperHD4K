package com.hashconcepts.wallpaperhd4k.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_WallpaperHD4K)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}