package com.kodex.rednavigation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.kodex.rednavigation.MainViewModel
import com.kodex.rednavigation.utils.HtmlText
import com.kodex.rednavigation.R

@Composable
fun DetailScreen (navController: NavController, viewModel: MainViewModel, itemId: String) {
    // Text(text = "Hi. Im` DetailsScreen. itemId: ${itemId}" )
    val currentItem = viewModel.allMovies
        .observeAsState(listOf()).value
        .firstOrNull { it.id == itemId.toInt() }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 8.dp),
    ) {
      LazyColumn {
          item {
              Column(modifier = Modifier
                  .background(colorResource(
                  id = R.color.colorPrimaryDark)),
                  horizontalAlignment = Alignment.CenterHorizontally
              )
              {
                  Image(
                      painter = rememberImagePainter(currentItem?.image?.medium),
                      contentDescription = null,
                      modifier = Modifier
                          .size(512.dp)
                  )
                  Text(
                      text = currentItem?.name ?: "",
                      fontWeight = FontWeight.Bold,
                      fontSize = 32.sp,
                      color = Color.White,
                      modifier = Modifier
                          .padding(top = 16.dp)
                  )
                  Row(

                      modifier = Modifier
                          .padding(
                              top = 8.dp)
                  ) {
                      Text(
                          text = "Rating: ",
                          fontWeight = FontWeight.Bold,
                          color = Color.White,
                          fontSize = 18.sp
                      )
                      Text(
                          text = currentItem?.rating?.average.toString(),
                          fontWeight = FontWeight.Bold,
                          color = Color.White,
                          fontSize = 18.sp
                      )
                  }
                  Row(
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(top = 8.dp)
                  ){
                      Text(
                          text = "Genre: ",
                          fontWeight = FontWeight.Bold,
                          color = Color.White,
                          fontSize = 18.sp
                      )
                      currentItem?.genres?.take(2)?.forEach{
                          Text(
                              text = " $it ",
                              color = Color.White,
                              fontSize = 18.sp
                          )
                      }
                  }
                  HtmlText(modifier = Modifier
                      .padding(top = 8.dp),
                      html = currentItem?.summary ?: "",

                  )
              }

          }
          
      }
        }
    }






@Preview(showBackground = true)
@Composable
fun PrevAddScreen(){

    DetailScreen(navController = rememberNavController( ), itemId = String() ,
        viewModel = viewModel())
}


//        val currentItem = viewModel.allMovies
//            .observeAsState(listOf()).value
//            .firstOrNull { it.id == itemId.toInt() }
//        Surface(
//            modifier = Modifier
//                .fillMaxSize(1f)
//                .padding(vertical = 8.dp, horizontal = 16.dp),
//
//            )
//        {
//
//
//        }
//    }
//
//
//
//@Preview(showBackground = true)
//@Composable
//
//fun PrevAddScreen(){
//     NotesAppMVVMTheme {
//    //         val context = LocalContext.current
////          val mViewModal: MainViewModel =
////              viewModel(factory = MainViewModel.MainViewModelFactory(
////                  context
////                      .applicationContext as Application
////              )
//    // )
//    DetailsScreen(navController = rememberNavController())
//
//}
///*

//@Provides
//@Composable
//fun PrevAppScreen(){
//    DetailsScreen(navController = rememberNavController())
//}*/
