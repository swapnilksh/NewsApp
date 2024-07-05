package com.example.newsapp.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.R
import com.example.newsapp.ui.theme.Dimens
import com.example.newsapp.data.PageData
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    pageData: PageData
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = pageData.imageResource),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimens.dimen24))
        Text(
            text = pageData.title,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = colorResource(id = R.color.display_small),
            modifier = Modifier.padding(horizontal = Dimens.dimen24)
        )
        Spacer(modifier = Modifier.height(Dimens.dimen12))

        Text(text = pageData.description,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium),
            modifier = Modifier.padding(horizontal = Dimens.dimen24))
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnBoardingPagePreview() {
    NewsAppTheme {
        OnBoardingPage(
            pageData = PageData(
                title = "Lorem Ipsum is simply dummy",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                imageResource = R.drawable.onboarding1
            )
        )
    }
}