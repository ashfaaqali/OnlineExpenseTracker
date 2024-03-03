package com.ali.onlinepaymenttracker.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ali.onlinepaymenttracker.data.model.Expenditure
import com.ali.onlinepaymenttracker.databinding.FragmentAddExpenditureBinding
import com.ali.onlinepaymenttracker.ui.viewmodel.ExpenditureViewModel
import com.ali.onlinepaymenttracker.util.AppConstants

class AddExpenditureFragment : Fragment() {
    private lateinit var binding: FragmentAddExpenditureBinding
    private lateinit var viewModel: ExpenditureViewModel
    private var amount: Int = 0
    private lateinit var note: String
    private lateinit var dateTxt: String
    private lateinit var time: String
    private lateinit var location: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenditureBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ExpenditureViewModel::class.java]
        if (arguments != null){
            amount = requireArguments().getInt(AppConstants.AMOUNT, 0)
            dateTxt = requireArguments().getString(AppConstants.DATE).toString()
            time = requireArguments().getString(AppConstants.TIME).toString()
            prefillData()
        }
        return binding.root
    }

    private fun prefillData() {
        binding.apply {
            editTextAmount.setText(amount.toString())
            editTextDate.setText(dateTxt)
            editTextTime.setText(time)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnAddExpense.setOnClickListener {

            binding.apply {
                amount = editTextAmount.text.toString().toInt()
                note = editTextNote.text.toString()
                dateTxt = editTextDate.text.toString()
                time = editTextTime.text.toString()
                location = editTextLocation.text.toString()
            }

            if (inputCheck(amount, note, dateTxt))
                addExpenditureToDb(amount, note, dateTxt, time, location)
        }
    }

    override fun onStart() {
        super.onStart()
        observeSmsData()
    }

    private fun observeSmsData() {
        viewModel.smsData.observe(viewLifecycleOwner) { smsData ->
            if (smsData != null && smsData.fromNotification) {
                // Pre-fill the amount, date, and time fields
                binding.editTextAmount.setText(smsData.amount)
                binding.editTextDate.setText(smsData.date)
                binding.editTextTime.setText(smsData.time)
            }
        }
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
                editTextDate.error = "Please select date"
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