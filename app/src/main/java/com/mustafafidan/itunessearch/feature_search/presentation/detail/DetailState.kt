package com.mustafafidan.itunessearch.feature_search.presentation.detail

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel

class DetailState(
    private val resultUiModel: ResultUiModel?
){
    fun getArtistFirstWords() = resultUiModel?.artistFirstWords

    fun getArtistImageUrlVisibility() = if(resultUiModel?.artistFirstWords == null) View.GONE else View.VISIBLE

    fun getArtistName() = resultUiModel?.artistName

    fun getReleaseDate() = resultUiModel?.date

    fun getName() = resultUiModel?.name

    fun getImageUrl() = resultUiModel?.imageUrl

    fun getNoItemVisibility() = if(resultUiModel?.description == null) View.VISIBLE else View.GONE

    fun getDescr() = resultUiModel?.description?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(it)
        }
    }

    fun onBackClicked(view : View){
        view.findFragment<DetailFragment>().findNavController().popBackStack()
    }
}