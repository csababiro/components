package com.mobiversal.smsretrieve

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task

/**
 * Created by Biro Csaba on 04/12/2020.
 */
abstract class SmsReceiverActivity : AppCompatActivity() {

    private val TAG = SmsReceiverActivity::class.java.simpleName

    private val smsBroadcastReceiver = object: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            Log.d(TAG, "MySMSBroadcastReceiver")
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras: Bundle? = intent.extras
                val status: Status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get SMS message contents
                        var message: String? = extras?.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                        Log.d(TAG,"MySMSBroadcastReceiver SUCCESS $message")
                        messageRetrieved(message)
                    }
                    else -> {
                        Log.d(TAG,"MySMSBroadcastReceiver error status code: ${status.statusCode}")
                    }
                }
            }
        }
    }

    abstract fun messageRetrieved(message: String?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerSmsRetriever()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(smsBroadcastReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(smsBroadcastReceiver)
    }

    private fun registerSmsRetriever() {
        // Get an instance of SmsRetrieverClient, used to start listening for a matching SMS message.
        // Get an instance of SmsRetrieverClient, used to start listening for a matching SMS message.
        val client: SmsRetrieverClient = SmsRetriever.getClient(this /* context */)

        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.

        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        val task: Task<Void> = client.startSmsRetriever()

        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);

        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(OnSuccessListener<Void?> {
            // Successfully started retriever, expect broadcast intent
            Log.d(TAG,"registerSmsRetriever: Successfully started retriever, expect broadcast intent")
        })

        task.addOnFailureListener(OnFailureListener {
            // Failed to start retriever, inspect Exception for more details
            Log.d(TAG,"registerSmsRetriever: Failed to start retriever, inspect Exception for more details")
        })
    }
}