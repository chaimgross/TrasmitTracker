package com.example.transmittrackersdk.presentation.observer

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.transmittrackersdk.domain.usecase.TrackButtonPressUseCase
import com.example.transmittrackersdk.domain.usecase.TrackScreenSwitchUseCase

class ScreenLifecycleObserver(
    private val trackScreenSwitchUseCase: TrackScreenSwitchUseCase,
    private val trackButtonPressUseCase: TrackButtonPressUseCase
) : Application.ActivityLifecycleCallbacks {

    fun register(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityStarted(activity: Activity) {
        trackScreenSwitchUseCase.execute(activity.javaClass.simpleName)
        OverlayClickObserver(trackButtonPressUseCase).register(activity)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}