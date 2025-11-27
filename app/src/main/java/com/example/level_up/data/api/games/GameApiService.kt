package com.example.level_up.data.api.games

import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiService {

    @GET("games")
    suspend fun getTrendingGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 20
    ): GameResponseDto
}
