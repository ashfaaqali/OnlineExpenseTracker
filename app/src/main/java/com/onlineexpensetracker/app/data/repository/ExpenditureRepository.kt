package com.onlineexpensetracker.app.data.repository

import androidx.lifecycle.LiveData
import com.onlineexpensetracker.app.data.dao.ExpenditureDAO
import com.onlineexpensetracker.app.data.model.Expenditure

class ExpenditureRepository(private val expenseDAO: ExpenditureDAO) {

    fun getAllExpenditures(): LiveData<List<Expenditure>> {
        return expenseDAO.getAllExpenditures()
    }

    suspend fun insertExpense(expense: Expenditure) {
        expenseDAO.insertExpenditure(expense)
    }

    // Add other methods as needed
}
