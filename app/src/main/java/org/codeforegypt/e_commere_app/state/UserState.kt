package org.codeforegypt.e_commere_app.state

import org.codeforegypt.domain.model.UserData

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: UserData) : UserState()
    data class Error(val message: String) : UserState()
}