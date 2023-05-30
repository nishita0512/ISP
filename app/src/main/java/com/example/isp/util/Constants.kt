package com.example.isp.util

import com.example.isp.model.Customer
import com.example.isp.model.Plan
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object Constants {

    const val BASE_URL = "http://192.168.0.110:3000/"
    var customer = Customer(-1,"","","",0.00,0.00,"","",0,0,0,0,0)
    var curPlanSelected = Plan(-1,"",0,0,0,0,0)

    fun sha256(input: String): String {
        val bytes = input.toByteArray(StandardCharsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

}