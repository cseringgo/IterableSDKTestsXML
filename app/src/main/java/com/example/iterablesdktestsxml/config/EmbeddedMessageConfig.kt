package com.example.iterablesdktestsxml.config

import com.iterable.iterableapi.ui.embedded.IterableEmbeddedViewConfig
import androidx.core.graphics.toColorInt


// Define the embedded message view settings in a centralized place
object EmbeddedMessageConfig {

    val config: IterableEmbeddedViewConfig by lazy {
        IterableEmbeddedViewConfig(
            backgroundColor = "#F7F4EA".toColorInt(),
            borderColor = "#D7C4B6".toColorInt(),
            borderWidth = 10,
            borderCornerRadius = 10f,
            primaryBtnBackgroundColor = "#49311f".toColorInt(),
            primaryBtnTextColor = "#FFFFFF".toColorInt(),
            secondaryBtnBackgroundColor = "#FFFFFF".toColorInt(),
            secondaryBtnTextColor = "#000000".toColorInt(),
            titleTextColor = "#49311f".toColorInt(),
            bodyTextColor = "#000000".toColorInt()
        )
    }
}