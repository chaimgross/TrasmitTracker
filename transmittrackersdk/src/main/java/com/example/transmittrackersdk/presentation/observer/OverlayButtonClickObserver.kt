package com.example.transmittrackersdk.presentation.observer

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.transmittrackersdk.domain.usecase.TrackButtonPressUseCase

class OverlayClickObserver(
    private val trackButtonPressUseCase: TrackButtonPressUseCase,
    private val trackedViewClasses: List<Class<*>> = listOf(Button::class.java, ImageButton::class.java)
) {

    fun register(activity: Activity) {
        val trackedViews = findTrackedViews(activity.window.decorView)
        val overlayViewManager = OverlayViewManager(activity, this::trackViewClick)
        overlayViewManager.setupOverlayIfNeeded(trackedViews)
    }

    private fun findTrackedViews(view: View, views: MutableList<View> = mutableListOf()): List<View> {
        when {
            trackedViewClasses.any { it.isInstance(view) } -> views.add(view)
            view is ViewGroup -> view.forEach { child ->
                findTrackedViews(child, views)
            }
        }
        return views
    }

    private fun trackViewClick(view: View) {
        val viewDescription = when (view) {
            is Button -> view.text.ifEmpty { view.contentDescription ?: "Button" }.toString()
            is ImageButton -> view.contentDescription?.toString() ?: "ImageButton"
            else -> view.contentDescription?.toString() ?: "Unknown View"
        }
        trackButtonPressUseCase.execute(
            viewDescription,
            view.id.toString(),
            view.tag?.toString()
        )
    }

    private fun ViewGroup.forEach(action: (View) -> Unit) {
        for (i in 0 until childCount) {
            action(getChildAt(i))
        }
    }
}