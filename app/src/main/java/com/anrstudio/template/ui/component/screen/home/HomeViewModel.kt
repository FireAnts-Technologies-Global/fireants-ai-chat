package com.pegas.aura.aigirlfriend.soul.ui.component.screen.home

import com.pegas.aura.aigirlfriend.soul.domain.model.common.AppResult
import com.pegas.aura.aigirlfriend.soul.domain.model.common.PaginationQuery
import com.pegas.aura.aigirlfriend.soul.domain.usecase.character.GetCharactersUseCase
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) :
    BaseComposeViewModel<HomeUiState, HomeIntent, HomeEffect>(HomeUiState()) {

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Initialize -> {
                if (currentState.hasLoadedInitialData || currentState.isLoading) return
                loadInitialData()
            }

            is HomeIntent.OpenCharacterDetail -> {
                if (intent.slug.isBlank()) return
                sendEffect(HomeEffect.NavigateCharacterDetail(intent.slug))
            }

            is HomeIntent.CreateAssistant -> {
                sendEffect(HomeEffect.CreateAssistant)
            }
            HomeIntent.LoadMore -> loadNextPage()

            HomeIntent.Retry -> {
                if (currentState.characters.isEmpty()) {
                    loadInitialData(forceReload = true)
                } else {
                    updateState { copy(error = null) }
                    loadNextPage()
                }
            }
        }
    }

    override fun dismissError() {
        updateState { copy(error = null) }
    }

    private fun loadInitialData(forceReload: Boolean = false) {
        if (!forceReload && currentState.hasLoadedInitialData) return

        launchIO {
            updateState {
                copy(
                    isLoading = true,
                    isLoadingMore = false,
                    characters = emptyList(),
                    currentPage = 0,
                    hasNextPage = true,
                    error = null
                )
            }

            when (val result = getCharactersUseCase(PaginationQuery(page = 1, limit = PAGE_SIZE))) {
                is AppResult.Failure -> {
                    updateState {
                        copy(
                            isLoading = false,
                            hasLoadedInitialData = false,
                            error = result.error
                        )
                    }
                }

                is AppResult.Success -> {
                    updateState {
                        copy(
                            characters = result.data.items,
                            currentPage = result.data.page,
                            hasNextPage = result.data.hasNext,
                            isLoading = false,
                            hasLoadedInitialData = true,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private fun loadNextPage() {
        if (
            currentState.isLoading ||
            currentState.isLoadingMore ||
            !currentState.hasNextPage ||
            currentState.error != null
        ) {
            return
        }

        val nextPage = currentState.currentPage + 1
        updateState { copy(isLoadingMore = true, error = null) }

        launchIO {
            when (
                val result = getCharactersUseCase(
                    PaginationQuery(page = nextPage, limit = PAGE_SIZE)
                )
            ) {
                is AppResult.Failure -> {
                    updateState {
                        copy(
                            isLoadingMore = false,
                            error = result.error
                        )
                    }
                }

                is AppResult.Success -> {
                    updateState {
                        copy(
                            characters = (characters + result.data.items)
                                .distinctBy { it.id },
                            currentPage = result.data.page,
                            hasNextPage = result.data.hasNext,
                            isLoadingMore = false,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}
