package com.example.level_up.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.level_up.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
){ // inicio

    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    // esquema oscuro
    val colorScheme = darkColorScheme(
        primary = Color(0xFF1E90FF),
        onPrimary = Color.White,
        background = Color.Black,
        surface = Color.Black,
        onSurface = Color.White
    )

    MaterialTheme(
        colorScheme = colorScheme
    ) { // inicio aplicar material

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Level-Up Gamer",
                            color = Color(0xFF1E90FF)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black
                    )
                )
            },
            containerColor = Color.Black
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) { // inicio contenido

                Text(
                    text = "¡Bienvenido Gamer!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF1E90FF)
                )

                Image(
                    painter = painterResource(id = R.drawable.level_up_gamer_logo),
                    contentDescription = "Logo App",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(40.dp))

                // -------- Usuario ----------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        "Usuario",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                OutlinedTextField(
                    value = state.username,
                    onValueChange = vm::onUsernameChange,
                    label = { Text("Usuario", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.95f),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                // -------- Contraseña ----------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        "Contraseña",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña", color = Color.White) },
                    singleLine = true,
                    visualTransformation = if (showPass)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation('•'),   // puntitos blancos
                    trailingIcon = {
                        TextButton(onClick = { showPass = !showPass }) {
                            Text(
                                if (showPass) "Ocultar" else "Ver",
                                color = Color(0xFF1E90FF)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.95f),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                // mensaje de error
                if (state.error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = state.error ?: "",
                        color = Color(0xFF39FF14),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        vm.submit { user ->
                            navController.navigate("DrawerMenu/user") {
                                popUpTo("login") { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E90FF),
                        contentColor = Color.White
                    )
                ) {
                    Text(if (state.isLoading) "Validando" else "Iniciar Sesion")
                }

            } // fin contenido
        } // fin scaffold
    } // fin aplicar material
} // fin LoginScreen

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    val vm = LoginViewModel()
    LoginScreen(navController = navController, vm = vm)
}
