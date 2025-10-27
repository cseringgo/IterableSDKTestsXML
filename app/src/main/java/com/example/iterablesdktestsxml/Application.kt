package com.example.iterablesdktestsxml

import android.app.Application
import android.util.Log
import com.iterable.iterableapi.IterableApi
import com.iterable.iterableapi.IterableConfig
import com.iterable.iterableapi.IterableInAppHandler

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("IterableLogs", "Application onCreate - initializing Iterable")

        // Initialize Iterable SDK
        val config = IterableConfig.Builder()
            .setInAppHandler { message ->
                Log.d("IterableInApp", "Handler fired! messageId=${message.messageId}")
                IterableInAppHandler.InAppResponse.SHOW
            }
            .setEnableEmbeddedMessaging(true)
            .build()

        // Log.d("IterableLogs", "Initalizing Iterable API with key: " + BuildConfig.ITERABLE_API_KEY)
        IterableApi.initialize(this, BuildConfig.ITERABLE_API_KEY, config)
        Log.d("IterableLogs", "Initalized Iterable API.")


        // Identify user immediately after initialization
        IterableApi.getInstance().setEmail(BuildConfig.ITERABLE_DEMO_EMAIL)
        Log.d("IterableLogs", "User identified: " + BuildConfig.ITERABLE_DEMO_EMAIL)
    }
}
