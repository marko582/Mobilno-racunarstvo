package com.example.frontend.ui.data

import com.example.frontend.ui.rated_movies.RatedItemData
import com.example.frontend.ui.watchlist.WatchlistItemData

data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String
)

object MovieRepo{
    val dune =  Movie("1", "Dune", "https://static.posters.cz/image/1300/122815.jpg")
    val batman = Movie("2", "The Batman", "https://vice-press.com/cdn/shop/files/batman-1989-movie-poster-raid71.jpg?v=1699015408&width=1024")
    val interstellar = Movie("3", "Interstellar", "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg")
    val oppenheimer = Movie("4", "Oppenheimer", "https://cdn.displate.com/artwork/270x380/2024-01-01/b02970480d20d4dd7b9e378e1852d702_211f4cbb87cbcbfbf0af3ac9c0c8fa1c.jpg")


    val dummyMovies = listOf(
        dune, batman, interstellar, oppenheimer
    )

    val dummyWatchlist = listOf(
        WatchlistItemData(dune, "12th Sept 2025."),
        WatchlistItemData(batman, "18th Nov 2023."),
        WatchlistItemData(interstellar, "7th Jan 2026."),
        WatchlistItemData(oppenheimer, "27th May 2025.")
    )

    val dummyRatedMovies = listOf(
        RatedItemData(dune, 4),
        RatedItemData(batman, 3),
        RatedItemData(interstellar, 5),
        RatedItemData(oppenheimer, 2)
    )
}