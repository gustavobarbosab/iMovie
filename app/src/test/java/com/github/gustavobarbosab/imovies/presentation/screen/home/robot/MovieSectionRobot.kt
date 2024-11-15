package com.github.gustavobarbosab.imovies.presentation.screen.home.robot

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType

class MovieSectionRobot(
    private val composeTest: ComposeContentTestRule,
    private val sectionType: HomeMovieSectionType
) {
    private val onMovieSection
        get() = composeTest.onNodeWithTag(sectionType.name)

    private val onFeedbackContainer
        get() = onMovieSection
            .onChildren()
            .filterToOne(hasAnyDescendant(hasTestTag("RetryButton")))
            .onChild()

    private val onLazyRow
        get() = onMovieSection
            .onChildren()
            .filterToOne(hasTestTag("MoviesList"))

    private val onFeedbackButton
        get() = onFeedbackContainer
            .onChildren()
            .filterToOne(hasTestTag("RetryButton"))

    operator fun invoke(block: MovieSectionRobot.() -> Unit) = block()

    fun assertSectionTitle(title: String) {
        onMovieSection
            .onChildren()
            .filterToOne(hasText(title))
            .assertExists()
    }

    fun assertFeedbackMessage(expectedText: String) {
        onFeedbackContainer
            .onChildren()
            .filterToOne(hasText(expectedText))
            .assertExists()
    }

    fun assertFeedbackButtonText(text: String) {
        onFeedbackButton.assertTextEquals(text)
    }

    fun clickOnFeedbackButton() {
        onFeedbackButton
            .assertHasClickAction()
            .performClick()
    }

    fun assertFeedbackButtonIsClickable() {
        onFeedbackButton
            .assertHasClickAction()
            .performClick()
            .assertHasClickAction()
    }

    fun scrollToMovie(position: Int) {
        onLazyRow.performScrollToIndex(position)
    }

    fun assertMovieExists(name: String) = apply {
        onLazyRow
            .onChildren()
            .filterToOne(hasTestTag(name))
            .assertExists()
    }

    fun clickOnMovie(name: String) = apply {
        onLazyRow
            .onChildren()
            .filterToOne(hasTestTag(name))
            .performClick()
    }
}