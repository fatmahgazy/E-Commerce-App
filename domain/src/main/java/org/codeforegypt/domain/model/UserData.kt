package org.codeforegypt.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData (
    val id : Int? = null,
    val name: String,
    val email:String,
    val password: String,
    val rePassword: String,
    val phone: String
)