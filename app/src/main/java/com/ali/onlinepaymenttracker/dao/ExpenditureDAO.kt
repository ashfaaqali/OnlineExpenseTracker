package com.ali.onlinepaymenttracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ali.onlinepaymenttracker.data.model.Expenditure

@Dao
interface ExpenditureDAO {

    @Query("SELECT * FROM expenditures ORDER BY date ASC")
    fun getAllExpenditures(): LiveData<List<Expenditure>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpenditure(expense: Expenditure)
}