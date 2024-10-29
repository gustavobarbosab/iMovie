package com.github.gustavobarbosab.imovies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreen
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreenViewModel
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            IMoviesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val viewModel: HomeScreenViewModel by viewModels()
                        HomeScreen(
                            viewModel = viewModel
                        )
                    }

                }
            }
        }
    }
}
