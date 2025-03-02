package org.codeforegypt.domain.repository

import org.codeforegypt.domain.model.UserData


interface SignupRepository {
    suspend fun sendUserData(user: UserData): Result<UserData>
}