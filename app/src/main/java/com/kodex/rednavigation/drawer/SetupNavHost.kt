package com.kodex.rednavigation.drawer

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kodex.rednavigation.screens.*
import com.kodex.rednavigation.MainViewModel
import com.kodex.rednavigation.R

sealed class NavRoute(var route: String, var icon: Int, var title: String) {
    object Home : NavRoute("home", R.drawable.ic_home, "Home")
    object Music : NavRoute("music", R.drawable.ic_music, "Music")
    object Movies : NavRoute("movies", R.drawable.ic_movie, "Movies")
    object Books : NavRoute("books", R.drawable.ic_book, "Books")
    object Profile : NavRoute("profile", R.drawable.ic_profile, "Profile")
    object Detail : NavRoute("detail", R.drawable.ic_profile, "Detail")
    object Settings : NavRoute("settings", R.drawable.ic_settings, "Settings")
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController, startDestination = NavRoute.Profile.route) {
        composable(NavRoute.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Music.route) {
            MusicScreen(navController = navController, viewModel = viewModel)
        }

        composable(NavRoute.Movies.route) {
            MoviesScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Books.route) {
            BooksScreen()
        }
        composable(NavRoute.Detail.route + "/{Id}") { backStackEntry ->
            DetailScreen(navController = navController,
                viewModel = viewModel,
                itemId = backStackEntry?.arguments?.getString("Id") ?: "1" )
        }
        composable(NavRoute.Profile.route) {
            ProfileScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Settings.route) {
            SettingsScreen()
        }
    }
}