/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.taghub.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.taghub.R
import com.example.taghub.presentation.theme.TagHubTheme
import android.nfc.NfcAdapter
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import android.app.PendingIntent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
//    lateinit var left: Node

    private lateinit var  nfcAdapter: NfcAdapter
    private var messageTextView: TextView? = null
    private var pendingIntent: PendingIntent? = null
    private val nfcReceiver = NfcReceiver()


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            // NFC is not available for this device
            Log.d("detect", "NFC not available")
            Toast.makeText(
                this, "NFC is not available on this device",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        } else if (!nfcAdapter!!.isEnabled) {
            // NFC is available for the device but not enabled
            Log.d("detect", "NFC not enabled")
            Toast.makeText(
                this, "Turn on NFC in your device settings",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            // NFC is enabled, register the BroadcastReceiver
            Log.d("detect", "NFC enabled")
            val intentFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
            registerReceiver(nfcReceiver, intentFilter)
        }

        // Create an Intent for the activity you want to launch
        val intent = Intent(this, this::class.java)

        // Set the FLAG_ACTIVITY_SINGLE_TOP flag
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        // Create a PendingIntent
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        setContent {
            WearApp("Bruno")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver when the activity is destroyed
        unregisterReceiver(nfcReceiver)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null && NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            // NFC tag detected, show a toast
            Log.d("detect", "Tag Detected")

            Toast.makeText(
                this, "Tag Detected",
                Toast.LENGTH_SHORT
            ).show()

            // You can add additional handling for the detected tag here if needed.
        }
    }
}



@Composable
fun WearApp(greetingName: String) {
    TagHubTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {

            TimeText()
            Greeting(greetingName = greetingName)
        }
    }
}


@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}

