package com.example.mouvie.ui.widget.movie.common

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipHorizontalList(content: List<String>) {
    LazyRow(content = {
        items(content) { item ->
            AssistChip(
                onClick = {},
                label = { Text(item, style = MaterialTheme.typography.bodyMedium) },
            )
        }
    })
}