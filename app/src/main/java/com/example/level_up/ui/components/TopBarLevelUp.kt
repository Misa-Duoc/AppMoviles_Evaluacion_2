package com.example.level_up.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLevelUp(
    onProfileClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onNewsClick: () -> Unit = {},
    cartCount: Int = 0
) {
    TopAppBar(
        title = {
            Text(
                text = "Level-Up Gamer",
                color = Color(0xFF1E90FF)
            )
        },
        actions = {
            // Botón Novedades Gamer
            IconButton(onClick = onNewsClick) {
                Icon(
                    imageVector = Icons.Default.Gamepad,
                    contentDescription = "Novedades gamer",
                    tint = Color.White
                )
            }

            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = Color.White
                )
            }

            BadgedBox(
                badge = {
                    if (cartCount > 0) {
                        Badge { Text(cartCount.toString()) }
                    }
                }
            ) {
                IconButton(onClick = onCartClick) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        tint = Color.White
                    )
                }
            }

            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Salir",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        )
    )
}

