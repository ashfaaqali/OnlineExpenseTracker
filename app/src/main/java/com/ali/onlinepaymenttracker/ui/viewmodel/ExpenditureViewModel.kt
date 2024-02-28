package com.ali.onlinepaymenttracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ali.onlinepaymenttracker.data.database.AppDatabase
import com.ali.onlinepaymenttracker.data.model.Expenditure
import com.ali.onlinepaymenttracker.data.repository.ExpenditureRepository
import kotlinx.coroutines.launch

class ExpenditureViewModel(application: Application) : AndroidViewModel(application) {

    val getAllExpenditures: LiveData<List<Expenditure>>
    private val repository: ExpenditureRepository

    init {
        val expenditureDAO =AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenditureRepository(expenditureDAO)
        getAllExpenditures = repository.getAllExpenditures()
    }

    fun insertExpenditure(expenditure: Expenditure) {
        viewModelScope.launch {
            repository.insertExpense(expenditure)
        }
    }
}