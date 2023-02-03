package com.kodex.rednavigation.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kodex.rednavigation.MainViewModel
import com.kodex.rednavigation.R
import com.kodex.rednavigation.drawer.NavRoute
import com.kodex.rednavigation.utils.*
import com.kodex.rednavigation.utils.Constants.Keys
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterialApi::class)
@Composable
@ExperimentalMaterialApi
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf(Keys.EMPTY) }
    var password by remember { mutableStateOf(Keys.EMPTY) }
    val context = LocalContext.current
    val auth: FirebaseAuth = Firebase.auth

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            viewModel.signWithCredential(credential)
        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }

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
                        .padding(all = 32.dp),

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
                                viewModel.initDatabase(TYPE_FIREBASE) {
                                 DB_TYPE.value = TYPE_FIREBASE
                                    navController.navigate("home")
                                }
                        },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(
                                id = R.color.colorPrimaryDark
                            )),

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
                         viewModel.initDatabase(type = String()) {
                             com.kodex.rednavigation.utils.DB_TYPE.value =
                                 com.kodex.rednavigation.utils.TYPE_FIREBASE
                             navController.navigate(com.kodex.rednavigation.utils.Constants.Screens.MAIN_SCREEN)
                         }
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
                        val gso = GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("164037452208-8llc2gebnff3nsuso46daoppm2jtd0q6.apps.googleusercontent.com")
                            .requestEmail()
                            .build()
                         val googleSignInClient = GoogleSignIn.getClient(context, gso)
                         launcher.launch(googleSignInClient.signInIntent)
                            Log.d("CheckData", "${auth.currentUser?.displayName }")
                        Toast.makeText(context, "Google     ${auth.currentUser?.displayName }     ", Toast.LENGTH_SHORT).show()
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



