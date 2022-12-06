package com.kodex.rednavigation.data.network

import com.kodex.rednavigation.data.modals.Movies
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(value = "/shows")
    suspend fun getAllMovies(): Response<List<Movies>>
}