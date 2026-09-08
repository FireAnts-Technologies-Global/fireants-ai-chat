package com.anrstudio.template.ui.component.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anrstudio.template.domain.usecase.auth.GetAuthUserFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAuthUserFlowUseCase: GetAuthUserFlowUseCase
) : ViewModel() {
    val coinBalance = getAuthUserFlowUseCase().map { it?.coinBalance ?: 0 }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
}
