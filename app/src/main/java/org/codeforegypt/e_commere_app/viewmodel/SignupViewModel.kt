package org.codeforegypt.e_commere_app.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.codeforegypt.domain.model.UserData
import org.codeforegypt.domain.repository.SignupRepository
import org.codeforegypt.e_commere_app.state.UserState
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val signupRepository: SignupRepository) : ViewModel() {

    private val _userData = MutableStateFlow<UserState>(UserState.Loading)
    val userDate: StateFlow<UserState> = _userData

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

      var fullName by mutableStateOf("")
          private set
      var phone by mutableStateOf("")
          private set
      var email by mutableStateOf("")
          private set
      var password by mutableStateOf("")
          private set
      var confirmPassword by mutableStateOf("")
          private set

    fun onFullNameChange(newFullName: String) {
        fullName = newFullName
    }
    fun onPhoneChange(newPhone: String) {
        phone = newPhone
    }
    fun onEmailChange(newEmail: String) {
        email = newEmail
    }
    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }
    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
    }
    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }


    fun signup(user: UserData){
        if(
            user.name.isEmpty() ||
            user.phone.isEmpty() ||
            user.email.isEmpty() ||
            user.password.isEmpty() ||
            user.rePassword.isEmpty()
            ) {
            _userData.value = UserState.Error("Please fill all fields")
            return
        }
        if(user.password != user.rePassword){
            _userData.value = UserState.Error("Passwords do not match")
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()){
            _userData.value = UserState.Error("Invalid email address")
            return
        }
        if(user.phone.length != 11){
            _userData.value = UserState.Error("Invalid phone number")
            return
        }
        if(user.password.length < 8){
            _userData.value = UserState.Error("Password must be at least 8 characters")
            return
        }
        _userData.value = UserState.Loading
        viewModelScope.launch {
            try {
                 val response = withContext(Dispatchers.IO) {signupRepository.sendUserData(user)}
                response.onSuccess { userData ->
                        _userData.value = UserState.Success(userData)
                    response.onFailure { exception ->
                           _userData.value = UserState.Error(exception.message ?: "Unknown error")
                        }
                }
            }
            catch(e: Exception){
                _userData.value = UserState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}