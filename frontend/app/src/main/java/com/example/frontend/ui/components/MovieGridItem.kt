package com.example.frontend.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.frontend.ui.data.Movie

@Composable
fun MovieGridItem(movie: Movie, onClick: () -> Unit) {
    val imageUrl = movie.imageUrl?.trim()

    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(0.7f)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        if (imageUrl.isNullOrBlank()) {
            // Backend returned null/blank imageUrl for this movie.
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
            ) {}
            Log.w("MovieGridItem", "Missing imageUrl. title='${movie.title}' id=${movie.id}")
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.errorContainer),
                onError = {
                    Log.e(
                        "MovieGridItem",
                        "Image load failed. title='${movie.title}' id=${movie.id} url='$imageUrl'",
                        it.result.throwable
                    )
                }
            )
        }
    }
}