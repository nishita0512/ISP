package com.example.isp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isp.adapters.PlanAdapter
import com.example.isp.databinding.ActivityBuyPlanBinding
import com.example.isp.model.Plan
import com.example.isp.repository.CustomerRepository
import com.example.isp.repository.PlanRepository
import com.example.isp.util.Constants
import com.example.isp.viewmodel.CustomerViewModel
import com.example.isp.viewmodel.CustomerViewModelFactory
import com.example.isp.viewmodel.PlanViewModel
import com.example.isp.viewmodel.PlanViewModelFactory
import com.razorpay.PaymentResultListener
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class BuyPlanActivity : AppCompatActivity(), PaymentResultListener {

    lateinit var binding: ActivityBuyPlanBinding
    lateinit var plansList: ArrayList<Plan>
    lateinit var planAdapter: PlanAdapter
    lateinit var planRepository: PlanRepository
    lateinit var planViewModelFactory: PlanViewModelFactory
    lateinit var planViewModel: PlanViewModel

    lateinit var customerRepository: CustomerRepository
    lateinit var customerViewModelFactory: CustomerViewModelFactory
    lateinit var customerViewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyPlanBinding.inflate(layoutInflater)

        planRepository = PlanRepository()
        planViewModelFactory = PlanViewModelFactory(planRepository)
        planViewModel = ViewModelProvider(this,planViewModelFactory)[PlanViewModel::class.java]
        planAdapter = PlanAdapter(this@BuyPlanActivity)

        customerRepository = CustomerRepository()
        customerViewModelFactory = CustomerViewModelFactory(customerRepository)
        customerViewModel = ViewModelProvider(this,customerViewModelFactory)[CustomerViewModel::class.java]

        binding.apply {

            planViewModel.getAllPlans()
            planViewModel.myResponseList.observe(this@BuyPlanActivity) {response ->
                if(response.isNotEmpty()){
                    plansList = response
                    planRecyclerView.layoutManager = LinearLayoutManager(this@BuyPlanActivity)
                    planAdapter.submitList(plansList)
                    planRecyclerView.adapter =planAdapter
                }
            }

        }
        customerViewModel.myResponseString.observe(this@BuyPlanActivity){response ->
            Toast.makeText(this@BuyPlanActivity,response,Toast.LENGTH_SHORT).show()
            Intent(this@BuyPlanActivity,MainActivity::class.java).also {
                startActivity(it)
            }
        }
        setContentView(binding.root)
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful", Toast.LENGTH_LONG).show()



        Intent(this,MainActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("RazorPay Error",p1.toString())
        Toast.makeText(this,"Payment Failed", Toast.LENGTH_LONG).show()

        //updating plan even after failure just for testing
        val fields = HashMap<String,RequestBody>()
        val custId = Constants.customer.custId
        val newPlanDuration: Long = 86400000 * Constants.curPlanSelected.duration.toLong()
        val newPlanEndDate: Long = Constants.customer.planEndDate + newPlanDuration
        val planId = Constants.curPlanSelected.id

        fields["custId"] = custId.toString().toRequestBody(MultipartBody.FORM)
        fields["planEndDate"] = newPlanEndDate.toString().toRequestBody(MultipartBody.FORM)
        fields["planId"] = planId.toString().toRequestBody(MultipartBody.FORM)
        fields["isActive"] = "1".toRequestBody(MultipartBody.FORM)

        customerViewModel.updateCustomerPlan(fields)

    }


}