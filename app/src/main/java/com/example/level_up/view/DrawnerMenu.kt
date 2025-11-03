package com.example.level_up.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TableBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun DrawerMenu(
    username:String,
    navController: NavController
){ // inicio

    Column(modifier =Modifier.fillMaxSize())
    { //inicio columna
        Box(
            modifier =Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.onBackground)
        ) // fin box
        { //inicio contenido
            Text(
                text="Categorias user:$username",
                style=MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier =Modifier
                    .align(Alignment.BottomStart)
            )
        } //fin contenido

        // LazyColumn: Crear la lista de elemnetos que se pueden desplazar verticalmente

        LazyColumn( modifier =Modifier.weight(1f)){
            item{
                NavigationDrawerItem(
                    label={Text("Auriculares gamer hyperX")},
                    selected = false,
                    onClick = {
                        val nombre = Uri.encode("Auriculares gamer hyperX")
                        val precio="35000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    },//fin onclick
                    icon = {Icon(Icons.Default.Headset , contentDescription = "Headset"  )}
                )
            }// fin item 1

            item{
                NavigationDrawerItem(
                    label={Text("Mouse logitech g203")},
                    selected = false,
                    onClick = {

                        val nombre = Uri.encode("Mouse logitech g203")
                        val precio="30000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")

                    },//fin onclick
                    icon = {Icon(Icons.Default.Mouse , contentDescription = "Mouse"  )}
                )
            }// fin item 2

            item{
                NavigationDrawerItem(
                    label={Text("Playstation 5")},
                    selected = false,
                    onClick = {

                        val nombre = Uri.encode("Playstation 5")
                        val precio="550000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")

                    },//fin onclick
                    icon = {Icon(Icons.Default.Gamepad , contentDescription = "Playstation 5"  )}
                )
            }// fin item 3

            item{
                NavigationDrawerItem(
                    label={Text("Mousepad")},
                    selected = false,
                    onClick = {

                        val nombre = Uri.encode("Mousepad")
                        val precio="5000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")

                    },//fin onclick
                    icon = {Icon(Icons.Default.BookOnline, contentDescription = "Mousepad"  )}
                )
            }// fin item 4

            item{
                NavigationDrawerItem(
                    label={Text("Carcassone")},
                    selected = false,
                    onClick = {

                        val nombre = Uri.encode("Carcassone")
                        val precio="10000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")

                    },//fin onclick
                    icon = {Icon(Icons.Default.TableBar , contentDescription = "Carcassone"  )}
                )
            }// fin item 5

            item{
                NavigationDrawerItem(
                    label={Text("Escanear QR")},
                    selected = false,
                    onClick = {
                        navController.navigate("camera")
                    },//fin onclick
                    icon = {Icon(Icons.Default.CameraAlt , contentDescription = "Camera"  )}
                )
            }// fin item 6

        } // fin Lazy

//Footer

        Text(
            text="@ 2025 Level Up Gaming APP",
            style=MaterialTheme.typography.bodySmall,
            modifier =Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center

        )


    } //fin columna


}// fin DrawerMenu


@Preview(showBackground = true)
@Composable


fun DrawerMenuPreview(){
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}