package com.example.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.newsapp.R
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.presentation.common.ArticlesListWithoutPagination
import com.example.newsapp.ui.theme.Dimens


@Composable
fun BookmarkScreen(
    uiState: BookmarkUiState,
    navigateToDetails: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                top = Dimens.dimen24,
                start = Dimens.dimen24,
                end = Dimens.dimen24,
            )
    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(
                id = R.color.text_title
            )
        )

        Spacer(modifier = Modifier.height(Dimens.dimen24))

        ArticlesListWithoutPagination(
            articles = uiState.articles,
            onClick = navigateToDetails
        )
    }
}