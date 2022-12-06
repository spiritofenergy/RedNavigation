package com.kodex.rednavigation

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodex.rednavigation.data.modals.Movies
import com.kodex.rednavigation.data.network.ApiRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository, application: Application): ViewModel(){

    val context = application
    private val _allMovies = MutableLiveData<List<Movies>>()

    val allMovies: MutableLiveData<List<Movies>>
        get() = _allMovies

    fun getAllMovies(){
        viewModelScope.launch {
            repository.getAllMovies().let {
                if (it.isSuccessful){
                    _allMovies.postValue(it.body())
                }else{
                    Log.d("checkData", "filed to load movie: ${it.errorBody()}")
                }
            }
        }
    }
}

fun initDatabase(type: String) {}