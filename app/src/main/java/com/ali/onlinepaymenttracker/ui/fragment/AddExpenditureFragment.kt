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
import java.util.Date

class AddExpenditureFragment : Fragment() {
    private lateinit var binding: FragmentAddExpenditureBinding
    private lateinit var viewModel: ExpenditureViewModel
    private lateinit var amount: String
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnAddExpense.setOnClickListener {

            binding.apply {
                amount = editTextAmount.text.toString()
                note = editTextNote.text.toString()
                dateTxt = editTextDate.text.toString()
                time = editTextTime.text.toString()
                location = editTextLocation.text.toString()
            }

            if (inputCheck(amount, note, dateTxt))
                addExpenditureToDb(amount, note, dateTxt, time, location)
        }
    }

    private fun inputCheck(amount: String?, note: String?, dateTxt: String?): Boolean {
        var isValid = true

        binding.apply {
            if (amount.isNullOrEmpty()) {
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
        amount: String,
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