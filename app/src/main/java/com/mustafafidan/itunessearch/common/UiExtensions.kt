package com.mustafafidan.itunessearch.common

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.finishRefreshing(){
    if(this.isRefreshing) {
        this.isRefreshing = false
    }
}