package com.example.level_up.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.level_up.data.repository.AuthRepository
import com.example.level_up.validacion.Validators

class RegistroViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState by mutableStateOf(RegistroUiState())
        private set

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, error = null, success = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null, success = null)
    }

    fun onConfirmPasswordChange(value: String) {
        uiState = uiState.copy(confirmPassword = value, error = null, success = null)
    }

    fun submit(onSuccess: () -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null, success = null)

        // 🔹 Validar usuario
        val userResult = Validators.required(uiState.username, "usuario")
        if (!userResult.isValid) {
            uiState = uiState.copy(isLoading = false, error = userResult.errorMessage)
            return
        }



        // 🔹 Validar confirmación
        if (uiState.password != uiState.confirmPassword) {
            uiState = uiState.copy(
                isLoading = false,
                error = "Las contraseñas no coinciden"
            )
            return
        }

        // 🔹 Registrar en memoria
        val created = repo.register(uiState.username, uiState.password)

        uiState = if (created) {
            uiState.copy(
                isLoading = false,
                error = null,
                success = "Usuario creado correctamente"
            ).also { onSuccess() }
        } else {
            uiState.copy(
                isLoading = false,
                error = "El usuario ya existe",
                success = null
            )
        }
    }
}
