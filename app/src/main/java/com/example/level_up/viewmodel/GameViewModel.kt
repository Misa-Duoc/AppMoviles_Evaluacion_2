package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.api.games.GameDto
import com.example.level_up.data.api.games.GameRemoteDataSource
import com.example.level_up.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class GameNewsUiState(
    val isLoading: Boolean = false,
    val games: List<GameDto> = emptyList(),
    val errorMessage: String? = null
)

class GameViewModel : ViewModel() {

    private val apiKey = "5dc6ed987cbe4ddeb8785783f71954c5" // api key real generada (correo: liquorxce, q se me olvida)

    private val remoteDataSource = GameRemoteDataSource.create(apiKey)
    private val repository = GameRepository(remoteDataSource)

    private val _uiState = MutableStateFlow(GameNewsUiState())
    val uiState: StateFlow<GameNewsUiState> = _uiState

    init {
        loadTrendingGames()
    }

    fun loadTrendingGames() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            try {
                val games = repository.getTrendingGames()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    games = games,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    games = emptyList(),
                    errorMessage = "Error al cargar juegos: ${e.message}"
                )
            }
        }
    }
}
