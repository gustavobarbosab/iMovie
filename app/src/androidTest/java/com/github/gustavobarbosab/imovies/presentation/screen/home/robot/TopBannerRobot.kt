package com.github.gustavobarbosab.imovies.presentation.screen.home.robot

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

class TopBannerRobot(
    private val composeTest: ComposeContentTestRule
) {
    private val onTopBannerSection
        get() = composeTest.onNodeWithTag("TopBannerSection")

    private val onFeedbackContainer
        get() = onTopBannerSection.onChild()

    private val onFeedbackButton
        get() = onFeedbackContainer
            .onChildren()
            .filterToOne(hasTestTag("RetryButton"))

    operator fun invoke(block: TopBannerRobot.() -> Unit) = block()

    fun assertFeedbackMessage(expectedText: String) = apply {
        onFeedbackContainer
            .onChildren()
            .filterToOne(hasText(expectedText))
            .assertExists()
    }

    fun assertFeedbackButtonText(text: String) = apply {
        onFeedbackButton.assertTextEquals(text)
    }

    fun clickOnFeedbackButton() = apply {
        onFeedbackButton
            .assertHasClickAction()
            .performClick()
    }
}