package com.hashconcepts.wallpaperhd4k.extentions

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hashconcepts.wallpaperhd4k.R

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showShortSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.showErrorSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .show()
        .also {
            setBackgroundColor(resources.getColor(R.color.colorRed, null))
        }
}

fun View.showSuccessSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .show()
        .also {
            setBackgroundColor(resources.getColor(R.color.colorGreen, null))
        }
}
