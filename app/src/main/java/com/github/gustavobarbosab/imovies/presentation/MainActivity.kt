package com.github.gustavobarbosab.imovies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.gustavobarbosab.imovies.presentation.screen.detail.DetailRoute
import com.github.gustavobarbosab.imovies.presentation.screen.detail.DetailScreen
import com.github.gustavobarbosab.imovies.presentation.screen.detail.DetailScreenViewModel
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeRoute
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreen
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeScreenViewModel
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMoviesTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeRoute
                ) {
                    composable<HomeRoute> { backStackEntry ->
                        val viewModel = hiltViewModel<HomeScreenViewModel>(backStackEntry)
                        HomeScreen(viewModel, navController)
                    }
                    composable<DetailRoute> { backStackEntry ->
                        val viewModel = hiltViewModel<DetailScreenViewModel>(backStackEntry)
                        DetailScreen(viewModel, navController)
                    }
                }
            }
        }
    }
}
