package com.example.isp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.isp.R
import com.example.isp.databinding.ActivityChangePasswordBinding
import com.example.isp.repository.CustomerRepository
import com.example.isp.util.Constants
import com.example.isp.viewmodel.CustomerViewModel
import com.example.isp.viewmodel.CustomerViewModelFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityChangePasswordBinding
    lateinit var customerRepository: CustomerRepository
    lateinit var customerViewModelFactory: CustomerViewModelFactory
    lateinit var customerViewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customerRepository = CustomerRepository()
        customerViewModelFactory = CustomerViewModelFactory(customerRepository)
        customerViewModel = ViewModelProvider(this,customerViewModelFactory)[CustomerViewModel::class.java]

        binding.apply {
            btnSubmitChangePass.setOnClickListener {
                btnSubmitChangePass.isEnabled = false
                val fields = HashMap<String,RequestBody>()
                val custId = edtTxtUserIdChangePass.text.toString()
                val oldPass = Constants.sha256(edtTxtOldPassChangePass.text.toString())
                val newPass = Constants.sha256(edtTxtNewPassChangePass.text.toString())
                fields["custId"] = custId.toRequestBody(MultipartBody.FORM)
                fields["oldHashedPassword"] = oldPass.toRequestBody(MultipartBody.FORM)
                fields["newHashedPassword"] = newPass.toRequestBody(MultipartBody.FORM)

                customerViewModel.updateCustomerPassword(fields)

            }
            customerViewModel.myResponseString.observe(this@ChangePasswordActivity){response ->
                Toast.makeText(this@ChangePasswordActivity,response,Toast.LENGTH_SHORT).show()
                btnSubmitChangePass.isEnabled = true
            }
        }

    }
}