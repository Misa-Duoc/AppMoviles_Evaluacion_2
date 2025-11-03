package com.example.level_up.data.model

data class Credential(val username:String, val password:String){
    // objeto que permite acceder a la instancia de la clase
    companion object{
        val Admin =Credential(username="admin", password="admin")
    }
}