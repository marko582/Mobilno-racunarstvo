package com.example.frontend.data.remote.dto

data class LoginRequestDto(
    val username: String,
    val password: String
)

data class RegisterRequestDto(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String
)

data class RatingRequestDto(
    val stars: Int
)

data class CommentRequestDto(
    val text: String
)

data class UserUpdateRequestDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val email: String? = null
)

