package com.example.isp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.example.isp.activity.MainActivity
import com.example.isp.databinding.PlanSingleRowBinding
import com.example.isp.model.Plan
import com.example.isp.util.Constants
import com.google.gson.JsonObject
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PlanAdapter(val act: Activity): ListAdapter<Plan, RecyclerView.ViewHolder>(Diff) {

    class PlanViewHolder(private val binding: PlanSingleRowBinding, private val act: Activity): RecyclerView.ViewHolder(binding.root){
        fun bind(plan: Plan){
            binding.apply{
                txtPlanNameSingleRow.text = plan.name
                txtPlanDurationSingleRow.text = "Duration: ${plan.duration} days"
                txtPlanSpeedSingleRow.text = "Speed: ${plan.speed} Mbps"
                txtPlanPriceSingleRow.text = "Rs.${plan.price}"
                parentLayoutPlanSingleRow.setOnClickListener {

                    Constants.curPlanSelected = plan
                    pay(plan.price*100)
                }
            }
        }
        private fun pay(price: Int){
            val chkOut = Checkout()
            val jsonObj = JSONObject()
            try{
                jsonObj.put("name","ISP")
                jsonObj.put("description","Buy Plan")
                jsonObj.put("image","")
                jsonObj.put("currency","INR")
                jsonObj.put("amount",price.toString())

                val prefill = JSONObject()
                prefill.put("contact","9890855980")
                prefill.put("email","vineshchtr@gmail.com")
                jsonObj.put("prefill",prefill)

                chkOut.open(act,jsonObj)
            }
            catch(e: Exception){
                Toast.makeText(act,"Some Error Occurred",Toast.LENGTH_LONG).show()
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlanViewHolder(
            PlanSingleRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            act
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plan = getItem(position)
        val viewHolder = holder as PlanViewHolder
        viewHolder.bind(plan)
    }

    private object Diff : DiffUtil.ItemCallback<Plan>() {
        override fun areItemsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return oldItem == newItem
        }

    }

}