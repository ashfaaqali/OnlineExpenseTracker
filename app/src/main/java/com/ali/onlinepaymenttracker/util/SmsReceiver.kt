package com.ali.onlinepaymenttracker.util

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ali.onlinepaymenttracker.ui.viewmodel.ExpenditureViewModel
import com.ali.onlinepaymenttracker.util.NotificationUtil.showDebitNotification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

class SmsReceiver : BroadcastReceiver() {
    private lateinit var viewModel: ExpenditureViewModel

    override fun onReceive(context: Context?, intent: Intent?) {
        val appContext = context?.applicationContext ?: return
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(appContext as Application)
            .create(ExpenditureViewModel::class.java)

        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            if (bundle != null) {
                // Extracting the PDU (Protocol Data Unit) objects from the bundle
                val pdus = bundle.get("pdus") as Array<*>
                for (pdu in pdus) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val message = smsMessage.messageBody
//                    Log.d("SmsReceiver", "Received SMS: $message")

                    // Parsing the SMS message to extract amount and timestamp
                    val amount = extractAmount(message)
                    val (date, time) = extractTimestamp(message)

                    val formattedDate = formatDate(convertDateToStandardFormat(date))
                    Log.d("SmsReceiver", "Amount: $amount, Date: $date, FormattedDate: $formattedDate, Time: $time")

                    // Log the extracted amount and timestamp

                    if (isDebitMessage(message)) {
                        showDebitNotification(context, amount, formattedDate, time)
                    }

                }
            }
        }
    }

    private fun extractAmount(message: String): Int {
        val amountPattern = Pattern.compile("""Rs\.?\s?(\d+(\.\d{1,2})?)""")
        val matcher = amountPattern.matcher(message)
        return if (matcher.find()) {
            val amountString = matcher.group(1)
            // Remove decimal part and parse as Int
            amountString?.substringBefore(".")?.toIntOrNull()
                ?: 0 // Provide a default value if amountString is null
        } else {
            throw IllegalArgumentException("Amount not found in the message") // Throw an exception if no match is found
        }
    }

    private fun extractTimestamp(message: String): Pair<String, String> {
        val timestampPattern = Pattern.compile("""(\d{2}-\d{2}-\d{4} \d{2}:\d{2}:\d{2})""")
        val matcher = timestampPattern.matcher(message)
        var date = ""
        var time = ""
        if (matcher.find()) {
            val dateTime = matcher.group(1)
            val parts = dateTime?.split(" ")
            if (parts != null) {
                if (parts.size == 2) {
                    date = parts[0]
                    time = parts[1]
                }
            }
        }
        return Pair(date, time)
    }

    private fun convertDateToStandardFormat(date: String): Date? {
        // List of possible input date formats
        val inputDateFormats = arrayOf(
            "dd-MM-yyyy",
            "MM/dd/yyyy",
            "yyyy-MM-dd",
            "dd.MM.yyyy",
            "dd/MM/yyyy",
            "yyyyMMdd"
        )

        for (inputFormat in inputDateFormats) {
            try {
                // Parse the date using the current format
                val inputDateFormat = SimpleDateFormat(inputFormat, Locale.US)
                val parsedDate = inputDateFormat.parse(date)

                // If parsing is successful, return the parsed date
                if (parsedDate != null) {
                    return parsedDate
                }
            } catch (e: Exception) {
                // Continue to the next format if parsing fails
                continue
            }
        }

        // Return null if no format matches
        return null
    }

    private fun formatDate(date: Date?): String {
        // Output date format
        val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        return outputDateFormat.format(date)
    }




    private fun isDebitMessage(message: String): Boolean {
        return message.contains("debited", ignoreCase = true)
    }
}
