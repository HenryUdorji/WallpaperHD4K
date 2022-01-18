package com.hashconcepts.wallpaperhd4k.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.databinding.DetailsFragmentBinding
import com.hashconcepts.wallpaperhd4k.ui.MainActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class DetailsFragment : Fragment() {
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        val photo = args.photo
        with(binding.wallpaperImage) {
            this.transitionName = photo.id.toString()
            Picasso.get().load(photo.src.portrait).into(this)
        }

        binding.buttonBack.setOnClickListener { v ->
            requireActivity().onBackPressed()
        }
    }

}