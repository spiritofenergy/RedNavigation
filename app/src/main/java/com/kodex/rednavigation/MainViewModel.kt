package com.kodex.rednavigation

import android.app.Application
import android.util.Log
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kodex.rednavigation.database.firebase.AppFirebaseRepository
import com.kodex.rednavigation.database.modals.Movies
import com.kodex.rednavigation.database.network.ApiRepository
import com.kodex.rednavigation.database.room.dao.AppRoomDatabase
import com.kodex.rednavigation.database.room.repository.RoomRepository
import com.kodex.rednavigation.model.Note
import com.kodex.rednavigation.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository,
             application: Application): AndroidViewModel(application){

    val liveData = MutableLiveData<String>()
    private val context = application
    private val _allMovies = MutableLiveData<List<Movies>>()

    val loadingState  = MutableStateFlow(LoadingState.IDLE)

    val allMovies: MutableLiveData<List<Movies>>
        get() = _allMovies

    fun getAllMovies() {
        viewModelScope.launch {
            repository.getAllMovies().let {
                if (it.isSuccessful) {
                    _allMovies.postValue(it.body())
                } else {
                    Log.d("checkData", "filed to load movie: ${it.errorBody()}")
                }
            }
        }
    }


    fun initDatabase(type: String, onSuccess: () -> Unit) {
        Log.d("checkData", "MainViewModal InitDatabase with type $type")
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase(
                    { onSuccess() },
                    { Log.d("checkData", "Error4: ${it}") }
                )
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun reedAllNotes() = REPOSITORY.readAll

    fun updateNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun deleteNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }


    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)
        }catch (e: Exception){
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }

    }
}


