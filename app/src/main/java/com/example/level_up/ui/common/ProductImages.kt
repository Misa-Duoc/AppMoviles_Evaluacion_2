package com.example.level_up.ui.common

import androidx.annotation.DrawableRes
import com.example.level_up.R
import java.text.Normalizer

private fun normalizeKey(raw: String): String {
    // quita tildes, pasa a minúsculas, recorta espacios
    val noAccent = Normalizer.normalize(raw, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    return noAccent.lowercase().trim()
}

@DrawableRes
// devuelve la imagen según el nombre del producto
fun productImageResByName(nombre: String): Int {
    return when (nombre.lowercase()) {
        "auriculares gamer hyperx" -> R.drawable.auriculares_gamer_hyperx
        "mouse logitech g203" -> R.drawable.mouse_logitech_g203
        "playstation 5" -> R.drawable.playstation5
        "mousepad" -> R.drawable.mousepad1
        "carcassone" -> R.drawable.carcassonne
        "catan" -> R.drawable.catan
        else -> R.drawable.placeholder
    }
}
