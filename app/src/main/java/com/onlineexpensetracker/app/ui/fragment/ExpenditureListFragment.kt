package com.onlineexpensetracker.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onlineexpensetracker.app.R
import com.onlineexpensetracker.app.databinding.FragmentExpenditureListBinding
import com.onlineexpensetracker.app.ui.adapter.ExpenditureListAdapter
import com.onlineexpensetracker.app.ui.viewmodel.ExpenditureViewModel

class ExpenditureListFragment : Fragment() {
    private lateinit var binding: FragmentExpenditureListBinding
    private lateinit var adapter: ExpenditureListAdapter
    private lateinit var viewModel: ExpenditureViewModel
    private var lastBackPressedTime: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ExpenditureViewModel::class.java]
        adapter = ExpenditureListAdapter(this)
        binding = FragmentExpenditureListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Handle back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackPressed()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getAllExpenditures.observe(viewLifecycleOwner, Observer { expenditure ->
            adapter.setData(expenditure)
        })

        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.addExpenditureFragment)
        }
    }

    private fun handleBackPressed() {
        val currentTime = System.currentTimeMillis()

        // Check if the difference between current time and last back press time is less than 2 seconds
        if (currentTime - lastBackPressedTime < 2000) {
            requireActivity().finish()
        } else {
            // If not, update the lastBackPressedTime and show a toast indicating the action
            lastBackPressedTime = currentTime
            Toast.makeText(requireContext(), R.string.exit_app_toast, Toast.LENGTH_SHORT).show()
        }
    }
}