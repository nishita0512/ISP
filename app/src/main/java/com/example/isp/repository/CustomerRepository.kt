package com.example.isp.repository

import com.example.isp.api.RetrofitInstance
import com.example.isp.model.Customer
import okhttp3.RequestBody
import okhttp3.ResponseBody

class CustomerRepository {

    suspend fun getCustomerById(custId: Int): Customer {
        return RetrofitInstance.custApi.getCustomerById(custId)
    }

    suspend fun updateCustomerPassword(fields: HashMap<String, RequestBody>): ResponseBody{
        return RetrofitInstance.custApi.updateCustomerPassword(fields)
    }

    suspend fun updateCustomerPlan(fields: HashMap<String, RequestBody>): ResponseBody{
        return RetrofitInstance.custApi.updateCustomerPlan(fields)
    }

}