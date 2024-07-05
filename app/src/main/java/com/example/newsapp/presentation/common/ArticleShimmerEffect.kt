package com.example.newsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.newsapp.common.extension.shimmerEffect
import com.example.newsapp.ui.theme.Dimens

@Composable
fun ArticleShimmerEffect(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(Dimens.dimen100)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = Dimens.dimen4)
                .height(Dimens.dimen100)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dimen30)
                    .padding(horizontal = Dimens.dimen24)
                    .shimmerEffect()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = Dimens.dimen24)
                        .height(Dimens.dimen16)
                        .shimmerEffect()
                )
            }
        }
    }
}
