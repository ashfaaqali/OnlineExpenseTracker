package com.ali.onlinepaymenttracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ali.onlinepaymenttracker.ui.fragment.AddExpenditureFragment
import com.ali.onlinepaymenttracker.util.AppConstants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Check if intent has fragment to open
        checkNotificationClick()
    }

    private fun checkNotificationClick() {
        if (intent.hasExtra(AppConstants.FRAGMENT_TO_OPEN)) {
            val fragmentToOpen = intent.getStringExtra(AppConstants.FRAGMENT_TO_OPEN)
            if (fragmentToOpen == AppConstants.ADD_EXPENDITURE_FRAGMENT) {
                openAddExpenditureFragment()
            }
        }
    }

    private fun openAddExpenditureFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, AddExpenditureFragment())
            .addToBackStack(null)
            .commit()
    }
}