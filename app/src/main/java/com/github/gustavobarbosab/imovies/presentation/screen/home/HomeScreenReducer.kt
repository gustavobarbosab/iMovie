package com.github.gustavobarbosab.imovies.presentation.screen.home

import com.github.gustavobarbosab.imovies.common.presentation.UiStateList
import com.github.gustavobarbosab.imovies.core.presentation.arch.Reducer
import com.github.gustavobarbosab.imovies.presentation.screen.home.HomeActionResult.SectionUpdate
import com.github.gustavobarbosab.imovies.presentation.screen.home.model.HomeMovieSectionType
import javax.inject.Inject

class HomeScreenReducer @Inject constructor(

) : Reducer<HomeScreenState, HomeActionResult>(HomeScreenState.initialState()) {

    override fun reduce(
        result: HomeActionResult,
        currentState: HomeScreenState
    ): HomeScreenState = when (result) {
        is HomeActionResult.TopBannerUpdate -> currentState.copy(
            topBannerMovies = when (result.update) {
                is SectionUpdate.Loading -> UiStateList.Loading
                is SectionUpdate.Failure -> UiStateList.Failure()
                is SectionUpdate.Success -> UiStateList.Success(result.update.movies)
                SectionUpdate.EmptyList -> UiStateList.EmptyList
            }
        )

        is HomeActionResult.UpdatePopularSection -> reduceUpdateSection(
            HomeMovieSectionType.POPULAR,
            result.update,
            currentState
        )

        is HomeActionResult.UpdateTopRatedSection -> reduceUpdateSection(
            HomeMovieSectionType.TOP_RATED,
            result.update,
            currentState
        )

        is HomeActionResult.UpdateUpcomingSection -> reduceUpdateSection(
            HomeMovieSectionType.UPCOMING,
            result.update,
            currentState
        )
    }

    private fun reduceUpdateSection(
        sectionType: HomeMovieSectionType,
        sectionUpdate: SectionUpdate,
        currentState: HomeScreenState
    ): HomeScreenState {
        return when (sectionUpdate) {
            is SectionUpdate.Loading -> currentState.copyAndUpdateMovieSection(
                sectionType = sectionType,
                sectionState = { oldSectionState -> oldSectionState.copy(uiState = UiStateList.Loading) }
            )

            is SectionUpdate.Failure -> currentState.copyAndUpdateMovieSection(
                sectionType = sectionType,
                sectionState = { oldSectionState -> oldSectionState.copy(uiState = UiStateList.Failure()) }
            )

            is SectionUpdate.Success -> currentState.copyAndUpdateMovieSection(
                sectionType = sectionType,
                sectionState = { oldSectionState ->
                    oldSectionState.copy(uiState = UiStateList.Success(sectionUpdate.movies))
                }
            )

            SectionUpdate.EmptyList -> currentState.copyAndUpdateMovieSection(
                sectionType = sectionType,
                sectionState = { oldSectionState ->
                    oldSectionState.copy(uiState = UiStateList.EmptyList)
                }
            )
        }
    }
}
