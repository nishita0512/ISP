package com.example.isp.repository

import com.example.isp.api.RetrofitInstance
import com.example.isp.model.Customer
import com.example.isp.model.Plan

class PlanRepository {

    suspend fun getAllPlans(): ArrayList<Plan> {
        return RetrofitInstance.planApi.getAllPlans()
    }

}