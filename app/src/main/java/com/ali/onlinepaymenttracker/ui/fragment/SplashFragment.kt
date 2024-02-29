package com.ali.onlinepaymenttracker.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
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

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        requestSmsPermission()
        requestNotificationPermission()
        return binding.root
    }

    // Requesting Sms Permission
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationPermission()
            } else {
                navigateToExpenseList()
            }
        } else {
            navigateToPermission()
        }
    }

    // Permission request launcher for notification permission
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            navigateToExpenseList()
        } else {
            navigateToPermission()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Handler().postDelayed({
            if (checkSmsPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkNotificationPermission()
                } else {
                    navigateToExpenseList()
                }
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

    private fun checkSmsPermission(): Boolean {
        val permission = Manifest.permission.RECEIVE_SMS
        val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    private fun requestSmsPermission() {
        requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission(): Boolean {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}