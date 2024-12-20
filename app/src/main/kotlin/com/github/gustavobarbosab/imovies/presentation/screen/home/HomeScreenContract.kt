package com.github.gustavobarbosab.imovies.presentation.screen.home

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.github.gustavobarbosab.imovies.R
import com.github.gustavobarbosab.imovies.common.ui.UiStateList
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieModel
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType

/**
 * This file contains all the classes that represent the contract of the Home Screen.
 * - ScreenState: Represents the state of the screen.
 * - Intent: Represents the intents that the screen can emit.
 * - Result: Represents the results from the intents.
 * - SideEffect: Represents the side effects generated by a intent and state.
 */

@Immutable
data class HomeScreenState(
    val topBannerMovies: UiStateList<HomeMovieModel>,
    val movieSectionMap: Map<HomeMovieSectionType, MovieSectionState>
) {
    val movieSectionList: List<MovieSectionState>
        get() = movieSectionMap.values.toList()

    data class MovieSectionState(
        val sectionType: HomeMovieSectionType,
        @StringRes val title: Int, // If it is created by the BE we could use string instead.
        val uiState: UiStateList<HomeMovieModel> = UiStateList.Loading,
        val hasMoreItems: Boolean = false,
    )

    fun copyAndUpdateMovieSection(
        sectionType: HomeMovieSectionType,
        sectionState: (MovieSectionState) -> MovieSectionState
    ): HomeScreenState {
        val updatedSections = movieSectionMap.toMutableMap()
        updatedSections[sectionType] = sectionState(updatedSections[sectionType] ?: return this)
        return copy(movieSectionMap = updatedSections)
    }

    companion object {
        fun initialState() = HomeScreenState(
            topBannerMovies = UiStateList.Loading,
            movieSectionMap = listOf(
                MovieSectionState(
                    HomeMovieSectionType.POPULAR,
                    title = R.string.home_popular_section_title
                ),
                MovieSectionState(
                    HomeMovieSectionType.TOP_RATED,
                    title = R.string.home_top_rated_section_title
                ),
                MovieSectionState(
                    HomeMovieSectionType.UPCOMING,
                    title = R.string.home_upcoming_section_title
                ),
            ).associateBy { it.sectionType }
        )
    }
}

sealed class HomeIntent {
    data object Init : HomeIntent()
    data object RetryLoadTopBanner : HomeIntent()
    data class RetryLoadListSection(val sectionType: HomeMovieSectionType) : HomeIntent()
    data class MovieClicked(val movie: HomeMovieModel) : HomeIntent()
}

sealed class HomeActionResult {
    data class TopBannerUpdate(val update: SectionUpdate) : HomeActionResult()

    // Here I could use a sealed class to represent these different sections
    // but I decided to implement like that to be more readable
    data class UpdatePopularSection(val update: SectionUpdate) : HomeActionResult()
    data class UpdateTopRatedSection(val update: SectionUpdate) : HomeActionResult()
    data class UpdateUpcomingSection(val update: SectionUpdate) : HomeActionResult()

    sealed class SectionUpdate {
        data object Loading : SectionUpdate()
        data object EmptyList : SectionUpdate()
        data object Failure : SectionUpdate()
        data class Success(val movies: List<HomeMovieModel>) : SectionUpdate()
    }
}

sealed class HomeSideEffect {
    // Pre
    data class NavigateToMovieDetail(val movieId: Long) : HomeSideEffect()

    // Post
    data class LoadMovieFailure(val sectionType: HomeMovieSectionType) : HomeSideEffect()
}