package com.mustafafidan.itunessearch.common

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T)->Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}