package com.example.projectscafold.data.api.retrofit

import android.arch.lifecycle.LiveData
import com.example.projectscafold.data.model.room.Repo
import com.example.projectscafold.data.api.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Sadman Sarar on 9/9/17.
 * Retrofit Service class
 */
interface WebService {

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String): LiveData<ApiResponse<List<Repo>>>

}