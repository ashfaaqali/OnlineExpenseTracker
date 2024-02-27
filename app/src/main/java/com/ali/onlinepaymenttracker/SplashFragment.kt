package com.ali.onlinepaymenttracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.ali.onlinepaymenttracker.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            if (checkSmsPermission()) {
                navigateToExpenseList()
            } else {
                navigateToPermission()
            }
        }, 3000)
    }

    private fun navigateToExpenseList() {
        findNavController().navigate(R.id.expenseListFragment)
    }

    private fun navigateToPermission() {
        findNavController().navigate(R.id.permissionFragment)
    }

    private fun checkSmsPermission() : Boolean {
        val permission = Manifest.permission.RECEIVE_SMS
        val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }
}