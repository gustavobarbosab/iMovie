package com.github.gustavobarbosab.imovies.sharedtest.home.robot.parts

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.sharedtest.common.robot.FeedbackContainerRobotPart

private typealias TopBannerMovieName = String

@OptIn(ExperimentalTestApi::class)
interface TopBannerRobotPart {

    fun waitUntilMovieLoad(movieTitle: String, timeoutMillis: Long = 2000)
    fun assertTopBannerMovieTitle(movieTitle: String)
    fun assertFeedbackMessage(expectedText: String)
    fun clickOnTopBannerMovie(movieTitle: String)
    fun assertFeedbackButtonText(expectedText: String)
    fun clickOnFeedbackButton()

    class Impl(
        private val composeTest: ComposeTestRule,
        private val testContext: Context
    ) : TopBannerRobotPart {

        private val topBannerNodeMatcher = hasContentDescription(
            testContext.getString(R.string.home_section_top_banner_content_description)
        )

        private val TopBannerMovieName.movieTitleNodeMatcher
            get() = hasAnyAncestor(topBannerNodeMatcher) and
                    hasContentDescription(
                        testContext.getString(
                            R.string.home_section_top_banner_movie_content_description,
                            this
                        )
                    )

        private val feedbackContainerRobotPart = FeedbackContainerRobotPart(
            composeTest = composeTest,
            testContext = testContext,
            parentContainerNodeMatcher = topBannerNodeMatcher
        )

        override fun waitUntilMovieLoad(movieTitle: String, timeoutMillis: Long) {
            composeTest.waitUntilAtLeastOneExists(
                movieTitle.movieTitleNodeMatcher,
                timeoutMillis = timeoutMillis
            )
        }

        override fun assertTopBannerMovieTitle(movieTitle: String) {
            composeTest
                .onNode(movieTitle.movieTitleNodeMatcher)
                .assertExists()
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

        override fun clickOnTopBannerMovie(movieTitle: String) {
            composeTest.onNode(movieTitle.movieTitleNodeMatcher)
                .assertHasClickAction()
                .performClick()
        }
    }
}