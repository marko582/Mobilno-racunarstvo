package com.example.frontend.ui.rated_movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.Screen
import com.example.frontend.ui.components.RatedMovieListItem
import com.example.frontend.ui.components.WatchlistItem
import com.example.frontend.ui.data.MovieRepo
import com.example.frontend.ui.theme.FrontendTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatedMoviesScreen(
    state: RatedMoviesUiState,
    onSearchChanged: (String) -> Unit,
    onMovieClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "RATED MOVIES",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // SEARCH FIELD
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onSearchChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search your rated movies...") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface
                )
            )

            // LIST
            if (state.items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No rated movies yet", color = MaterialTheme.colorScheme.secondary)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.items) { item ->
                        RatedMovieListItem(
                            movie = item.movie,
                            rating = item.rating,
                            onClick = { onMovieClick(item.movie.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    FrontendTheme {
        val navController = rememberNavController()
        RatedMoviesScreen(RatedMoviesUiState(), {}, {})
    }
}