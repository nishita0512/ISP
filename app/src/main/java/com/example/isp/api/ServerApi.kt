package com.example.isp.api

import com.example.isp.model.Server
import retrofit2.http.GET

interface ServerApi {

    @GET("getAllServers")
    suspend fun getAllServers(): ArrayList<Server>

}