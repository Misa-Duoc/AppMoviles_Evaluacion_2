package com.example.level_up.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
fun formatearFecha(millis: Long): String {
    val date = Date(millis)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}