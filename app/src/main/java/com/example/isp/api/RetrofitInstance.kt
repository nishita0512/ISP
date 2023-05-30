package com.example.isp.api

import com.example.isp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val custApi: CustomerApi by lazy{
        retrofit.create(CustomerApi::class.java)
    }

    val planApi: PlanApi by lazy{
        retrofit.create(PlanApi::class.java)
    }

    val serverApi: ServerApi by lazy{
        retrofit.create(ServerApi::class.java)
    }

}