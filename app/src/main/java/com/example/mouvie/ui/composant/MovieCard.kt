package com.example.mouvie.ui.composant

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.mouvie.model.favorite.Favorite
import coil.compose.rememberImagePainter
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource


@Composable
fun MovieGrid(movies: List<Favorite>, modifier: Modifier = Modifier, onDelete: (Favorite) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, onDelete = { onDelete(movie)} )
        }
    }
}

@Composable
fun MovieCard(movie: Favorite, modifier: Modifier = Modifier, onDelete: () -> Unit) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Load image with Glide
        Image(
            painter = rememberImagePainter(
                data = movie.posterPath,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    setShowDialog(true)
                },
            contentScale = ContentScale.Crop
        )

        // Display movie title
        Text(
            text = movie.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                setShowDialog(false)
            },
            title = { Text(stringResource(com.example.mouvie.R.string.action)) },
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            confirmButton = {
                TextButton(
                    onClick = {
                        // Delete the image from the database
                        onDelete()
                        setShowDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White // Color Text
                    )
                ) {
                    Text(stringResource(com.example.mouvie.R.string.delete))

                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White, // Color Text
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),

                ) {
                    Text(stringResource(com.example.mouvie.R.string.cancel))
                }
            }
        )
    }
}
