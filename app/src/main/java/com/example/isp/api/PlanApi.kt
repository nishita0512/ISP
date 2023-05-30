package com.example.isp.api

import com.example.isp.model.Plan
import retrofit2.http.GET
import retrofit2.http.Part

interface PlanApi {

    @GET("getAllPlans")
    suspend fun getAllPlans(): ArrayList<Plan>

}