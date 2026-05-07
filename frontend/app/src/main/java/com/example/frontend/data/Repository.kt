package com.example.frontend.data

import android.util.Log
import com.example.frontend.data.remote.MovieTrackerApi
import com.example.frontend.data.remote.TokenStore
import com.example.frontend.data.remote.dto.CommentRequestDto
import com.example.frontend.data.remote.dto.LoginRequestDto
import com.example.frontend.data.remote.dto.RatingRequestDto
import com.example.frontend.data.remote.dto.RegisterRequestDto
import com.example.frontend.data.remote.dto.UserUpdateRequestDto
import com.example.frontend.ui.data.Comment
import com.example.frontend.ui.data.Movie
import com.example.frontend.ui.rated_movies.RatedItemData
import com.example.frontend.ui.watchlist.WatchlistItemData
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Repository(
    private val api: MovieTrackerApi,
    private val tokenStore: TokenStore
) {
    private val watchlistDateFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
            .withZone(ZoneId.systemDefault())

    suspend fun login(username: String, password: String) {
        val resp = api.login(LoginRequestDto(username = username, password = password))
        tokenStore.setAccessToken(resp.accessToken)
    }

    suspend fun logout() {
        tokenStore.setAccessToken(null)
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ) {
        api.register(
            RegisterRequestDto(
                firstName = firstName,
                lastName = lastName,
                username = username,
                email = email,
                password = password
            )
        )
    }

    suspend fun listMovies(query: String?): List<Movie> {
        val endpoint = if (query.isNullOrBlank()) "GET /api/movies" else "GET /api/movies/search"
        val page =
            if (query.isNullOrBlank()) api.listMovies()
            else api.searchMovies(title = query)

        // #region agent log
        try {
            val sample = page.content.take(5).map { dto ->
                mapOf(
                    "id" to dto.id,
                    "title" to dto.title,
                    "imageUrl" to dto.imageUrl
                )
            }
            Log.d("DebugMovies", "H1/H2 repo.listMovies endpoint=$endpoint query='$query' sample=$sample")
        } catch (_: Exception) {}
        // #endregion agent log

        return page.content.map { it.toUi() }
    }

    suspend fun getMovie(movieId: Long): Movie = api.getMovie(movieId).toUi()

    suspend fun listComments(movieId: Long): List<Comment> =
        api.listComments(movieId).map { it.toUi() }

    suspend fun addComment(movieId: Long, text: String): Comment =
        api.addComment(movieId, CommentRequestDto(text = text)).toUi()

    suspend fun toggleWatchlist(movieId: Long, add: Boolean): Boolean {
        if (add) api.addToWatchlist(movieId) else api.removeFromWatchlist(movieId)
        return add
    }

    suspend fun listWatchlist(): List<WatchlistItemData> =
        api.listWatchlist().content.map {
            val formatted =
                try {
                    watchlistDateFormatter.format(Instant.parse(it.dateAdded))
                } catch (_: Exception) {
                    it.dateAdded
                }
            WatchlistItemData(
                movie = it.movie.toUi(),
                dateAdded = formatted
            )
        }

    suspend fun myRatings(): List<RatedItemData> =
        api.myRatings().content.map {
            RatedItemData(
                movie = it.movie.toUi(),
                rating = it.stars
            )
        }

    suspend fun rate(movieId: Long, stars: Int): Int {
        api.rateMovie(movieId, RatingRequestDto(stars = stars))
        return stars
    }

    suspend fun me(): UserProfile = api.me().toProfile()

    suspend fun updateMe(
        firstName: String?,
        lastName: String?,
        username: String?,
        email: String?
    ): UserProfile {
        val updated =
            api.updateMe(
                UserUpdateRequestDto(
                    firstName = firstName,
                    lastName = lastName,
                    username = username,
                    email = email
                )
            )
        return updated.toProfile()
    }
}

