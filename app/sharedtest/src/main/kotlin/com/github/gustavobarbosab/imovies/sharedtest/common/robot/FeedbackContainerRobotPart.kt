package com.github.gustavobarbosab.imovies.sharedtest.common.robot

import android.content.Context
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import com.github.gustavobarbosab.imovies.R

class FeedbackContainerRobotPart(
    private val composeTest: ComposeTestRule,
    testContext: Context,
    parentContainerNodeMatcher: SemanticsMatcher
) {
    private val feedbackIconNodeMatcher =
        hasAnyAncestor(parentContainerNodeMatcher) and
                hasContentDescription(testContext.getString(R.string.feedback_container_icon_content_description))

    private val feedbackMessageNodeMatcher =
        hasAnyAncestor(parentContainerNodeMatcher) and
                hasContentDescription(testContext.getString(R.string.feedback_container_message_content_description))

    private val feedbackButtonNodeMatcher =
        hasAnyAncestor(parentContainerNodeMatcher) and
                hasContentDescription(testContext.getString(R.string.feedback_container_button_content_description))

    fun assertFeedbackMessage(expectedText: String) {
        composeTest
            .onNode(feedbackMessageNodeMatcher)
            .assertTextEquals(expectedText)
            .assertExists()
    }

    fun assertFeedbackButtonText(expectedText: String) {
        composeTest
            .onNode(feedbackButtonNodeMatcher)
            .assertTextEquals(expectedText)
    }

    fun clickOnFeedbackButton() {
        composeTest
            .onNode(feedbackButtonNodeMatcher)
            .assertHasClickAction()
            .performClick()
    }
}