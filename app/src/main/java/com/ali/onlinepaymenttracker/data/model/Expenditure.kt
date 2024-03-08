package com.ali.onlinepaymenttracker.data.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "expenditures")
//@TypeConverters(DateConverter::class)
data class Expenditure(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Int,
    val note: String,
    val dateInMills: Long,
    val time: String,
    val location: String
)

//class DateConverter {
//    @TypeConverter
//    fun toDate(timestamp: Long?): Date? {
//        val date = timestamp?.let { Date(it) }
//        Log.e("Expenditure", "Converted to Date: $date")
//        return date
//    }
//
//    @TypeConverter
//    fun toTimestamp(date: Date?): Long? {
//        Log.e("Expenditure", "Converted to Timestamp: ${date?.time}")
//        return date?.time
//    }
//}