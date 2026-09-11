package com.pegas.yuki.virtual.chat.ui.component.screen.histories

import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.usecase.character.GetCharactersUseCase
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoriesViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : BaseComposeViewModel<HistoriesUiState, HistoriesIntent, HistoriesEffect>(HistoriesUiState()) {

    override fun handleIntent(intent: HistoriesIntent) {
        when (intent) {
            HistoriesIntent.Initialize -> {
                if (currentState.hasLoadedInitialData || currentState.isLoading) return
                loadInitialData()
            }

            HistoriesIntent.Retry -> {
                if (currentState.characters.isEmpty()) {
                    loadInitialData(forceReload = true)
                } else {
                    updateState { copy(error = null) }
                    loadNextPage()
                }
            }

            HistoriesIntent.LoadMore -> loadNextPage()

            is HistoriesIntent.OpenCharacterDetail -> {
                if (intent.slug.isBlank()) return
                sendEffect(HistoriesEffect.NavigateCharacterDetail(intent.slug))
            }

            HistoriesIntent.NavigateBack -> {
                sendEffect(HistoriesEffect.NavigateBack)
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
        val state = currentState
        if (state.isLoading || state.isLoadingMore || !state.hasNextPage || state.error != null) return

        val nextPage = state.currentPage + 1

        launchIO {
            updateState { copy(isLoadingMore = true, error = null) }

            when (val result = getCharactersUseCase(PaginationQuery(page = nextPage, limit = PAGE_SIZE))) {
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
                            characters = (characters + result.data.items).distinctBy { it.id },
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