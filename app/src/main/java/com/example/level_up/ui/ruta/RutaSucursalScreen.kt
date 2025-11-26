// RutaSucursalScreen.kt
package com.example.level_up.ui.ruta

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RutaSucursalScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var origin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Ruta a la sucursal",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Ingresa tu dirección de inicio y te abrimos Google Maps con el recorrido hasta la sucursal.",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = origin,
            onValueChange = { origin = it },
            label = { Text("Dirección de origen") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { openRouteToBranch(context, origin) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver ruta en Google Maps")
        }
    }
}

fun openRouteToBranch(context: Context, originAddress: String) {
    if (originAddress.isBlank()) {
        Toast.makeText(context, "Ingresa una dirección de origen", Toast.LENGTH_SHORT).show()
        return
    }

    val originEncoded = URLEncoder.encode(originAddress, StandardCharsets.UTF_8.toString())
    val destEncoded = URLEncoder.encode(
        "Av. Apoquindo 3000, Las Condes, Chile",
        StandardCharsets.UTF_8.toString()
    )

    // URL de Google Maps en modo web
    val uri = Uri.parse(
        "https://www.google.com/maps/dir/?api=1" +
                "&origin=$originEncoded" +
                "&destination=$destEncoded" +
                "&travelmode=driving"
    )

    val intent = Intent(Intent.ACTION_VIEW, uri)

    // Verificar si hay una aplicación (Google Maps o navegador) que pueda abrir la URL
    if (intent.resolveActivity(context.packageManager) != null) {
        try {
            context.startActivity(intent) // Intenta abrir la URL con el Intent
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir el mapa: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "No hay aplicación para abrir el mapa", Toast.LENGTH_SHORT).show()
    }
}

