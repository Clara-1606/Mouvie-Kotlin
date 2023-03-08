package com.example.mouvie.ui.widget.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mouvie.model.movie.entity.PersonEntity

@Composable
fun HorizontalPersonList(
    title: String,
    persons: List<PersonEntity>
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        LazyRow(
            Modifier.align(Alignment.CenterHorizontally),
        ) {
            items(persons) { person ->
                PersonCard(person, onClick = {})
            }
        }
    }
}