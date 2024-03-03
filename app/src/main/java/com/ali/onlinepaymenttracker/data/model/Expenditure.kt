package com.ali.onlinepaymenttracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenditures")
data class Expenditure(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Int,
    val note: String,
    val date: String,
    val time: String,
    val location: String
)