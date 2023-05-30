package com.example.isp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isp.model.Customer
import com.example.isp.repository.CustomerRepository
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class CustomerViewModel(private val customerRepository: CustomerRepository): ViewModel() {

    val myResponse: MutableLiveData<Customer> = MutableLiveData()
    val myResponseString: MutableLiveData<String> = MutableLiveData()

    fun getCustomerById(custId: Int){
        viewModelScope.launch {
            try{
                val response = customerRepository.getCustomerById(custId)
                myResponse.value = response
            }
            catch(e: Exception){
                myResponse.value = Customer(-1,"","","",0.00,0.00,"","",0,0,0,0,0)
            }

        }
    }

    fun updateCustomerPassword(fields: HashMap<String, RequestBody>){
        viewModelScope.launch {
            try{
                val response = customerRepository.updateCustomerPassword(fields)
                myResponseString.value = response.string()
            }
            catch(e: Exception){
                e.printStackTrace()
                myResponseString.value = "Failed"
            }
        }
    }

    fun updateCustomerPlan(fields: HashMap<String, RequestBody>){
        viewModelScope.launch {
            try{
                val response = customerRepository.updateCustomerPlan(fields)
                myResponseString.value = response.string()
            }
            catch(e: Exception){
                e.printStackTrace()
                myResponseString.value = "Failed"
            }
        }
    }

}