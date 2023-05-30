package com.example.isp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.isp.repository.CustomerRepository
import com.example.isp.repository.PlanRepository

class PlanViewModelFactory(private val planRepository: PlanRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlanViewModel(planRepository) as T
    }

}