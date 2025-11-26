package com.example.level_up.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    vm: RegistroViewModel = viewModel()
) {
    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }
    var showConfirmPass by remember { mutableStateOf(false) }

    val colorScheme = darkColorScheme(
        primary = Color(0xFF1E90FF),
        onPrimary = Color.White,
        background = Color.Black,
        surface = Color.Black,
        onSurface = Color.White
    )

    MaterialTheme(
        colorScheme = colorScheme
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nuevo usuario", color = Color.White) },
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
            ) {

                Text(
                    text = "Crear nuevo usuario",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF1E90FF)
                )

                // Usuario
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Usuario",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = vm::onUsernameChange,
                        label = { Text("Nombre de usuario", color = Color.White) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1E90FF),
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }

                // Contraseña
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Contraseña",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = vm::onPasswordChange,
                        label = { Text("Contraseña", color = Color.White) },
                        singleLine = true,
                        visualTransformation = if (showPass)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation('•'),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1E90FF),
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }

                // Confirmar contraseña
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Confirmar contraseña",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = vm::onConfirmPasswordChange,
                        label = { Text("Repite la contraseña", color = Color.White) },
                        singleLine = true,
                        visualTransformation = if (showConfirmPass)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation('•'),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1E90FF),
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }

                // Error / éxito
                state.error?.let { msg ->
                    Text(
                        text = msg,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                state.success?.let { msg ->
                    Text(
                        text = msg,
                        color = Color(0xFF39FF14),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        vm.submit {
                            // Al crear usuario, volvemos al login
                            navController.popBackStack()
                        }
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E90FF),
                        contentColor = Color.White
                    )
                ) {
                    Text(if (state.isLoading) "Creando..." else "Registrar usuario")
                }

                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Volver al login", color = Color(0xFF1E90FF))
                }
            }
        }
    }
}
