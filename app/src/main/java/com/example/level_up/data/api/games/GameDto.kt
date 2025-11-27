package com.example.level_up.data.api.games

import com.google.gson.annotations.SerializedName

data class GameDto(
    val id: Int,
    val name: String,

    @SerializedName("background_image")
    val backgroundImage: String?,

    val rating: Double?,

    @SerializedName("released")
    val releaseDate: String?,

    @SerializedName("metacritic")
    val metacriticScore: Int?
)