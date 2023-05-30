package com.example.isp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isp.model.Customer
import com.example.isp.model.Plan
import com.example.isp.repository.CustomerRepository
import com.example.isp.repository.PlanRepository
import kotlinx.coroutines.launch

class PlanViewModel(private val planRepository: PlanRepository): ViewModel() {

    val myResponseList: MutableLiveData<ArrayList<Plan>> = MutableLiveData()

    fun getAllPlans(){
        viewModelScope.launch {
            try{
                val response = planRepository.getAllPlans()
                println("GetAllPlans"+response[0])
                myResponseList.value = response
            }
            catch(e: Exception){
                Log.d("GetAllPlans",e.message.toString())
                myResponseList.value = ArrayList()
            }

        }
    }

}