package org.codeforegypt.data.repositoryImp

import org.codeforegypt.data.network.ApiService
import org.codeforegypt.domain.repository.SignupRepository
import org.codeforegypt.domain.model.UserData

import javax.inject.Inject

class SignupRepositoryImp @Inject constructor(private val apiService: ApiService) : SignupRepository {
    override suspend fun sendUserData(user: UserData): Result<UserData> {
        return try {
            val response = apiService.sendUserData(user)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                }
                return Result.failure(Exception("Response body is null"))
            }
            else{
                Result.failure(Exception("Response not successful"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

}
