package com.example.mouvie.ui.widget.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mouvie.config.fixed.ApiValues
import com.example.mouvie.model.movie.entity.PersonEntity

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PersonCard(
    person: PersonEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(5.dp),
        onClick = onClick
    ) {
        GlideImage(
            model = ApiValues.IMAGE_W500_URL + person.picturePath,
            contentDescription = person.name,
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = person.name, Modifier.padding(5.dp).align(alignment = CenterHorizontally), color = Color.Black)
    }
}