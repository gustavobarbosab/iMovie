package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
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

    private fun ComposeContentTestRule.setHomeScreenContent(state: HomeScreenState) {
        setContent {
            IMoviesTheme {
                HomeScreenContent(
                    screenState = state,
                    onRetryLoadSection = onRetryLoadSection,
                    onRetryLoadTopBanner = onRetryLoadTopBanner,
                    onMovieClicked = onMovieClicked
                )
            }
        }
    }

    @Test
    fun whenLoadMoviesSuccessfully_ShouldShowTheMoviesAndAcceptClick() {
        val mockedMovies = mutableListOf<HomeMovieModel>()
        repeat(10) { position ->
            mockedMovies.add(
                HomeMovieModel(
                    id = position.toLong(),
                    title = "Movie $position",
                    backdropPath = "https://image.tmdb.org/t/p/w1280/3V4kLQg0kSqPLctI5ziYWabAZYF.jpg",
                    posterPath = "https://image.tmdb.org/t/p/w500/aosm8NMQ3UyoBVpSxyimorCQykC.jpg"
                )
            )
        }

        val state = HomeScreenState.initialState().copy(
            topBannerMovies = UiStateList.Success(mockedMovies),
            movieSectionMap = mapOf(
                HomeMovieSectionType.POPULAR to HomeScreenState.MovieSectionState(
                    HomeMovieSectionType.POPULAR,
                    title = R.string.home_popular_section_title,
                    uiState = UiStateList.Success(mockedMovies)
                ),
            )
        )

        composeTestRule.setHomeScreenContent(state)

        topBannerRobot {
            assertMovieTitle("Movie 0")
            clickOnMovie()
        }

        movieSectionRobot {
            assertSectionTitle("Popular")
            scrollToMovie(5)
            clickOnMovie("Movie 5")
        }

        verify {
            onMovieClicked(mockedMovies.first())
            onMovieClicked(mockedMovies[5])
        }
    }

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

        composeTestRule.setHomeScreenContent(state)

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
