package com.example.frontend.ui.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.ui.data.MovieRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private var allMovies = MovieRepo.dummyMovies

    init {
        loadMovies()
    }

    fun loadMovies(){
        _uiState.update { it.copy(isLoading = true) }

        //TODO Poziv ka bekendu
        viewModelScope.launch {
            delay(1000)
            _uiState.update {
                it.copy(
                    isLoading=false,
                    movies = MovieRepo.dummyMovies
                )
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String){
        _uiState.update { it.copy(searchQuery = newQuery) }

        val filteredList = if (newQuery.isEmpty()){
            allMovies
        } else {
            allMovies.filter { it.title.contains(newQuery, ignoreCase = true) }
        }

        _uiState.update { it.copy(movies = filteredList) }
    }
}