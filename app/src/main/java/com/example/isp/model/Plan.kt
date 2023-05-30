package com.example.isp.model

data class Plan(
    val id: Int,
    val name: String,
    val duration: Int,
    val speed: Int,
    val isLimited: Int,
    val dataLimit: Int,
    val price: Int
)