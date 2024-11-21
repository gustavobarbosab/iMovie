package com.github.gustavobarbosab.imovies.presentation.screen.home.robot

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import com.github.gustavobarbosab.imovies.presentation.screen.home.robot.parts.MovieSectionRobotPart
import com.github.gustavobarbosab.imovies.presentation.screen.home.robot.parts.TopBannerRobotPart

class HomeRobot(
    private val composeTestRule: ComposeTestRule,
    private val context: Context
) {

    operator fun invoke(func: HomeRobot.() -> Unit) = func()

    fun onTopBanner(func: TopBannerRobotPart.() -> Unit) {
        TopBannerRobotPart.Impl(composeTestRule, context).func()
    }

    fun onMovieSection(sectionType: HomeMovieSectionType, func: MovieSectionRobotPart.() -> Unit) {
        MovieSectionRobotPart.Impl(composeTestRule, context, sectionType).func()
    }
}