package com.example.projectsample

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Composable
fun NewsCard(news: News, modifier: Modifier) {
    Surface(shape = RoundedCornerShape(8.dp), elevation = 8.dp, modifier = modifier) {
        val image = imageResource(news.imageResource)
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(asset = image, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(144.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                Text(news.title, style = MaterialTheme.typography.h4, modifier = Modifier.padding(bottom = 4.dp))
                for (ingredient in news.ingredients) {
                    Text("• $ingredient")
                }
            }
        }
    }
}

@Composable
@Preview
fun DefaultRecipeCard() {
    MaterialTheme {
        NewsCard(defaultNewes[0], Modifier.padding(16.dp))
    }
}
