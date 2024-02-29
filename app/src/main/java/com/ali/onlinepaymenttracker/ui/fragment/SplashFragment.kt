package com.ali.onlinepaymenttracker.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.ali.onlinepaymenttracker.R
import com.ali.onlinepaymenttracker.databinding.FragmentSplashBinding
import com.ali.onlinepaymenttracker.util.AppConstants

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (checkNotificationPermission()) {
                        navigateToExpenseList()
                    } else {
                        navigateToPermission()
                    }
                } else {
                    navigateToExpenseList()
                }
            } else {
                navigateToPermission()
            }
        }, 2500)
    }

    private fun navigateToExpenseList() {
        findNavController().navigate(R.id.expenseListFragment)
    }

    private fun navigateToPermission() {
        findNavController().navigate(R.id.permissionFragment)
    }

    private fun checkSmsPermission(): Boolean {
        val permission = Manifest.permission.RECEIVE_SMS
        val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission(): Boolean {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)
        Log.d("SplashFragment", "Result: $permissionCheckResult")
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }
}