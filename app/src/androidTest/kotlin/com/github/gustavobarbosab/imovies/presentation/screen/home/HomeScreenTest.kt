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
import com.github.gustavobarbosab.imovies.presentation.screen.home.robot.HomeRobot
import com.github.gustavobarbosab.imovies.presentation.screen.home.server.HomeMockServer
import com.github.gustavobarbosab.imovies.presentation.theme.IMoviesTheme
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
    private val homeRobot = HomeRobot(
        composeTestRule,
        testContext
    )

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
            onTopBanner {
                val terrifierMovieName = "Terrifier 3"
                waitUntilMovieLoad(movieTitle = terrifierMovieName, timeoutMillis = 2000)
                assertTopBannerMovieTitle(movieTitle = terrifierMovieName)
                clickOnTopBannerMovie(movieTitle = terrifierMovieName)
            }

            onMovieSection(HomeMovieSectionType.POPULAR) {
                val theSubstanceMovieName = "The Substance"
                assertTitle("Popular")
                waitUntilLoadMovie(movieTitle = theSubstanceMovieName)
                scrollToMovie(movieTitle = theSubstanceMovieName)
                clickOnMovie(movieTitle = theSubstanceMovieName)
            }

            verify(exactly = 2) {
                navController.navigate(any<DetailRoute>())
            }
        }
    }

//    @Test
//    fun whenThereIsAnErrorToLoadTheMovies_ShouldShowErrorState() {
//        val state = HomeScreenState.initialState().copy(
//            topBannerMovies = UiStateList.Failure("Error"),
//            movieSectionMap = mapOf(
//                HomeMovieSectionType.POPULAR to HomeScreenState.MovieSectionState(
//                    HomeMovieSectionType.POPULAR,
//                    title = R.string.home_popular_section_title,
//                    uiState = UiStateList.Failure("Error")
//                ),
//            )
//        )
//
//        composeTestRule.when (state)
//
//        topBannerRobot {
//            assertFeedbackMessage("There was an error on loading the movies now playing.")
//            assertFeedbackButtonText("Try again")
//            clickOnFeedbackButton()
//        }
//
//        movieSectionRobot {
//            assertSectionTitle("Popular")
//            assertFeedbackMessage("There was an error, try again.")
//            assertFeedbackButtonText("Try again")
//            clickOnFeedbackButton()
//        }
//
//        verify {
//            onRetryLoadTopBanner()
//            onRetryLoadSection(HomeMovieSectionType.POPULAR)
//        }
//    }
}
