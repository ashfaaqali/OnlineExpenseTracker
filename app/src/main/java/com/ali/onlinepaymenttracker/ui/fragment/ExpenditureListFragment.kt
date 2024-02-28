package com.ali.onlinepaymenttracker.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ali.onlinepaymenttracker.R
import com.ali.onlinepaymenttracker.databinding.FragmentExpenditureListBinding

class ExpenditureListFragment : Fragment() {
    private lateinit var binding: FragmentExpenditureListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenditureListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.addExpenditureFragment)
        }
    }
}