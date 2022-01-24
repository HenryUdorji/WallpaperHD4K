package com.hashconcepts.wallpaperhd4k.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.databinding.DetailsFragmentBinding
import com.hashconcepts.wallpaperhd4k.databinding.OptionsBottomSheetDialogBinding
import com.hashconcepts.wallpaperhd4k.extentions.showKProgressHUD
import com.hashconcepts.wallpaperhd4k.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var photo: Photo

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

        photo = args.photo
        with(binding.wallpaperImage) {
            this.transitionName = photo.id.toString()
            Picasso.get().load(photo.src.portrait).into(this)
        }

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.downloadFab.setOnClickListener {
            binding.downloadFab.hide()
            showDownloadBottomSheetDialog()
        }
    }

    private fun showDownloadBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogBinding: OptionsBottomSheetDialogBinding =
            OptionsBottomSheetDialogBinding.inflate(
                layoutInflater
            )
        bottomSheetDialog.setContentView(dialogBinding.root)

        bottomSheetDialog.setOnDismissListener { dialog ->
            dialog.dismiss()
            binding.downloadFab.show()
        }

        bottomSheetDialog.show()

        dialogBinding.original.setOnClickListener {
            bottomSheetDialog.dismiss()
            callViewModel(photo.src.original)
        }

        dialogBinding.medium.setOnClickListener {
            bottomSheetDialog.dismiss()
            callViewModel(photo.src.medium)
        }

        dialogBinding.portrait.setOnClickListener {
            bottomSheetDialog.dismiss()
            callViewModel(photo.src.portrait)
        }

        dialogBinding.small.setOnClickListener {
            bottomSheetDialog.dismiss()
            callViewModel(photo.src.small)
        }

    }

    private fun callViewModel(src: String) {
        val progressDialog = requireContext().showKProgressHUD("Downloading wallpaper")

        viewModel.downloadImage(src)
        viewModel.downloadImageLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                    state.data?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show() }
                    progressDialog.dismiss()
                }
                is Resource.Loading -> {
                    progressDialog.show()
                }
                is Resource.Error -> {
                    Snackbar.make(binding.root, state.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}