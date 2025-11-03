package com.example.level_up.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.map
import com.example.level_up.ui.login.LoginScreen
import com.example.level_up.view.DrawerMenu
import com.example.level_up.view.ProductoFormScreen
import com.example.level_up.ui.camera.CameraScreen

@Composable
fun AppNav() {
    // 1. controlador
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route ?: "" }
        .collectAsState(initial = "")

    // 2. contenedor animado
    AnimatedContent(
        targetState = currentRoute,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) {   // <- aquí empieza el contenido animado

        // 3. NavHost dentro del AnimatedContent
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {

            // login
            composable("login") {
                LoginScreen(navController = navController)
            }

            // drawer
            composable(
                route = "DrawerMenu/{username}",
                arguments = listOf(
                    navArgument("username") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username").orEmpty()
                DrawerMenu(username = username, navController = navController)
            }

            // formulario
            composable(
                route = "ProductoFormScreen/{nombre}/{precio}",
                arguments = listOf(
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("precio") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
                val precio = backStackEntry.arguments?.getString("precio") ?: ""
                ProductoFormScreen(
                    navController = navController,
                    nombre = nombre,
                    precio = precio
                )
            }

            // cámara
            composable("camera") {
                CameraScreen()
            }
        }
    }
}
