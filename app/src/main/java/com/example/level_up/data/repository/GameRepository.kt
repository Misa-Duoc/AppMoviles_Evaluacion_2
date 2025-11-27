package com.example.level_up.repository

import com.example.level_up.data.api.games.GameRemoteDataSource
import com.example.level_up.data.api.games.GameDto

class GameRepository(
    private val remoteDataSource: GameRemoteDataSource
) {

    suspend fun getTrendingGames(limit: Int = 20): List<GameDto> {
        val response = remoteDataSource.getTrendingGames(limit)
        return response.results
    }
}
