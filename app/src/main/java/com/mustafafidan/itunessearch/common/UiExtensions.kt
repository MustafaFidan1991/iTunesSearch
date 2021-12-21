package com.mustafafidan.itunessearch.common

import android.view.View
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

fun SwipeRefreshLayout.finishRefreshing(){
    if(this.isRefreshing) {
        this.isRefreshing = false
    }
}

fun Fragment.showSnackBar(message : String){
    Snackbar.make(
        requireView(),
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}