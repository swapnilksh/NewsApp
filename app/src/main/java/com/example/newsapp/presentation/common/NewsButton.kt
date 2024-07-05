package com.example.newsapp.presentation.common


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.newsapp.ui.theme.Dimens
import com.example.newsapp.ui.theme.WhiteGray


@Composable
fun NewsButton(modifier: Modifier = Modifier, text: String, onButtonClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = Dimens.dimen6)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
        )
    }
}

@Composable
fun NewsTextButton(modifier: Modifier = Modifier, text: String, onButtonClick: () -> Unit) {
    TextButton(modifier = modifier, onClick = onButtonClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = WhiteGray
        )
    }
}