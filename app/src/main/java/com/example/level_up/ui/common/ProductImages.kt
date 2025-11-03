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
fun productImageResByName(nombre: String): Int {
    return when (normalizeKey(nombre)) {
        "auriculares gamer hyperx", "hyperx" -> R.drawable.auriculares_gamer_hyperx
        "mouse logitech g203", "logitech" -> R.drawable.mouse_logitech_g203
        "playstation5", "sony" -> R.drawable.playstation5
        "carcassonne", "hasbro" -> R.drawable.carcassonne
        "mousepad", "hyperx" -> R.drawable.mousepad1
        // Agrega aquí más sinónimos/variantes según tu catálogo
        else -> android.R.drawable.ic_menu_gallery // fallback
    }
}
