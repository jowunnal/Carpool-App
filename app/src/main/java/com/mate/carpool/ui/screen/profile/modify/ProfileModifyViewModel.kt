package com.mate.carpool.ui.screen.profile.modify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.model.domain.UserRole
import com.mate.carpool.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek

class ProfileModifyViewModel @AssistedInject constructor(
    @Assisted profile: Profile
) : BaseViewModel() {

    @AssistedFactory
    interface InitialProfileAssistedFactory {

        fun create(profile: Profile): ProfileModifyViewModel
    }

    private val _profile = MutableStateFlow(profile)
    val profile = _profile.asStateFlow()

    fun setPhone(phone: String) {
        _profile.update { it.copy(phone = phone) }
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

    }

    companion object {

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