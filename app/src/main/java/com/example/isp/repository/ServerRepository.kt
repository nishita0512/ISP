package com.example.isp.repository

import com.example.isp.api.RetrofitInstance
import com.example.isp.model.Server

class ServerRepository {

    suspend fun getAllServers(): ArrayList<Server> {
        return RetrofitInstance.serverApi.getAllServers()
    }

}