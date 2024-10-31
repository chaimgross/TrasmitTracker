package com.example.transmittrackersdk.domain.usecase

import com.example.transmittrackersdk.data.model.ScreenEvent
import com.example.transmittrackersdk.data.repository.UserInteractionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TrackScreenSwitchUseCase(
    private val userInteractionRepository: UserInteractionRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(kotlinx.coroutines.Dispatchers.IO)
) {
    fun execute(screenName: String) {
        val screenEvent = ScreenEvent(
            eventName = screenName,
            timestamp = System.currentTimeMillis()
        )

        coroutineScope.launch {
            userInteractionRepository.handleScreenSwitch(screenEvent)
        }
    }
}