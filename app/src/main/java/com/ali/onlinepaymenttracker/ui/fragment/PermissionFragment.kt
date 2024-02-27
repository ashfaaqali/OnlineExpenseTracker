package com.ali.onlinepaymenttracker.ui.fragment

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.ali.onlinepaymenttracker.R
import com.ali.onlinepaymenttracker.databinding.FragmentPermissionBinding

class PermissionFragment : Fragment() {
    private lateinit var binding: FragmentPermissionBinding
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("PermissionFragment", "Permission Granted!!")
            navigateToExpenseList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnGrantPermission.setOnClickListener {
            requestSmsPermission()
        }
    }

    private fun requestSmsPermission() {
        requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
    }

    private fun navigateToExpenseList() {
        findNavController().navigate(R.id.expenseListFragment)
    }
}

const val RECEIVE_SMS_CODE = 110