package com.example.level_up.validacion

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object Validators {

    fun required(value: String, field: String): ValidationResult {
        return if (value.isBlank()) {
            ValidationResult(false, "El $field es obligatorio")
        } else ValidationResult(true)
    }

    fun minLength(value: String, field: String, min: Int): ValidationResult {
        return if (value.length < min) {
            ValidationResult(false, "$field debe tener al menos $min caracteres")
        } else ValidationResult(true)
    }

    fun price(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult(false, "El precio es obligatorio")
        val num = value.toDoubleOrNull()
        return if (num == null || num <= 0.0) {
            ValidationResult(false, "Ingresa un precio válido mayor a 0")
        } else ValidationResult(true)
    }


}
