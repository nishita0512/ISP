package com.example.isp.api

import com.example.isp.model.Customer
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface CustomerApi {

    @GET("getCustomerById/{custId}")
    suspend fun getCustomerById(@Path("custId") custId: Int): Customer

    @Multipart
    @POST("updateCustomerPassword")
    suspend fun updateCustomerPassword(@PartMap fields: HashMap<String, RequestBody>): ResponseBody

    @Multipart
    @POST("updateCustomerPlan")
    suspend fun updateCustomerPlan(@PartMap fields: HashMap<String, RequestBody>): ResponseBody


}