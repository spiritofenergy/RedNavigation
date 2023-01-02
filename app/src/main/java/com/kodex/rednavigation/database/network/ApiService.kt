package com.kodex.rednavigation.database.network

import com.kodex.rednavigation.database.modals.Movies
import retrofit2.Response
import retrofit2.http.GET

interface       ApiService {
    @GET(value = "/shows")
    suspend fun getAllMovies(): Response<List<Movies>>
}