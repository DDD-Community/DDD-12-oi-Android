package com.ddd.oi.presentation.login.social

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import java.security.MessageDigest
import java.util.UUID

class GoogleLoginHandler() : LoginHandler {

    override suspend fun loginWithSocial(context: Context): SignInResult {
        try {
            val credentialManager = getCredentialManager(context)
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getGoogleIdOption())
                .build()
            val result = credentialManager.getCredential(context, request)
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
            return SignInResult.Success(googleIdTokenCredential.idToken)
        } catch (e: Exception) {
            return SignInResult.Failure(e)
        }
    }

    override suspend fun logout() {

    }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    private fun getGoogleIdOption(): GetGoogleIdOption {
        val clientId = "101169551620-376018bmi0nr41ac45ifeaj0hdbd5ukm.apps.googleusercontent.com"
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(
                clientId
            )
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()
    }

    private fun getCredentialManager(context: Context): CredentialManager {
        return CredentialManager.create(context)
    }
}