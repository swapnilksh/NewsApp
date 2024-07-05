package com.example.newsapp.common.extension

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.colorResource
import com.example.newsapp.R
import com.example.newsapp.ui.theme.Black
import com.example.newsapp.ui.theme.Dimens

@Composable
fun Modifier.shimmerEffect(): Modifier {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    return this then background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
}

fun Modifier.searchBarBorder(): Modifier = composed {
    if (!isSystemInDarkTheme()) {
        border(
            width = Dimens.dimen2,
            color = Black,
            shape = MaterialTheme.shapes.medium
        )
    } else {
        this
    }
}