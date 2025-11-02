package com.example.level_up.data.repository

import com.example.level_up.data.model.Credential

class AuthRepository (
    private val validCredential: Credential =Credential.Admin
){
    fun login(username:String,password:String):Boolean{
        return username == validCredential.username && password==validCredential.password
    }

}