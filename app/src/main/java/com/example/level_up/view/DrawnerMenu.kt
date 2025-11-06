package com.example.level_up.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.TableBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.level_up.ui.components.TopBarLevelUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) { // inicio función

    // ----------- Lista de productos
    val productos = listOf(
        Triple("Auriculares Gamer HyperX", "35000", Icons.Default.Headset),
        Triple("Mouse Logitech G203", "30000", Icons.Default.Mouse),
        Triple("PlayStation 5", "550000", Icons.Default.Gamepad),
        Triple("Mousepad", "5000", Icons.Default.BookOnline),
        Triple("Carcassone", "10000", Icons.Default.TableBar),
        Triple("Catan", "10000", Icons.Default.TableBar),
        Triple("Escanear QR", "", Icons.Default.CameraAlt),
    )

    Scaffold(
        topBar = {
            TopBarLevelUp(
                onLogoutClick = {
                    // Acción de cierre de sesión
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
        ) { // inicio columna

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Catálogo Gamer",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "@$username", // muestra el nombre del usuario
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF1E90FF)
                )
            } // fin encabezado

            // ----------- Lista de productos
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) { // inicio lista

                items(productos) { item ->
                    val nombre = item.first
                    val precio = item.second
                    val icono = item.third

                    // Cada producto es una tarjeta
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                if (nombre == "Escanear QR") {
                                    navController.navigate("camera")
                                } else {
                                    val encoded = Uri.encode(nombre)
                                    navController.navigate("ProductoFormScreen/$encoded/$precio")
                                }
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1A1A1A) // Fondo oscuro gamer
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) { // inicio card
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Ícono de producto
                            Icon(
                                imageVector = icono,
                                contentDescription = "Icono producto",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(end = 12.dp)
                            )

                            // Nombre y precio
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = nombre,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                if (precio.isNotBlank()) {
                                    Text(
                                        text = "Precio: $precio",
                                        color = Color(0xFF39FF14), // verde neón
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            } // fin columna texto
                        } // fin row
                    } // fin card
                } // fin items
            } // fin LazyColumn

            //footer
            Text(
                text = "@ 2025 Level Up Gaming APP",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )

        } // fin columna principal
    } // fin Scaffold
} // fin DrawerMenu
