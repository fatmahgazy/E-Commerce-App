package org.codeforegypt.data.network


import org.codeforegypt.domain.model.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/signup")
    suspend fun sendUserData(@Body user: UserData): Response<UserData>
}