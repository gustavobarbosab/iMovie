package com.github.gustavobarbosab.imovies.sharedtest.home.robot.parts

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import com.github.gustavobarbosab.imovies.sharedtest.common.robot.FeedbackContainerRobotPart

private typealias SectionMovieName = String

@OptIn(ExperimentalTestApi::class)
interface MovieSectionRobotPart {

    fun scrollToSection()
    fun assertSectionTitle(sectionTitle: String)
    fun assertFeedbackMessage(expectedText: String)
    fun assertFeedbackButtonText(expectedText: String)
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
        private val parentLazyRowNodeMatcher = hasContentDescription(
            testContext.getString(R.string.home_sections_content_description)
        )

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

        private val sectionLazyRow = hasContentDescription(
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

        override fun scrollToSection() {
            composeTest
                .onNode(parentLazyRowNodeMatcher)
                .performScrollToNode(sectionLazyRow)
        }

        override fun assertSectionTitle(sectionTitle: String) {
            composeTest
                .onNode(sectionTitleNodeMatcher)
                .assertTextEquals(sectionTitle)
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

        override fun assertFeedbackButtonText(expectedText: String) {
            feedbackContainerRobotPart.assertFeedbackButtonText(expectedText)
        }

        override fun clickOnFeedbackButton() {
            feedbackContainerRobotPart.clickOnFeedbackButton()
        }
    }
}