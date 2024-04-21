package com.example.taghub.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.util.Log
import android.widget.Toast

class NfcReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            Log.d("NfcReceiver", "NFC tag detected")

            // Handle NFC tag detection here
            // You can perform actions or notify the Detect activity as needed
            Toast.makeText(
                context, "NFC Tag Detected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
