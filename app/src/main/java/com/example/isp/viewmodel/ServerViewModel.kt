package com.example.isp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isp.model.Server
import com.example.isp.repository.ServerRepository
import kotlinx.coroutines.launch

class ServerViewModel(private val serverRepository: ServerRepository): ViewModel() {

    val myResponseList: MutableLiveData<ArrayList<Server>> = MutableLiveData()

    fun getAllServers(){
        viewModelScope.launch {
            try{
                val response = serverRepository.getAllServers()
                println("GetAllServers"+response[0])
                myResponseList.value = response
            }
            catch(e: Exception){
                Log.d("GetAllServers",e.message.toString())
                myResponseList.value = ArrayList()
            }

        }
    }

}