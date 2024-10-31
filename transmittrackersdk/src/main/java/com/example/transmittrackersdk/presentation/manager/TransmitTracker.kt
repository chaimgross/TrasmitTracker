package com.example.transmittrackersdk.presentation.manager

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.example.transmittrackersdk.data.repository.UserInteractionRepository
import com.example.transmittrackersdk.domain.usecase.TrackButtonPressUseCase
import com.example.transmittrackersdk.domain.usecase.TrackScreenSwitchUseCase
import com.example.transmittrackersdk.presentation.observer.ScreenLifecycleObserver

object TransmitTracker {

    fun init(application: Application) {
        registerObservers(application)
    }

    @VisibleForTesting
    private fun registerObservers(application: Application,
                                  userInteractionRepository: UserInteractionRepository = UserInteractionRepository(application.applicationContext)
    ) {
        val trackButtonPressUseCase = TrackButtonPressUseCase(userInteractionRepository)
        val trackScreenSwitchUseCase = TrackScreenSwitchUseCase(userInteractionRepository)

        val screenLifecycleObserver = ScreenLifecycleObserver(
            trackScreenSwitchUseCase,
            trackButtonPressUseCase
        )
        screenLifecycleObserver.register(application)
    }
}