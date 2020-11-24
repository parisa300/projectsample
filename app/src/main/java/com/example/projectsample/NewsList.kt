package com.example.projectsample

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NewsList(recipes: List<News>) {
    LazyColumnFor(items = recipes) { item ->
        NewsCard(item, Modifier.padding(16.dp))
    }
}
