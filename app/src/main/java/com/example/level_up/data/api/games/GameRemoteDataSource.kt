package com.example.level_up.data.api.games

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameRemoteDataSource(
    private val apiKey: String,
    private val service: GameApiService
) {

    companion object {
        private const val BASE_URL = "https://api.rawg.io/api/"

        fun create(apiKey: String): GameRemoteDataSource {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GameApiService::class.java)
            return GameRemoteDataSource(apiKey, service)
        }
    }

    suspend fun getTrendingGames(pageSize: Int = 20): GameResponseDto {
        return service.getTrendingGames(
            apiKey = apiKey,
            pageSize = pageSize
        )
    }
}
