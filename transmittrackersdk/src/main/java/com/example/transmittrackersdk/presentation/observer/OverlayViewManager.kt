package com.example.transmittrackersdk.presentation.observer

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.transmittrackersdk.presentation.util.any
import com.example.transmittrackersdk.presentation.util.isViewAtCoordinates

class OverlayViewManager(
    private val activity: Activity,
    private val onTrackClick: (View) -> Unit,
) {
    private class OverlayView(activity: Activity) : View(activity)

    private val overlayView: OverlayView by lazy { createOverlayView() }
    private lateinit var trackedViews: List<View>

    fun setupOverlayIfNeeded(trackedViews: List<View>) {
        this.trackedViews = trackedViews
        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
        if (rootView.any { it is OverlayView }) return // Check if overlay already exists

        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            gravity = Gravity.CENTER
        }

        rootView.addView(overlayView, params)
    }

    private fun createOverlayView(): OverlayView {
        return OverlayView(activity).apply {
            setBackgroundColor(Color.TRANSPARENT)
            setOnTouchListener { _, event ->
                handleTouchEvent(event)
                true
            }
        }
    }

    private fun handleTouchEvent(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_UP) {
            val x = event.rawX
            val y = event.rawY

            val touchedView = findTrackedViewAtCoordinates(x, y)
            touchedView?.let {
                it.performClick()
                onTrackClick(it)
            }
        }
    }

    private fun findTrackedViewAtCoordinates(x: Float, y: Float): View? {
        return trackedViews.firstOrNull { it.isViewAtCoordinates(x, y) }
    }
}