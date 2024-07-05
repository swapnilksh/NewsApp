package com.example.newsapp.data

import androidx.annotation.DrawableRes

data class PageData(
    @DrawableRes val imageResource: Int,
    val title: String,
    val description: String
)