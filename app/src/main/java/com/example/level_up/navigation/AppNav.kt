package com.example.level_up.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.level_up.ui.home.MuestraDatosScreen
import com.example.level_up.ui.login.LoginScreen
import com.example.level_up.view.DrawerMenu
import com.example.level_up.view.ProductoFormScreen
import kotlinx.coroutines.flow.map

import androidx.compose.animation.with

@Composable


fun AppNav(){

    // Creamos controlador
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route ?: ""}
        .collectAsState (initial = "")

    // Transicion
    AnimatedContent(
    targetState = currentRoute,
    transitionSpec = {
        fadeIn() togetherWith  fadeOut()
    },
    label = "RouteAnimatedContent"
    ){


        NavHost(navController = navController, startDestination = "login")
        {
            composable("login") {
                LoginScreen(navController = navController)
            }//fin composable

            composable(
                //route="muestraDatos/{username}",
                route = "DrawerMenu/{username}",
                arguments = listOf(
                    navArgument("username") {
                        type = NavType.StringType
                    }
                )//fin List Of
            )//fin composable 2

            { // inicio
                    backStackEntry ->
                val username = backStackEntry.arguments?.getString("username").orEmpty()
                // MuestraDatosScreen(username=username,navController= navController )
                DrawerMenu(username = username, navController = navController)

            }

            ///  Ruta del Formulario:  ProductoFormScreen

            composable(
                route = "ProductoFormScreen/{nombre}/{precio}",
                arguments = listOf(
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("precio") { type = NavType.StringType },
                )//fin List Of
            ) // fin composable 3

            { // inicio
                    backStackEntry ->
                val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
                val precio = backStackEntry.arguments?.getString("precio") ?: ""

                ProductoFormScreen(navController = navController, nombre = nombre, precio = precio)
            }

            }// Fin NavHost
        }
    } //fin AppNav