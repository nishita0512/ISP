package com.example.isp.model

data class Customer(
    val custId: Int,
    val name: String,
    val hashedPassword: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val phoneNo: String,
    val email: String,
    val customerSince: Long,
    val planEndDate: Long,
    val planId: Int,
    val isActive: Int,
    val dataUsed: Long
)