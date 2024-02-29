package com.ali.onlinepaymenttracker.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.ali.onlinepaymenttracker.util.NotificationUtil.showDebitNotification
import java.util.regex.Pattern

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            if (bundle != null) {
                // Extracting the PDU (Protocol Data Unit) objects from the bundle
                val pdus = bundle.get("pdus") as Array<*>
                for (pdu in pdus) {
                    val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                    val message = smsMessage.messageBody
                    Log.d("SmsReceiver", "Received SMS: $message")

                    // Parsing the SMS message to extract amount and timestamp
                    val amount = extractAmount(message)
                    val timestamp = extractTimestamp(message)

                    // Log the extracted amount and timestamp
                    Log.d("SmsReceiver", "Amount: $amount, Timestamp: $timestamp")

                    if (isDebitMessage(message)) {
                        showDebitNotification(context)
                    }

                }
            }
        }
    }

    private fun extractAmount(message: String): String? {
        val amountPattern = Pattern.compile("""Rs\.(\d+(\.\d{1,2})?)""")
        val matcher = amountPattern.matcher(message)
        return if (matcher.find()) {
            matcher.group(1)
        } else {
            null
        }
    }

    private fun extractTimestamp(message: String): String? {
        val timestampPattern = Pattern.compile("""(\d{2}-\d{2}-\d{4} \d{2}:\d{2}:\d{2})""")
        val matcher = timestampPattern.matcher(message)
        return if (matcher.find()) {
            matcher.group(1)
        } else {
            null
        }
    }

    private fun isDebitMessage(message: String): Boolean {
        return message.contains("debited", ignoreCase = true)
    }
}
