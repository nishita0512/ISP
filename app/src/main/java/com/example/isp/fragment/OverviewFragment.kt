package com.example.isp.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.isp.R
import com.example.isp.activity.BuyPlanActivity
import com.example.isp.activity.LoginActivity
import com.example.isp.databinding.FragmentOverviewBinding
import com.example.isp.repository.CustomerRepository
import com.example.isp.util.Constants
import com.example.isp.viewmodel.CustomerViewModel
import com.example.isp.viewmodel.CustomerViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class OverviewFragment : Fragment() {

    lateinit var binding: FragmentOverviewBinding
    lateinit var customerRepository: CustomerRepository
    lateinit var customerViewModelFactory: CustomerViewModelFactory
    lateinit var customerViewModel: CustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater,container,false)

        val custId = Constants.customer.custId
        if(custId==-1){
            Intent(requireContext(), LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        customerRepository = CustomerRepository()
        customerViewModelFactory = CustomerViewModelFactory(customerRepository)
        customerViewModel = ViewModelProvider(this,customerViewModelFactory)[CustomerViewModel::class.java]

        binding.apply {

            customerViewModel.getCustomerById(custId)
            customerViewModel.myResponse.observe(requireActivity()) { response ->
                Constants.customer = response
                if(response.isActive == 1){
                    txtActiveInActive.text = "Active"
                    txtActiveInActive.background = ResourcesCompat.getDrawable(requireActivity().resources,
                        R.drawable.green_background,requireActivity().theme)
                }
                else{
                    txtActiveInActive.text = "InActive"
                    txtActiveInActive.background = ResourcesCompat.getDrawable(requireActivity().resources,
                        R.drawable.red_background,requireActivity().theme)
                }

                val dataUsed = response.dataUsed
                if(dataUsed<1024){
                    txtDataUsedValue.text = "$dataUsed Bytes"
                }
                else if(dataUsed<1048576){
                    val kbUsed = response.dataUsed/1024
                    txtDataUsedValue.text = "$kbUsed KB"
                }
                else if(dataUsed<1073741824){
                    val mbUsed = response.dataUsed/1048576
                    txtDataUsedValue.text = "$mbUsed MB"
                }
                else{
                    val gbUsed = response.dataUsed/1073741824
                    txtDataUsedValue.text = "$gbUsed GB"
                }


                txtPlanName.text = response.planId.toString()
                val formatter = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
                val dateString = formatter.format(Date(response.planEndDate))
                txtPlanTill.text = dateString

                txtName.text = "Name: ${response.name}"
                txtAddress.text = "Address: ${response.address}"

            }

            btnBuy.setOnClickListener {

                Intent(requireContext(),BuyPlanActivity::class.java).also{
                    startActivity(it)
                }

            }

        }

        return binding.root
    }

}