package com.mabn.calendarlibrary.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage

/**
 * @return background color of the node, which is measured at a point - P(width/2, height)
 */
fun SemanticsNodeInteraction.getBackgroundColor(): Color {
    val array = IntArray(1)
    val image = captureToImage()
    // get pixels from the bottom - there is anti-aliasing at the top, which interferes measurements
    image.readPixels(array, startY = image.height-2, startX = image.width/2, width = 1, height = 1)
    return Color(array[0])
}