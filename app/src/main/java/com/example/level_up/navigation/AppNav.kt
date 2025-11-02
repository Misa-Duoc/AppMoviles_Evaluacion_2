package com.example.level_up.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
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

@Composable


fun AppNav(){

    // Creamos controlador
    val navController = rememberNavController()

    NavHost( navController= navController, startDestination = "login")
    {
        composable("login"){
            LoginScreen(navController= navController)
        }//fin composable

        composable(
            //route="muestraDatos/{username}",
            route="DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username"){
                    type= NavType.StringType
                }
            )//fin List Of
        )//fin composable 2

        { // inicio
                backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            // MuestraDatosScreen(username=username,navController= navController )
            DrawerMenu(username=username,navController= navController )

        }

///  Ruta del Formulario:  ProductoFormScreen

        composable(
            route="ProductoFormScreen/{nombre}/{precio}",
            arguments = listOf(
                navArgument("nombre"){ type= NavType.StringType },
                navArgument("precio"){ type= NavType.StringType },
            )//fin List Of
        ) // fin composable 3

        { // inicio
                backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?:"")
            val precio = backStackEntry.arguments?.getString("precio") ?:""

            ProductoFormScreen( navController= navController,  nombre=nombre,precio= precio )
        }

    }// Fin NavHost

}//fin AppNav