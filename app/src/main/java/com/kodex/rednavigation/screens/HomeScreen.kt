package com.kodex.rednavigation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kodex.rednavigation.MainViewModel
import com.kodex.rednavigation.R
import com.kodex.rednavigation.drawer.NavRoute
import com.kodex.rednavigation.model.Note
import com.kodex.rednavigation.ui.theme.RedNavigationTheme
import com.kodex.rednavigation.utils.Constants
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    val notes = viewModel.reedAllNotes().observeAsState(listOf()).value
    var auth: FirebaseAuth = Firebase.auth
    title = auth.currentUser?.displayName.toString()

    val context = LocalContext.current
    lateinit var mViewModel: MainViewModel


    RedNavigationTheme {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetState.show()
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add_icons",
                            tint = Color.White
                        )
                    }
                }
            ) {
                LazyColumn {
                    items(notes) { note ->
                        NoteItem(note = note,navController = navController)
                    }
                }
            }
        }
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetElevation = 5.dp,
            sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetContent = {
                Surface {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 32.dp)
                    ) {
                        Text(
                            text = Constants.Keys.ADD_BUSINESS,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        OutlinedTextField(
                            value =   title ,
                            onValueChange = { title = it },
                            label = { Text(text = Constants.Keys.TITLE) },
                            isError = title.isEmpty()
                        )
                        OutlinedTextField(
                            value = subtitle,
                            onValueChange = { subtitle = it  },
                            label = { Text(text = Constants.Keys.SUBTITLE) },
                            isError = subtitle.isEmpty()
                        )
                        Button(
                            modifier = Modifier.padding(top = 26.dp),
                            onClick = {
                                Log.d("checkData", "$title # $subtitle")
                                    viewModel.addNote(note = Note(title = title, subtitle = subtitle
                                    )

                                    ) {
                                        Log.d("checkData", "$title # $subtitle")
                                }
                            },
                        ) {
                            Text(text = Constants.Keys.ADD)

                        }
                    }
                }

            }) {
        }
    }



@Composable
fun NoteItem(note: Note, navController: NavHostController) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                navController.navigate(NavRoute.Detail.route + "/${note.id}")
            }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.colorPrimary))
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = rememberImagePainter(note),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Column() {
                Text(
                    text = note.title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                    )
                Row() {
                    Text(
                        text = "Rating",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        color = Color.White,
                        text = note.title
                    )
                }
                Row() {
                    Text(
                        text = "Genre",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    note.subtitle.take(2).forEach {
                        Text(
                            color = Color.White,
                            text = "$note")}
                }
                Row {
                    Text(
                        text = "Premiered: ",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = note.subtitle,
                        color = Color.White
                    )
                }

            }

        }
    }


}