package com.mustafafidan.itunessearch.feature_search.presentation.search

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchState @Inject constructor(){
    val searchTerm = MutableStateFlow("")
    var filterMediaType = MutableStateFlow(FilterMediaType.NONE)
    val noItemDefaultVisibility = MutableStateFlow(View.GONE)

    fun onFilterBtnCheckedChanged(view : View,mediaType : FilterMediaType) {
        if(view is RadioButton && view.isChecked){
            filterMediaType.value = mediaType
        }
    }
}