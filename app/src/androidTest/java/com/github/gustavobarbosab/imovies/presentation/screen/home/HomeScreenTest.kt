package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.ui.test.junit4.createComposeRule
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.common.ui.UiStateList
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import com.github.gustavobarbosab.imovies.presentation.screen.home.robot.MovieSectionRobot
import com.github.gustavobarbosab.imovies.presentation.screen.home.robot.TopBannerRobot
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val onRetryLoadSection: (HomeMovieSectionType) -> Unit = mockk(relaxed = true)
    private val onRetryLoadTopBanner: () -> Unit = mockk(relaxed = true)
    private val onMovieClicked: (HomeMovieModel) -> Unit = mockk(relaxed = true)

    private val topBannerRobot = TopBannerRobot(composeTestRule)
    private val movieSectionRobot = MovieSectionRobot(composeTestRule, HomeMovieSectionType.POPULAR)

    @Test
    fun whenThereIsAnErrorToLoadTheMovies_ShouldShowErrorState() {
        val state = HomeScreenState.initialState().copy(
            topBannerMovies = UiStateList.Failure("Error"),
            movieSectionMap = mapOf(
                HomeMovieSectionType.POPULAR to HomeScreenState.MovieSectionState(
                    HomeMovieSectionType.POPULAR,
                    title = R.string.home_popular_section_title,
                    uiState = UiStateList.Failure("Error")
                ),
            )
        )

        composeTestRule.setContent {
            IMoviesTheme {
                HomeScreenContent(
                    screenState = state,
                    onRetryLoadSection = {
                        onRetryLoadSection(it)
                    },
                    onRetryLoadTopBanner = {
                        onRetryLoadTopBanner()
                    },
                    onMovieClicked = {
                        onMovieClicked(it)
                    }
                )
            }
        }

        topBannerRobot {
            assertFeedbackMessage("There was an error on loading the movies now playing.")
            assertFeedbackButtonText("Try again")
            clickOnFeedbackButton()
        }

        movieSectionRobot {
            assertSectionTitle("Popular")
            assertFeedbackMessage("There was an error, try again.")
            assertFeedbackButtonText("Try again")
            clickOnFeedbackButton()
        }

        verify {
            onRetryLoadTopBanner()
            onRetryLoadSection(HomeMovieSectionType.POPULAR)
        }
    }
}
