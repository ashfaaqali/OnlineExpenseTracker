package com.ali.onlinepaymenttracker.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.ali.onlinepaymenttracker.data.model.Expenditure
import com.ali.onlinepaymenttracker.databinding.FragmentAddExpenditureBinding
import com.ali.onlinepaymenttracker.ui.viewmodel.ExpenditureViewModel
import com.ali.onlinepaymenttracker.util.AppConstants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenditureFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentAddExpenditureBinding
    private lateinit var viewModel: ExpenditureViewModel
    private var amount: Int = 0
    private lateinit var note: String
    private lateinit var date: String
    private lateinit var time: String
    private lateinit var location: String
    private val calendar = Calendar.getInstance()
    private val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenditureBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ExpenditureViewModel::class.java]
        binding.dateTv.text = dateFormatter.format(calendar.timeInMillis)
        if (arguments != null) {
            amount = requireArguments().getInt(AppConstants.AMOUNT, 0)
            date = requireArguments().getString(AppConstants.DATE).toString()
            time = requireArguments().getString(AppConstants.TIME).toString()
            prefillData()
        }
        return binding.root
    }

    private fun prefillData() {
        binding.apply {
            editTextAmount.setText(amount.toString())
            dateTv.text = date
            timeTv.text = time
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.dateTv.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnAddExpense.setOnClickListener {

            binding.apply {
                amount = editTextAmount.text.toString().toIntOrNull() ?: 0
                note = editTextNote.text.toString()
                date = dateTv.text.toString()
                time = timeTv.text.toString()
                location = editTextLocation.text.toString()
            }

            if (inputCheck(amount, note, date))
                addExpenditureToDb(amount, note, date, time, location)
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        binding.dateTv.text = dateFormatter.format(calendar.timeInMillis)
    }

    private fun inputCheck(amount: Int?, note: String?, dateTxt: String?): Boolean {
        var isValid = true

        binding.apply {
            if (amount == null || amount == 0) {
                editTextAmount.error = "Please enter amount"
                isValid = false
            }

            if (note.isNullOrEmpty()) {
                editTextNote.error = "Please enter note"
                isValid = false
            }

            if (dateTxt.isNullOrEmpty()) {
                dateTv.error = "Please select date"
                isValid = false
            }
        }
        return isValid
    }

    private fun addExpenditureToDb(
        amount: Int,
        note: String,
        date: String,
        time: String,
        location: String
    ) {
        val expenditure = Expenditure(
            0,
            amount = amount,
            note = note,
            date = date,
            time = time,
            location = location
        )
        Log.d("DataInserted", expenditure.toString())
        viewModel.insertExpenditure(expenditure)
    }
}