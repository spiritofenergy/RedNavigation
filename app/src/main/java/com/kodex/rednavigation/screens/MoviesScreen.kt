package com.kodex.rednavigation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.kodex.rednavigation.R
import com.kodex.rednavigation.database.modals.Movies
import com.kodex.rednavigation.MainViewModel
import com.kodex.rednavigation.drawer.NavRoute


@Composable
fun MoviesScreen(navController: NavController, viewModel: MainViewModel) {


    val allMovies = viewModel.allMovies.observeAsState(listOf()).value
    allMovies.forEach { Log.d("checkData", "ID: ${it.id} name: ${it.name}") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        LazyColumn{
            items(allMovies.take(40)) { item ->
                NoteItem(
                    item = item,
                    navController= navController)
            }
        }
    }
}



@Composable
fun NoteItem(item: Movies, navController: NavController) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                navController.navigate(NavRoute.Detail.route + "/${item.id}")
            }
    ) {
        // Создаем строку в списке
        // Create a row in the  list
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.colorPrimaryDark))
                .padding(vertical = 16.dp)

        ) {
            // Подгружаем фото
            // Uploading photos
            Image(
                painter = rememberImagePainter(item.image.medium),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            // Создаем колонку с тремя строками
            // Create a column with  three row strings
            Column {
                // Строка Названия
                //Output of name string
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                // Строку Рейтинга
                // Output the string of Rating
                Row {
                    Text(
                        text = "Rating: ",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        color = Color.White,
                        text = item.rating.average.toString())
                    // item.genres.take(2).forEach { Text(text = " $it ") }
                }
                // Строку жанра по второму елементу списка
                // Output the genre string the second element of the list
                Row {
                    Text(text =  "Genre: ",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    item.genres.take(2).forEach{ Text(
                        color = Color.White,
                        text = " $it")}
                    // Text(text = item.rating.average.toString())

                }
                // Выводим дату премьеры
                // We output the premiere date
                Row {
                    Text(text =  "Premiered: ",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = item.premiered,
                        color = Color.White,
                    )

                }
            }
        }
    }
}

 @Preview(showBackground = true)
 @Composable
 fun MoviesScreenPreview() {
     val navController = rememberNavController()
     val viewModel = hiltViewModel<MainViewModel>()
 MoviesScreen(navController = navController,
     viewModel = viewModel)
 }


