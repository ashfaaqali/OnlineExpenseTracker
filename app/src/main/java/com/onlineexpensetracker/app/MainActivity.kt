package com.onlineexpensetracker.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ali.onlinepaymenttracker.R
import com.onlineexpensetracker.app.ui.fragment.AddExpenditureFragment
import com.onlineexpensetracker.app.util.AppConstants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Check if intent has fragment to open
        checkNotificationClick()
    }

    private fun checkNotificationClick() {
        if (intent != null && intent.hasExtra(AppConstants.FRAGMENT_TO_OPEN)) {
            val fragmentToOpen = intent.getStringExtra(AppConstants.FRAGMENT_TO_OPEN)
            if (fragmentToOpen == AppConstants.ADD_EXPENDITURE_FRAGMENT) {
                val amt = intent.getIntExtra(AppConstants.AMOUNT, 0)
                val date = intent.getStringExtra(AppConstants.DATE)
                val time = intent.getStringExtra(AppConstants.TIME)

                openAddExpenditureFragment(amt, date, time)
            }
        }
    }

    private fun openAddExpenditureFragment(amt: Int, date: String?, time: String?) {
        val fragment = AddExpenditureFragment().apply{
            arguments = Bundle().apply {
                putInt(AppConstants.AMOUNT, amt)
                putString(AppConstants.DATE, date)
                putString(AppConstants.TIME, time)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }
}