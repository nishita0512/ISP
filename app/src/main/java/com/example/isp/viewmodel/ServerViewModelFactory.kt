package com.example.isp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.isp.repository.ServerRepository

class ServerViewModelFactory(private val serverRepository: ServerRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ServerViewModel(serverRepository) as T
    }

}