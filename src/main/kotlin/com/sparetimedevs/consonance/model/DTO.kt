package com.sparetimedevs.consonance.model

import io.ktor.auth.Credential

data class UserPasswordCredential(val email: String, val password: String) : Credential
