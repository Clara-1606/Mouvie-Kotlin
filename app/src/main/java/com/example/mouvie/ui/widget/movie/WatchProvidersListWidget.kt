package com.example.mouvie.ui.widget.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mouvie.config.fixed.ApiValues
import com.example.mouvie.model.movie.dto.WatchProviderDto

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WatchProvidersListWidget(
    title: String,
    watchProviders : List<WatchProviderDto>
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        LazyRow() {
                items(watchProviders) { watchProvider ->
                    watchProvider.logo_path?.let {
                        GlideImage(
                            model = ApiValues.IMAGE_ORIGINAL_URL + it,
                            contentDescription = "A streaming service",
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shadow(5.dp)
                        )
                    }
                }
        }
    }
}