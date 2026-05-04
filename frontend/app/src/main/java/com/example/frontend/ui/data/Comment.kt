package com.example.frontend.ui.data

data class Comment(val user: String, val text: String, val date: String)

object commentRepo{
    val dummyComments = listOf(
        Comment("Marko", "Fenomenalan film, vizuelno remek-delo!", "pre 2h"),
        Comment("Jelena", "Malo sporiji tempo, ali gluma je 10/10.", "pre 5h")
    )
}
