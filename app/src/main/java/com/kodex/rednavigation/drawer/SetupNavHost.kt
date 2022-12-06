package com.kodex.rednavigation.drawer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kodex.rednavigation.screens.*
import com.kodex.rednavigation.MainViewModel
import com.kodex.rednavigation.R

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Home : NavDrawerItem("home", R.drawable.ic_home, "Home")
    object Music : NavDrawerItem("music", R.drawable.ic_music, "Music")
    object Movies : NavDrawerItem("movies", R.drawable.ic_movie, "Movies")
    object Books : NavDrawerItem("books", R.drawable.ic_book, "Books")
    object Profile : NavDrawerItem("profile", R.drawable.ic_profile, "Profile")
    object Detail : NavDrawerItem("detail", R.drawable.ic_profile, "Detail")
    object Settings : NavDrawerItem("settings", R.drawable.ic_settings, "Settings")
}
/*
sealed class Screens(val route: String) {
    object Splash : Screens(route = Constants.Screens.SPLASH_SCREEN)
    object Main : Screens(route = Constants.Screens.MAIN_SCREEN)
    object Detail : Screens(route = Constants.Screens.DETAIL_SCREEN)
}
*/

@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController, startDestination = NavDrawerItem.Movies.route) {
        composable(NavDrawerItem.Home.route) {
            HomeScreen()
        }
        composable(NavDrawerItem.Music.route) {
            MusicScreen()
        }
        composable(NavDrawerItem.Movies.route) {
            MoviesScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavDrawerItem.Books.route) {
            BooksScreen()
        }
        composable(NavDrawerItem.Detail.route + "/{Id}") { backStackEntry ->
            DetailScreen(navController = navController,
                viewModel = viewModel,
                itemId = backStackEntry?.arguments?.getString("Id") ?: "1" )
        }
        composable(NavDrawerItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavDrawerItem.Settings.route) {
            SettingsScreen()
        }
    }
}