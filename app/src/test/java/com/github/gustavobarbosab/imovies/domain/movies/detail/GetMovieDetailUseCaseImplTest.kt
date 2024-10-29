package com.github.gustavobarbosab.imovies.domain.movies.detail

import com.github.gustavobarbosab.imovies.core.domain.DomainResponse
import com.github.gustavobarbosab.imovies.domain.movies.entity.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class GetMovieDetailUseCaseImplTest {

    private val repository = mockk<GetMovieDetailRepository>()
    private val getMovieDetailUseCase = GetMovieDetailUseCaseImpl(repository)

    @Test
    fun `should return success when repository returns success`() = runTest {
        // Arrange
        val movieId = 123L
        val expectedMovie = Movie(
            id = movieId,
            title = "Test Movie",
            overview = "Test overview",
            backdropPath = "test.jpg",
            voteAverage = 5.0,
            posterPath = "test.jpg",
            releaseDate = "2021-01-01",
            popularity = 10.0
        )

        coEvery { repository.getDetails(movieId) } returns DomainResponse.Success(expectedMovie)

        // Act
        val result = getMovieDetailUseCase.getMovieDetail(movieId)

        // Assert
        assertThat(result, `is`(Result.success(expectedMovie)))
    }

    @Test
    fun `should return failure when repository returns error`() = runTest {
        // Arrange
        val movieId = 123L
        coEvery { repository.getDetails(movieId) } returns DomainResponse.ExternalError(1, "Error")

        // Act
        val result = getMovieDetailUseCase.getMovieDetail(movieId)

        // Assert
        assertThat(result.isFailure, `is`(true))
    }
}