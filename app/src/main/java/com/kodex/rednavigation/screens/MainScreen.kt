package com.kodex.rednavigation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kodex.rednavigation.R
import com.kodex.rednavigation.drawer.Drawer
import com.kodex.rednavigation.drawer.Navigation
import com.kodex.rednavigation.drawer.TopBar

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerBackgroundColor = colorResource(id = R.color.colorPrimaryDark),
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        },
        backgroundColor = colorResource(id = R.color.colorPrimaryDark)
    ) { padding ->  // We need to pass scaffold's inner padding to content. That's why we use Box.
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController = navController)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    MainScreen()
}





