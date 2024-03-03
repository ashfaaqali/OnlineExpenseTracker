package com.ali.onlinepaymenttracker.data.model

data class SmsData(
    val amount: Int,
    val date: String,
    val time: String,
    val fromNotification: Boolean = false
)