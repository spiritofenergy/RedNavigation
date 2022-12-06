package com.kodex.rednavigation.screens

import android.provider.SyncStateContract
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.kodex.rednavigation.MainViewModel
import com.kodex.rednavigation.R
import com.kodex.rednavigation.utils.*
import com.kodex.rednavigation.utils.Constants.Keys
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
@ExperimentalMaterialApi
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var password by remember { mutableStateOf(Constants.Keys.EMPTY) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp
        ),
        sheetContent = {
            Surface(
                color = colorResource(id = R.color.colorPrimary)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 32.dp)
                ) {
                    Text(
                        text = Keys.ENTER_LOG_IN,
                        fontSize = 16.sp,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it },
                        label = { Text(
                            color = White,
                            text = Keys.LOGIN_TEXT) },
                        isError = login.isEmpty()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(
                            color = White,
                            text = Keys.PASSWORD_TEX) },
                        isError = password.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 26.dp),
                            onClick = {
                            LOGIN = login
                            PASSWORD = password
                            //    viewModel.initDataBase(TYPE_FIREBASE)
                            {
                                DB_TYPE.value = TYPE_FIREBASE
                                navController.navigate(Constants.Screens.MAIN_SCREEN)
                            }
                        },
                                    enabled = login.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text(
                            color = White,
                            text = Keys.SING_IN)

                    }
                }
            }


        }) {

        val context = LocalContext.current
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.colorPrimaryDark))
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Регистрация",
                    fontWeight = FontWeight.Bold,
                    color = White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Button(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    onClick = {
                              coroutineScope.launch {
                                  bottomSheetState.show()
                              }
                        // viewModel.initDatabase()
                    },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(
                            id = R.color.colorPrimary
                        )
                    )
                )
                {
                    Text(
                        text = "Firebase",
                        fontWeight = FontWeight.Bold,
                        color = White,
                        fontSize = 18.sp
                    )
                }
                Button(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    onClick = {

                        Toast.makeText(context, "Google          ", Toast.LENGTH_SHORT).show()
                    }, colors = ButtonDefaults.buttonColors(
                        colorResource(
                            id = R.color.colorPrimary
                        )
                    )
                )
                {
                    Text(
                        text = "Google",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }


    @Composable
    fun ProfileScreenPreview() {
        val navController = rememberNavController()
        ProfileScreen(navController = navController, viewModel = viewModel)
    }
}