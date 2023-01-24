package com.mate.carpool.ui.screen.profile.modify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.model.domain.UserRole
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.util.map
import com.mate.carpool.util.substring
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek

class ProfileModifyViewModel @AssistedInject constructor(
    @Assisted profile: Profile,
    private val memberRepository: MemberRepository
) : BaseViewModel() {

    @AssistedFactory
    interface InitialProfileAssistedFactory {
        fun create(profile: Profile): ProfileModifyViewModel
    }

    private val originalProfile = profile.copy()
    private val _profile = MutableStateFlow(profile)
    val profile = _profile.asStateFlow()

    val enableConfirm = _profile.map(viewModelScope) { newProfile ->
        newProfile.phone.length == 11 && (originalProfile.phone != newProfile.phone || originalProfile.userRole != newProfile.userRole || originalProfile.daysOfUse != newProfile.daysOfUse)
    }

    fun setPhone(phone: String) {
        val result = phone
            .filter { it.isDigit() }
            .substring(11)
        _profile.update { it.copy(phone = result) }
    }

    fun setUserRole(userRole: UserRole) {
        _profile.update { it.copy(userRole = userRole) }
    }

    fun addDayOfUse(dayOfWeek: DayOfWeek) {
        _profile.update { it.copy(daysOfUse = (it.daysOfUse + dayOfWeek).sorted()) }
    }

    fun removeDayOfUse(dayOfWeek: DayOfWeek) {
        _profile.update { it.copy(daysOfUse = it.daysOfUse - dayOfWeek) }
    }

    fun save() {
        memberRepository.updateMyProfile(
            phone = profile.value.phone,
            userRole = profile.value.userRole,
            daysOfUse = profile.value.daysOfUse
        ).onEach { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    emitSnackbar("수정되었습니다.")
                    emitEvent(EVENT_FINISH)
                }

                is Result.Error -> {
                    emitSnackbar(result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {

        const val EVENT_FINISH = "EVENT_FINISH"

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: InitialProfileAssistedFactory,
            initialProfile: Profile
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(initialProfile) as T
            }
        }
    }
}