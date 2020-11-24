package com.example.projectsample.model



data class NewsResponce(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)