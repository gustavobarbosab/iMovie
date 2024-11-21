package com.github.gustavobarbosab.imovies.presentation.screen.home.robot.parts

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.common.FeedbackContainerRobotPart
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType

private typealias SectionMovieName = String

@OptIn(ExperimentalTestApi::class)
interface MovieSectionRobotPart {

    fun assertTitle(expectedText: String)
    fun assertFeedbackMessage(expectedText: String)
    fun assertFeedbackButtonText(text: String)
    fun clickOnFeedbackButton()
    fun waitUntilLoadMovie(movieTitle: String, timeoutMillis: Long = 2000)
    fun clickOnMovie(movieTitle: String)
    fun scrollToMovie(movieTitle: String)

    class Impl(
        private val composeTest: ComposeTestRule,
        private val testContext: Context,
        private val sectionType: HomeMovieSectionType
    ) : MovieSectionRobotPart {

        // region Node Matcher
        private val sectionTitleNodeMatcher = hasContentDescription(
            testContext.getString(
                R.string.home_movie_section_title_content_description,
                sectionType.title
            )
        )

        private val lazyRowNodeMatcher = hasContentDescription(
            testContext.getString(
                R.string.home_movie_section_list_content_description,
                sectionType.title
            )
        )

        private val SectionMovieName.movieNodeMatcher
            get() = hasContentDescription(
                testContext.getString(
                    R.string.home_movie_card_content_description,
                    this,
                    sectionType.title
                )
            )

        private val feedbackContainerRobotPart = FeedbackContainerRobotPart(
            composeTest = composeTest,
            testContext = testContext,
            parentContainerNodeMatcher = lazyRowNodeMatcher
        )

        // endregion

        operator fun invoke(block: MovieSectionRobotPart.() -> Unit) = block()

        override fun assertTitle(expectedText: String) {
            composeTest
                .onNode(sectionTitleNodeMatcher)
                .assertTextEquals(expectedText)
        }

        override fun waitUntilLoadMovie(movieTitle: String, timeoutMillis: Long) {
            composeTest.waitUntilAtLeastOneExists(
                movieTitle.movieNodeMatcher,
                timeoutMillis = timeoutMillis
            )
        }

        override fun scrollToMovie(movieTitle: String) {
            composeTest
                .onNode(lazyRowNodeMatcher)
                .performScrollToNode(movieTitle.movieNodeMatcher)
        }

        override fun clickOnMovie(movieTitle: String) {
            composeTest
                .onNode(movieTitle.movieNodeMatcher)
                .assertHasClickAction()
                .performClick()
        }

        override fun assertFeedbackMessage(expectedText: String) {
            feedbackContainerRobotPart.assertFeedbackMessage(expectedText)
        }

        override fun assertFeedbackButtonText(text: String) {
            feedbackContainerRobotPart.assertFeedbackButtonText(text)
        }

        override fun clickOnFeedbackButton() {
            feedbackContainerRobotPart.clickOnFeedbackButton()
        }
    }
}