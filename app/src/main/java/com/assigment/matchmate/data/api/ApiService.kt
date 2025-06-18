package com.assigment.matchmate.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getRandomUsers(
        @Query("results") results: Int = 10
    ): Response<RandomUserResponse>

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}
