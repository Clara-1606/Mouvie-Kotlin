package com.example.mouvie.ui.widget.movie

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mouvie.model.movie.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: Movie) {
    Card() {
        Text(text = movie.title)
    }
}