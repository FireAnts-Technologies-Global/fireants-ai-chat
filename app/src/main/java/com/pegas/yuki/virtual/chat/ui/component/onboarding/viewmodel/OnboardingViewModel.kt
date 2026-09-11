package com.pegas.yuki.virtual.chat.ui.component.onboarding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pegas.yuki.virtual.chat.ui.bases.BaseViewModel

class OnboardingViewModel : BaseViewModel() {

    private val _isNeedNextPage = MutableLiveData<Boolean>()
    val isNeedNextPage: LiveData<Boolean> = _isNeedNextPage

    fun onNextClicked() {
        _isNeedNextPage.value = true
    }

    fun onNextPageHandled() {
        _isNeedNextPage.value = false
    }
}