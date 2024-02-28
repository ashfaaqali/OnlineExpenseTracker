package com.ali.onlinepaymenttracker.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.onlinepaymenttracker.R
import com.ali.onlinepaymenttracker.adapter.ExpenditureListAdapter
import com.ali.onlinepaymenttracker.data.model.Expenditure
import com.ali.onlinepaymenttracker.databinding.FragmentExpenditureListBinding
import com.ali.onlinepaymenttracker.ui.viewmodel.ExpenditureViewModel

class ExpenditureListFragment : Fragment() {
    private lateinit var binding: FragmentExpenditureListBinding
    private lateinit var adapter: ExpenditureListAdapter
    private lateinit var viewModel: ExpenditureViewModel
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
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getAllExpenditures.observe(viewLifecycleOwner, Observer { expenditure ->
            adapter.setData(expenditure)
        })
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.addExpenditureFragment)
        }
    }
}