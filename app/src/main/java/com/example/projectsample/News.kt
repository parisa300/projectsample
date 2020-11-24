package com.example.projectsample

import androidx.annotation.DrawableRes

data class News( @DrawableRes
                 val imageResource: Int,
                 val title: String,
                 val ingredients: List<String>)

