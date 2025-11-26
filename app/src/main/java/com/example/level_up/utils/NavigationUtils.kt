// app/src/main/java/com/example/level_up/utils/NavigationUtils.kt
package com.example.level_up.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// Dirección fija de la sucursal (puedes poner la dirección que desees)
private const val BRANCH_ADDRESS = "Avenida Concha Y Toro, Av. San Carlos 1340, Puente Alto, Región Metropolitana"

fun openRouteToBranch(context: Context, originAddress: String) {
    if (originAddress.isBlank()) {
        Toast.makeText(context, "Ingresa una dirección de origen", Toast.LENGTH_SHORT).show()
        return
    }

    val originEncoded = URLEncoder.encode(originAddress, StandardCharsets.UTF_8.toString())
    val destEncoded = URLEncoder.encode(BRANCH_ADDRESS, StandardCharsets.UTF_8.toString())

    // URL oficial de Google Maps Directions API (modo web)
    val uri = Uri.parse(
        "https://www.google.com/maps/dir/?api=1" +
                "&origin=$originEncoded" +
                "&destination=$destEncoded" +
                "&travelmode=driving"
    )

    val intent = Intent(Intent.ACTION_VIEW, uri)

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No hay app para abrir el mapa", Toast.LENGTH_SHORT).show()
    }
}
