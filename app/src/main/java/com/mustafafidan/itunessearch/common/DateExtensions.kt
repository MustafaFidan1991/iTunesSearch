package com.mustafafidan.itunessearch.common

import android.content.Context
import android.text.format.DateFormat
import com.mustafafidan.itunessearch.constants.DATE_FORMAT
import com.mustafafidan.itunessearch.constants.OUTPUT_DATE_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.getFormattedDate() : String{
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)
    var date : Date?= null;
    try {
        date = sdf.parse(this)
    } catch (e: ParseException) {
        return ""
    }
    return  SimpleDateFormat(OUTPUT_DATE_FORMAT,Locale.US).format(date)
}