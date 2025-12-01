package com.example.level_up.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFechaPedido(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}
