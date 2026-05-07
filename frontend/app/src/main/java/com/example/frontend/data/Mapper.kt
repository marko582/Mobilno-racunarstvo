package com.example.frontend.data

import com.example.frontend.data.remote.dto.CommentDto
import com.example.frontend.data.remote.dto.MovieDto
import com.example.frontend.data.remote.dto.UserDto
import com.example.frontend.ui.data.Comment
import com.example.frontend.ui.data.Movie
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun MovieDto.toUi(): Movie =
    Movie(
        id = id,
        title = title,
        imageUrl = imageUrl,
        description = description,
        genre = genre,
        releaseYear = releaseYear,
        duration = duration,
        averageRating = averageRating,
        numberOfRatings = numberOfRatings
    )

private val commentDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
        .withZone(ZoneId.systemDefault())

fun CommentDto.toUi(): Comment =
    Comment(
        id = id,
        user = username,
        text = text,
        date =
            try {
                commentDateFormatter.format(Instant.parse(createdAt))
            } catch (_: Exception) {
                createdAt
            }
    )

data class UserProfile(
    val id: Long,
    val username: String,
    val email: String?
)

fun UserDto.toProfile(): UserProfile =
    UserProfile(
        id = id,
        username = username,
        email = email
    )

