package com.kodex.rednavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.kodex.rednavigation.screens.*
import com.kodex.rednavigation.ui.theme.RedNavigationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedNavigationTheme{
                val navController = rememberNavController()
                val viewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, viewModel = viewModel)
            }

        }
    }
}
