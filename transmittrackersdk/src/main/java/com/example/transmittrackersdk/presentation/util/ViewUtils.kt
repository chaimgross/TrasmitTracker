package com.example.transmittrackersdk.presentation.util

import android.view.View
import android.view.ViewGroup

fun View.isViewAtCoordinates(x: Float, y: Float): Boolean {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val viewX = location[0]
    val viewY = location[1]

    return x >= viewX && x <= (viewX + width) &&
            y >= viewY && y <= (viewY + height)
}

fun ViewGroup.any(predicate: (View) -> Boolean): Boolean {
    for (i in 0 until childCount) {
        if (predicate(getChildAt(i))) {
            return true
        }
    }
    return false
}