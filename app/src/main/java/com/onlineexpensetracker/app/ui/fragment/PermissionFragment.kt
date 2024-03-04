package com.onlineexpensetracker.app.ui.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.onlineexpensetracker.app.R
import com.onlineexpensetracker.app.databinding.FragmentPermissionBinding

class PermissionFragment : Fragment() {
    private lateinit var binding: FragmentPermissionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(layoutInflater)
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
            permissionDenied()
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkSmsPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkNotificationPermission()) {
                    navigateToExpenseList()
                } else {
                    requestNotificationPermission()
                }
            } else {
                navigateToExpenseList()
            }
        } else {
            requestSmsPermission()
        }
    }

    private fun permissionDenied() {
        binding.btnGrantPermission.visibility = View.GONE
        binding.btnOpenSettings.visibility = View.VISIBLE
    }

    // Permission request launcher for notification permission
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            navigateToExpenseList()
        } else {
            permissionDenied()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnGrantPermission.setOnClickListener {
            requestSmsPermission()
        }
        binding.btnOpenSettings.setOnClickListener {
            openAppSetting()
        }
    }

    private fun navigateToExpenseList() {
        findNavController().navigate(R.id.expenseListFragment)
    }

    private fun requestSmsPermission() {
        requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun openAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
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