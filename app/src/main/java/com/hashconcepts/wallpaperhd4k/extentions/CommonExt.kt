package com.hashconcepts.wallpaperhd4k.extentions

import android.app.ProgressDialog
import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

fun Context.showKProgressHUD(label: String): KProgressHUD {
    return KProgressHUD.create(this)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setCancellable(false)
        .setLabel(label)
        .setAnimationSpeed(1)
        .setDimAmount(0.5f)
}

fun Context.showDownloadProgressDialog(): ProgressDialog {
    return ProgressDialog(this)
}