package com.github.gustavobarbosab.imovies.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import com.github.gustavobarbosab.imovies.MainCoroutineRule
import com.github.gustavobarbosab.imovies.domain.movies.detail.GetMovieDetailUseCase
import com.github.gustavobarbosab.imovies.presentation.screen.detail.model.DetailScreenMapper
import com.github.gustavobarbosab.imovies.presentation.screen.detail.model.DetailScreenModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DetailScreenViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val savedStateHandle = mockk<SavedStateHandle>()
    private val reducer = mockk<DetailScreenReducer>(relaxed = true) {
        every { screenState } returns MutableStateFlow(DetailScreenState.initialState(1))
    }
    private val reducerFactory = mockk<DetailScreenReducer.Factory> {
        every { create(any()) } returns reducer
    }
    private val sideEffectProcessor = mockk<DetailScreenSideEffectProcessor>(relaxed = true)
    private val mapper = mockk<DetailScreenMapper>(relaxed = true)
    private val getMovieDetailUseCase = mockk<GetMovieDetailUseCase>(relaxed = true)

    private val viewModel: DetailScreenViewModel = DetailScreenViewModel(
        savedStateHandle,
        reducerFactory,
        sideEffectProcessor,
        mapper,
        getMovieDetailUseCase
    )

    @Test
    fun `when the intent is Init and there are no errors, should show the movie detail`() =
        runTest {
            // Given
            val detailModel = DetailScreenModel(
                "Title",
                "Overview",
                "image url",
                5.0,
            )
            every { mapper.mapToModel(any()) } returns detailModel

            coEvery {
                getMovieDetailUseCase.getMovieDetail(any())
            } returns Result.success(mockk())

            // When
            viewModel(DetailScreenIntent.Init)

            // Then
            verify {
                reducer(DetailScreenActionResult.Loading)
                reducer(DetailScreenActionResult.Success(detailModel))
            }
        }

    @Test
    fun `when the intent is Init and there are errors, should show the error container`() =
        runTest {
            // Given
            coEvery {
                getMovieDetailUseCase.getMovieDetail(any())
            } returns Result.failure(mockk())

            // When
            viewModel(DetailScreenIntent.Init)

            // Then
            verify {
                reducer(DetailScreenActionResult.Loading)
                reducer(DetailScreenActionResult.Failure)
            }
        }

    @Test
    fun `when the intent is RetryLoad and there are no errors, should show the movie detail`() =
        runTest {
            // Given
            val detailModel = DetailScreenModel(
                "Title",
                "Overview",
                "image url",
                5.0,
            )
            every { mapper.mapToModel(any()) } returns detailModel

            coEvery {
                getMovieDetailUseCase.getMovieDetail(any())
            } returns Result.success(mockk())

            // When
            viewModel(DetailScreenIntent.RetryLoad)

            // Then
            verify {
                reducer(DetailScreenActionResult.Loading)
                reducer(DetailScreenActionResult.Success(detailModel))
            }
        }

    @Test
    fun `when the intent is RetryLoad and there are errors, should show the error container`() =
        runTest {
            // Given
            coEvery {
                getMovieDetailUseCase.getMovieDetail(any())
            } returns Result.failure(mockk())

            // When
            viewModel(DetailScreenIntent.RetryLoad)

            // Then
            verify {
                reducer(DetailScreenActionResult.Loading)
                reducer(DetailScreenActionResult.Failure)
            }
        }

    @Test
    fun `when the intent is BackPressed, should do nothing`() =
        runTest {
            // When
            viewModel(DetailScreenIntent.BackPressed)

            // Then
            verify(exactly = 0) {
                reducer(any())
            }
        }
}
