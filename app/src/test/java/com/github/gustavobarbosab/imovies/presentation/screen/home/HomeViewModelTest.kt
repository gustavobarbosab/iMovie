package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.MainCoroutineRule
import com.github.gustavobarbosab.imovies.domain.movies.GetMoviesUseCase
import com.github.gustavobarbosab.imovies.domain.movies.entity.MoviePage
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeActionResult.SectionUpdate
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeModelMapper
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val reducer = mockk<HomeReducer> {
        every { screenState } returns MutableStateFlow(HomeScreenState.initialState())
    }
    private val sideEffectProcessor = mockk<HomeSideEffectProcessor>(relaxed = true)
    private val mapper = mockk<HomeModelMapper>(relaxed = true)
    private val upcomingMoviesUseCase = mockk<GetMoviesUseCase.UpcomingMovies>(relaxed = true)
    private val topRatedMoviesUseCase = mockk<GetMoviesUseCase.TopRatedMovies>(relaxed = true)
    private val popularMoviesUseCase = mockk<GetMoviesUseCase.PopularMovies>(relaxed = true)
    private val nowPlayingMoviesUseCase = mockk<GetMoviesUseCase.NowPlayingMovies>(relaxed = true)

    private val viewModel: HomeViewModel = HomeViewModel(
        reducer,
        sideEffectProcessor,
        mapper,
        upcomingMoviesUseCase,
        topRatedMoviesUseCase,
        popularMoviesUseCase,
        nowPlayingMoviesUseCase
    )

    @Before
    fun setup() {
        every { reducer(any()) } returns Unit
    }

    @Test
    fun `when the intent is Init and there are movies, should emit the movies`() = runTest {
        // Given
        val mockedPage = MoviePage(1, 1, emptyList())
        val mockedMovieList = emptyList<HomeMovieModel>()
        every { mapper.map(mockedPage) } returns mockedMovieList

        coEvery {
            upcomingMoviesUseCase.getUpcomingMovies()
        } returns GetMoviesUseCase.Result.Success(mockedPage)

        coEvery {
            topRatedMoviesUseCase.getTopRatedMovies()
        } returns GetMoviesUseCase.Result.Success(mockedPage)

        coEvery {
            popularMoviesUseCase.getPopularMovies()
        } returns GetMoviesUseCase.Result.Success(mockedPage)

        coEvery {
            nowPlayingMoviesUseCase.getNowPlayingMovies()
        } returns GetMoviesUseCase.Result.Success(mockedPage)

        // When
        viewModel(HomeIntent.Init)

        // Then
        coVerify {
            reducer(HomeActionResult.TopBannerUpdate(SectionUpdate.Loading))
            reducer(HomeActionResult.TopBannerUpdate(SectionUpdate.Success(mockedMovieList)))

            reducer(HomeActionResult.UpdatePopularSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdatePopularSection(SectionUpdate.Success(mockedMovieList)))

            reducer(HomeActionResult.UpdateTopRatedSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdateTopRatedSection(SectionUpdate.Success(mockedMovieList)))

            reducer(HomeActionResult.UpdateUpcomingSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdateUpcomingSection(SectionUpdate.Success(mockedMovieList)))
        }
    }

    @Test
    fun `when the intent is Init and there is an error, should emit failure`() = runTest {
        // Given
        every { reducer(any()) } returns Unit
        coEvery {
            upcomingMoviesUseCase.getUpcomingMovies()
        } returns GetMoviesUseCase.Result.Error(null)

        coEvery {
            topRatedMoviesUseCase.getTopRatedMovies()
        } returns GetMoviesUseCase.Result.Error(null)

        coEvery {
            popularMoviesUseCase.getPopularMovies()
        } returns GetMoviesUseCase.Result.Error(null)

        coEvery {
            nowPlayingMoviesUseCase.getNowPlayingMovies()
        } returns GetMoviesUseCase.Result.Error(null)

        // When
        viewModel(HomeIntent.Init)

        // Then
        coVerify {
            reducer(HomeActionResult.TopBannerUpdate(SectionUpdate.Loading))
            reducer(HomeActionResult.TopBannerUpdate(SectionUpdate.Failure))

            reducer(HomeActionResult.UpdatePopularSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdatePopularSection(SectionUpdate.Failure))

            reducer(HomeActionResult.UpdateTopRatedSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdateTopRatedSection(SectionUpdate.Failure))

            reducer(HomeActionResult.UpdateUpcomingSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdateUpcomingSection(SectionUpdate.Failure))
        }
    }

    @Test
    fun `when the intent is Init and there is an empty list, should emit empty result`() = runTest {
        // Given
        every { reducer(any()) } returns Unit
        coEvery {
            upcomingMoviesUseCase.getUpcomingMovies()
        } returns GetMoviesUseCase.Result.ThereIsNoMovies

        coEvery {
            topRatedMoviesUseCase.getTopRatedMovies()
        } returns GetMoviesUseCase.Result.ThereIsNoMovies

        coEvery {
            popularMoviesUseCase.getPopularMovies()
        } returns GetMoviesUseCase.Result.ThereIsNoMovies

        coEvery {
            nowPlayingMoviesUseCase.getNowPlayingMovies()
        } returns GetMoviesUseCase.Result.ThereIsNoMovies

        // When
        viewModel(HomeIntent.Init)

        // Then
        coVerify {
            reducer(HomeActionResult.TopBannerUpdate(SectionUpdate.Loading))
            reducer(HomeActionResult.TopBannerUpdate(SectionUpdate.EmptyList))

            reducer(HomeActionResult.UpdatePopularSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdatePopularSection(SectionUpdate.EmptyList))

            reducer(HomeActionResult.UpdateTopRatedSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdateTopRatedSection(SectionUpdate.EmptyList))

            reducer(HomeActionResult.UpdateUpcomingSection(SectionUpdate.Loading))
            reducer(HomeActionResult.UpdateUpcomingSection(SectionUpdate.EmptyList))
        }
    }
}