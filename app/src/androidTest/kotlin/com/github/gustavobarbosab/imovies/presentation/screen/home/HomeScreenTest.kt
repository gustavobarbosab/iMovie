package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.gustavobarbosab.imovies.HiltTestActivity
import com.github.gustavobarbosab.imovies.core.data.di.NetworkParamsModule
import com.github.gustavobarbosab.imovies.presentation.screen.detail.DetailRoute
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import com.github.gustavobarbosab.imovies.presentation.screen.home.server.HomeMockServer
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
import com.github.gustavobarbosab.imovies.sharedtest.home.robot.HomeRobot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@UninstallModules(NetworkParamsModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private val navController = mockk<NavController>(relaxed = true)
    private val mockHomeServer = HomeMockServer()
    private val testContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val homeRobot = HomeRobot(composeTestRule, testContext)

    private fun startTest() {
        val viewModel by composeTestRule.activity.viewModels<HomeScreenViewModel>()
        composeTestRule.setContent {
            IMoviesTheme {
                HomeScreen(viewModel = viewModel, navController = navController)
            }
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockHomeServer.stop()
    }

    @Test
    fun whenLoadMoviesSuccessfully_ShouldShowTheMoviesAndAcceptClick() {
        // GIVEN
        mockHomeServer {
            popularMoviesSuccess()
            topRatedMoviesSuccess()
            nowPlayingMoviesSuccess()
            upcomingMoviesSuccess()
        }

        // WHEN
        startTest()

        // THEN
        homeRobot {
            val firstMovieTitle = "Terrifier 3"

            onTopBanner {
                waitUntilMovieLoad(movieTitle = firstMovieTitle, timeoutMillis = 2000)
                assertTopBannerMovieTitle(movieTitle = firstMovieTitle)
                clickOnTopBannerMovie(movieTitle = firstMovieTitle)
            }

            onMovieSection(HomeMovieSectionType.POPULAR) {
                val substanceMovieTitle = "The Substance"
                scrollToSection()
                assertSectionTitle(sectionTitle = "Popular")
                waitUntilLoadMovie(movieTitle = firstMovieTitle)
                scrollToMovie(movieTitle = substanceMovieTitle)
                clickOnMovie(movieTitle = substanceMovieTitle)
            }

            onMovieSection(HomeMovieSectionType.TOP_RATED) {
                val gladiatorMovieTitle = "Gladiator II"
                scrollToSection()
                assertSectionTitle(sectionTitle = "Top Rated")
                waitUntilLoadMovie(movieTitle = firstMovieTitle)
                scrollToMovie(movieTitle = gladiatorMovieTitle)
                clickOnMovie(movieTitle = gladiatorMovieTitle)
            }

            onMovieSection(HomeMovieSectionType.UPCOMING) {
                val redOneMovieTitle = "Red One"
                scrollToSection()
                assertSectionTitle(sectionTitle = "Upcoming")
                waitUntilLoadMovie(movieTitle = firstMovieTitle)
                scrollToMovie(movieTitle = redOneMovieTitle)
                clickOnMovie(movieTitle = redOneMovieTitle)
            }

            verify(exactly = 4) {
                navController.navigate(any<DetailRoute>())
            }
        }
    }

    @Test
    fun whenThereIsAnErrorToLoadTheMovies_ShouldShowErrorState() {
        // GIVEN
        mockHomeServer {
            popularMoviesFailure()
            topRatedMoviesFailure()
            nowPlayingMoviesFailure()
            upcomingMoviesFailure()
        }

        // WHEN
        startTest()

        // THEN
        homeRobot {
            onTopBanner {
                assertFeedbackMessage("There was an error on loading the movies now playing.")
                assertFeedbackButtonText("Try again")
                clickOnFeedbackButton()
            }

            onMovieSection(HomeMovieSectionType.POPULAR) {
                assertSectionTitle("Popular")
                scrollToSection()
                assertFeedbackMessage("There was an error, try again.")
                assertFeedbackButtonText("Try again")
                clickOnFeedbackButton()
            }

            onMovieSection(HomeMovieSectionType.TOP_RATED) {
                assertSectionTitle("Top Rated")
                scrollToSection()
                assertFeedbackMessage("There was an error, try again.")
                assertFeedbackButtonText("Try again")
                clickOnFeedbackButton()
            }

            onMovieSection(HomeMovieSectionType.UPCOMING) {
                assertSectionTitle("Upcoming")
                scrollToSection()
                assertFeedbackMessage("There was an error, try again.")
                assertFeedbackButtonText("Try again")
                clickOnFeedbackButton()
            }
        }
    }
}
