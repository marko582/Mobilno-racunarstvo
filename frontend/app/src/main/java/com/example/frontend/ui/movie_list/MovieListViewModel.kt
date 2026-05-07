package com.example.frontend.ui.movie_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.AppGraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private var allMovies = emptyList<com.example.frontend.ui.data.Movie>()

    init {
        loadMovies()
    }

    fun loadMovies(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val q = _uiState.value.searchQuery
                // #region agent log
                Log.d("DebugMovies", "H3 vm.loadMovies query='$q'")
                // #endregion agent log

                val movies = AppGraph.repository.listMovies(query = q)
                allMovies = movies

                // #region agent log
                val sample = movies.take(5).map { m -> mapOf("id" to m.id, "title" to m.title, "imageUrl" to m.imageUrl) }
                Log.d("DebugMovies", "H1/H3 vm.loadMovies result count=${movies.size} sample=$sample")
                // #endregion agent log

                _uiState.update { it.copy(isLoading = false, movies = movies) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Failed to load movies") }
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String){
        _uiState.update { it.copy(searchQuery = newQuery) }

        // If backend supports search, fetch from server for consistency.
        viewModelScope.launch {
            try {
                // #region agent log
                Log.d("DebugMovies", "H3 vm.onSearchQueryChanged query='$newQuery'")
                // #endregion agent log

                val movies = AppGraph.repository.listMovies(query = newQuery)
                allMovies = movies
                _uiState.update { it.copy(movies = movies, errorMessage = null) }
            } catch (e: Exception) {
                // Fallback to local filtering if request fails
                val filtered =
                    if (newQuery.isEmpty()) allMovies
                    else allMovies.filter { it.title.contains(newQuery, ignoreCase = true) }
                _uiState.update { it.copy(movies = filtered, errorMessage = e.message) }
            }
        }
    }
}