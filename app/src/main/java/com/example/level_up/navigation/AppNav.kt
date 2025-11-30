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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.map
import com.example.level_up.ui.login.LoginScreen
import com.example.level_up.ui.login.RegistroScreen
import com.example.level_up.view.DrawerMenu
import com.example.level_up.view.ProductoFormScreen
import com.example.level_up.view.CartScreen
import com.example.level_up.view.HistorialPedidosScreen
import com.example.level_up.ui.camera.CameraScreen
import com.example.level_up.ui.profile.ProfileScreen
import com.example.level_up.ui.ruta.RutaSucursalScreen
import com.example.level_up.viewmodel.ProductoViewModel
import com.example.level_up.viewmodel.PedidoViewModel
import com.example.level_up.viewmodel.GameViewModel
import com.example.level_up.view.GameNewsScreen

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.level_up.data.database.ProductoDatabase
import com.example.level_up.data.repository.ProductoRepository
import com.example.level_up.viewmodel.ProductoViewModelFactory


@Composable
fun AppNav() {
    val navController = rememberNavController()

    val context = LocalContext.current

    // Crear instancia de Room una sola vez
    val database = remember { ProductoDatabase.getDatabase(context) }
    val productoRepository = remember { ProductoRepository(database.productoDao()) }


    // ViewModels compartidos
    val productoViewModel: ProductoViewModel = viewModel(
        factory = ProductoViewModelFactory(productoRepository)
    )

    val pedidoViewModel: PedidoViewModel = viewModel()

    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route ?: "" }
        .collectAsState(initial = "")

    AnimatedContent(
        targetState = currentRoute,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(navController = navController)
            }

            composable("register") {
                RegistroScreen(navController = navController)
            }

            composable(
                route = "DrawerMenu/{username}",
                arguments = listOf(
                    navArgument("username") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username").orEmpty()
                DrawerMenu(
                    username = username,
                    navController = navController,
                    productoViewModel = productoViewModel
                )
            }

            composable(
                route = "productoForm/{nombre}/{precio}",
                arguments = listOf(
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("precio") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
                val precio = backStackEntry.arguments?.getString("precio") ?: "0"

                ProductoFormScreen(
                    navController = navController,
                    nombre = nombre,
                    precio = precio,
                    productoViewModel = productoViewModel,
                    pedidoViewModel = pedidoViewModel
                )
            }

            composable("cart") {
                CartScreen(
                    navController = navController,
                    productoViewModel = productoViewModel,
                    pedidoViewModel = pedidoViewModel
                )
            }

            composable("historial") {
                HistorialPedidosScreen(
                    navController = navController,
                    pedidoViewModel = pedidoViewModel
                )
            }

            composable("profile") {
                ProfileScreen(navController = navController)
            }

            composable("camera") {
                CameraScreen()
            }

            composable("rutaSucursal") {
                RutaSucursalScreen(navController = navController)
            }

            composable("gameNews") {
                val gameViewModel: GameViewModel = viewModel()
                GameNewsScreen(
                    navController = navController,
                    gameViewModel = gameViewModel
                )
            }
        }
    }
}
