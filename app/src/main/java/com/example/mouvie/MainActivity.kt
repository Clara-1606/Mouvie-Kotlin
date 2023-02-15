package com.example.mouvie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mouvie.ui.navigation.graph.RootNavigationGraph
import com.example.mouvie.ui.theme.MouvieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MouvieTheme {
                RootNavigationGraph()
            }
        }
    }
}