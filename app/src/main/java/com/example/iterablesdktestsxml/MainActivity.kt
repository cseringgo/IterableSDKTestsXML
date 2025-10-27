package com.example.iterablesdktestsxml

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.iterablesdktestsxml.config.EmbeddedMessageConfig
import com.iterable.iterableapi.IterableApi
import com.iterable.iterableapi.ui.embedded.IterableEmbeddedView
import com.iterable.iterableapi.ui.embedded.IterableEmbeddedViewType
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Update profile and send custom event logic for buttons
        updateProfile()
        sendCustomEvent()


        // EMBEDDED MESSAGES
        // Add a slight delay to allow the SDK time to sync. Just for demo purposes.
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            fetchEmbeddedMessages()
        }, 800) // delay 800ms to let SDK sync

    }

    override fun onResume() {
        super.onResume()
        // Attempting to show in app messages on resume
        Log.d("IterableLogs", "Inside onResume. Attempting to show in app messages.")
        showFirstInAppMessage()

        // Add listener for embedded messages
        // IterableApi.getInstance().embeddedManager.addUpdateListener(this)
    }


    // UPDATE ITERABLE USER PROFILE
    private fun updateProfile() {
        // Button to update user profile
        findViewById<Button>(R.id.updateProfile).setOnClickListener {

            Log.d("IterableLogs", "Update button clicked.")

            val fieldsToUpdate = JSONObject()
            val datafields = JSONObject()

            try {
                fieldsToUpdate.put("firstName", "Christy")
                fieldsToUpdate.put("isRegisteredUser", true)
                fieldsToUpdate.put("SA_User_Test_Key", "completed")

                datafields.put("dataFields", fieldsToUpdate)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            Log.d("IterableLogs","Attempting to update these fields on Iterable user profile: $fieldsToUpdate" )

            // Update the user profile with provided data fields
            // This option didn't seem to work, needed the mergeNestedObjects param
            // IterableApi.getInstance().updateUser(datafields)
            IterableApi.getInstance().updateUser(fieldsToUpdate, false)
        }
    }

    // SEND CUSTOM EVENT
    private fun sendCustomEvent() {
        // Button to update user profile
        findViewById<Button>(R.id.sendEvent).setOnClickListener {

            Log.d("IterableLogs", "Send event button clicked.")

            val dataFields = JSONObject()
            dataFields.put("platform", "Android")
            dataFields.put("isTestEvent", true)
            dataFields.put("url", "https://iterable.com/sa-test/CHRISTY")
            dataFields.put("secret_code_key", "Code_123")

            Log.d("IterableLogs", "custom event data fields: $dataFields")

            // Call custom event in Iterable
            IterableApi.getInstance().track(
                "mobileSATestEvent",
                dataFields
            );

        }
    }


    // IN APP MESSAGES
    private fun showFirstInAppMessage() {
        val inAppManager = IterableApi.getInstance().inAppManager
        val messages = inAppManager.messages
        Log.d("IterableLogs", "Found ${messages.size} in app messages")

        if (messages.isNotEmpty()) {
            val message = messages[0]
            Log.d("IterableLogs", "Attempting to show in app messageId=${message.messageId}")
            inAppManager.showMessage(message)
        } else {
            Log.d("IterableLogs", "No in app messages available")
        }
    }


    // EMBEDDED MESSAGES
    private fun fetchEmbeddedMessages() {
        // Configure the Iterable embedded message display
        val embeddedViewConfig = EmbeddedMessageConfig.config

        // Fetch embedded messages
        val embeddedMessages = IterableApi.getInstance().embeddedManager.getMessages(1396)
        if (!embeddedMessages.isNullOrEmpty()) {
            val message = embeddedMessages[0]
            Log.d("IterableLogs", "Found embedded message=${message.metadata.messageId}")

            // Create IterableEmbeddedView fragment
            val embeddedFragment = IterableEmbeddedView(
                IterableEmbeddedViewType.BANNER,
                message,
                embeddedViewConfig
            )

            // Insert fragment into placeholder FrameLayout
            supportFragmentManager.beginTransaction()
                .replace(R.id.embeddedMessageContainer, embeddedFragment)
                .commit()
        } else {
            Log.d("IterableLogs", "No embedded messages available")
        }

        // Button to reload embedded messages if needed
        findViewById<Button>(R.id.reloadEmbedded).setOnClickListener {
            // Repeat fetch + replace logic if needed
            Log.d("IterableLogs", "Button clicked. Attempting to get embedded messages.")


            // Fetch embedded messages
            val embeddedMessages = IterableApi.getInstance().embeddedManager.getMessages(1396)
            if (!embeddedMessages.isNullOrEmpty()) {
                val message = embeddedMessages[0]
                Log.d("IterableLogs", "Found embedded message=${message.metadata.messageId}")

                // Create IterableEmbeddedView fragment
                val embeddedFragment = IterableEmbeddedView(
                    IterableEmbeddedViewType.BANNER,
                    message,
                    embeddedViewConfig
                )

                // Insert fragment into placeholder FrameLayout
                supportFragmentManager.beginTransaction()
                    .replace(R.id.embeddedMessageContainer, embeddedFragment)
                    .commit()
            } else {
                Log.d("IterableLogs", "No embedded messages available")
            }
        }
    }

}