package com.mate.carpool.ui.screen.profile.lookup

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileLookUpViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : BaseViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    init {
        fetch()
    }

    fun fetch() {
        memberRepository.getMyProfile().onEach { result ->
            when (result) {
                is Result.Loading -> {
                }

                is Result.Success -> {
                    _profile.value = result.data
                }

                is Result.Error -> {
                    emitSnackbar(result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setProfileImage(uri: Uri) {
        memberRepository.updateProfileImage(uri).onEach { result ->
            when (result) {
                is Result.Loading -> {
                }

                is Result.Success -> {
                    emitSnackbar(result.data.message)
                    fetch()
                }

                is Result.Error -> {
                    emitSnackbar(result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

}