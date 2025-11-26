package com.example.level_up.data.repository

import com.example.level_up.data.model.Credential

class AuthRepository {

    // Lista de usuarios en memoria (se pierde al cerrar la app)
    companion object {
        private val users = mutableListOf(
            Credential.Admin // usuario por defecto: admin / admin
        )
    }

    fun login(username: String, password: String): Boolean {
        return users.any { it.username == username && it.password == password }
    }

    /**
     * Registra un nuevo usuario SOLO en memoria.
     * Retorna:
     *  - true  -> si se creó correctamente
     *  - false -> si el usuario ya existe
     */
    fun register(username: String, password: String): Boolean {
        // Evitar duplicados
        if (users.any { it.username.equals(username.trim(), ignoreCase = true) }) {
            return false
        }

        users.add(Credential(username.trim(), password))
        return true
    }
}
