package com.example.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import com.example.newsapp.R
import com.example.newsapp.ui.theme.Dimens
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(error: LoadState.Error? = null) {

    var message by remember {
        mutableStateOf(parseErrorMessage(error = error))
    }

    var icon by remember {
        mutableStateOf(R.drawable.ic_network_error)
    }

    if (error == null) {
        message = "You have not saved news so far !"
        icon = R.drawable.ic_search_document
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(alphaAnimation = alphaAnimation, message = message, iconId = icon)

}

fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }

        is ConnectException -> {
            "Internet Unavailable."
        }

        else -> {
            "Unknown Error."
        }
    }
}

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    alphaAnimation: Float,
    message: String,
    iconId: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray,
            modifier = Modifier
                .size(Dimens.dimen120)
                .alpha(alphaAnimation)
        )
        Text(
            text = message,
            modifier = Modifier
                .padding(Dimens.dimen10)
                .alpha(alphaAnimation),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EmptyScreenPreview() {
    EmptyContent(
        alphaAnimation = 0.3f,
        message = "Internet Unavailable.",
        iconId = R.drawable.ic_network_error
    )
}