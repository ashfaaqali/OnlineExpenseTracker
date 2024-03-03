package com.ali.onlinepaymenttracker.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ali.onlinepaymenttracker.data.database.AppDatabase
import com.ali.onlinepaymenttracker.data.model.Expenditure
import com.ali.onlinepaymenttracker.data.model.SmsData
import com.ali.onlinepaymenttracker.data.repository.ExpenditureRepository
import kotlinx.coroutines.launch

class ExpenditureViewModel(application: Application) : AndroidViewModel(application) {

    private val _smsData = MutableLiveData<SmsData>()
    val smsData: LiveData<SmsData> get() = _smsData

    val getAllExpenditures: LiveData<List<Expenditure>>
    private val repository: ExpenditureRepository

    init {
        val expenditureDAO =AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenditureRepository(expenditureDAO)
        getAllExpenditures = repository.getAllExpenditures()
    }

    fun updateSmsData(amount: Int, date: String, time: String, fromNotification: Boolean) {
        val smsData = SmsData(amount, date, time, fromNotification)
        _smsData.value = smsData
        Log.d("ExpenditureViewModel", "Amount: $amount, Date: $date, Time: $time, FromNotification $fromNotification")
    }

    fun insertExpenditure(expenditure: Expenditure) {
        viewModelScope.launch {
            repository.insertExpense(expenditure)
        }
    }
}