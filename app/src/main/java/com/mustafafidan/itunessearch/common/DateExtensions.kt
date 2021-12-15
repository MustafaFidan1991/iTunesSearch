package com.mustafafidan.itunessearch.common

import android.content.Context
import android.text.format.DateFormat
import com.mustafafidan.itunessearch.constants.DATE_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.getFormattedDate(context : Context) : String{
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    var date : Date?= null;
    try {
        date = sdf.parse(this)
    } catch (e: ParseException) {
        return ""
    }
    val dateFormat = DateFormat.getLongDateFormat(context)
    val timeFormat = DateFormat.getTimeFormat(context)
    val dateFormatStr = dateFormat.format(date)
    val timeFormatStr = timeFormat.format(date)
    return  "$dateFormatStr $timeFormatStr"
}