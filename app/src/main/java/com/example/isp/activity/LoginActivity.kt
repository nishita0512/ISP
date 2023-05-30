package com.example.isp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.isp.databinding.ActivityLoginBinding
import com.example.isp.model.Customer
import com.example.isp.repository.CustomerRepository
import com.example.isp.util.Constants
import com.example.isp.viewmodel.CustomerViewModel
import com.example.isp.viewmodel.CustomerViewModelFactory

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var customerRepository: CustomerRepository
    lateinit var customerViewModelFactory: CustomerViewModelFactory
    lateinit var customerViewModel: CustomerViewModel
    lateinit var sharedPreferences: SharedPreferences
    var custId = -1
    var userInputHashedPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("ISP",MODE_PRIVATE)
        val cId = sharedPreferences.getInt("custId",-1)
        if(cId!=-1){
            Constants.customer = Customer(cId,"","","",0.00,0.00,"","",0,0,0,0,0)
            Intent(this@LoginActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        customerRepository = CustomerRepository()
        customerViewModelFactory = CustomerViewModelFactory(customerRepository)
        customerViewModel = ViewModelProvider(this,customerViewModelFactory)[CustomerViewModel::class.java]

        binding.apply{

            btnLogin.setOnClickListener {
                btnLogin.isEnabled = false
                custId = edtTxtUserId.text.toString().toInt()
                val password = edtTxtPassword.text.toString()
                userInputHashedPassword = Constants.sha256(password)
                customerViewModel.getCustomerById(custId)
            }

            layoutSetPasswordButton.setOnClickListener {
                Intent(this@LoginActivity, ChangePasswordActivity::class.java).also {
                    startActivity(it)
                }
            }

            customerViewModel.myResponse.observe(this@LoginActivity) { response ->
                if (response.custId != -1) {
                    val hashedPassword = response.hashedPassword
                    Log.d("Login Response", hashedPassword)
                    if (hashedPassword == userInputHashedPassword) {
                        sharedPreferences.edit().putInt("custId",custId).apply()
                        Constants.customer = Customer(cId,"","","",0.00,0.00,"","",0,0,0,0,0)
                        Intent(this@LoginActivity, MainActivity::class.java).also {
                            startActivity(it)
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid Credentials",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_LONG)
                        .show()
                }
                btnLogin.isEnabled = true
            }

        }

    }
}