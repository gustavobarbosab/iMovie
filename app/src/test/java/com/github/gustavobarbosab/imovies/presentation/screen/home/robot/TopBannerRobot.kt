package com.github.gustavobarbosab.imovies.presentation.screen.home.robot

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasClickAction
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

    private val onAutoScrollableMoviesPager
        get() = onTopBannerSection.onChild()

    private val onFeedbackContainer
        get() = onTopBannerSection.onChild()

    private val onFeedbackButton
        get() = onFeedbackContainer
            .onChildren()
            .filterToOne(hasTestTag("RetryButton"))

    operator fun invoke(block: TopBannerRobot.() -> Unit) = block()

    fun assertMovieTitle(title: String) {
        onAutoScrollableMoviesPager
            .onChildren()
            .filterToOne(hasAnyDescendant(hasText(title)))
            .assertExists()
    }

    fun assertFeedbackMessage(expectedText: String) = apply {
        onFeedbackContainer
            .onChildren()
            .filterToOne(hasText(expectedText))
            .assertExists()
    }

    fun assertFeedbackButtonText(text: String) = apply {
        onFeedbackButton.assertTextEquals(text)
    }

    fun assertFeedbackButtonIsClickable() {
        onFeedbackButton
            .assertHasClickAction()
            .performClick()
            .assertHasClickAction()
    }

    fun clickOnFeedbackButton() = apply {
        onFeedbackButton
            .assertHasClickAction()
            .performClick()
    }

    fun clickOnCurrentMovie() = apply {
        onAutoScrollableMoviesPager
            .onChildren()
            .filterToOne(hasClickAction())
            .performClick()
    }
}