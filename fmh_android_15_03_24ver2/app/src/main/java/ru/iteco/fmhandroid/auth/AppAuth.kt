package ru.iteco.fmhandroid.auth

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.iteco.fmhandroid.dto.AuthState
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class AppAuth @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val ATKey = "access"
    private val RTKey = "refresh"

    /**
     * Набор токенов. `null` если пользователь не авторизован
     */
    var authState by Delegates.observable(
        createInitialAuthState()
    )
    { _, _, authState ->
        with(prefs.edit()) {
            putString(ATKey, authState?.accessToken)
            putString(RTKey, authState?.refreshToken)
            apply()
        }
    }

    private fun createInitialAuthState(): AuthState? {
        val accessToken2 = prefs.getString(ATKey, null)
        return if (accessToken2 == null) null else {
            val refreshToken2 = prefs.getString(RTKey, null)
            checkNotNull(refreshToken2) {
                "accessToken и refreshToken должны быть не null"
            }
            AuthState(
                accessToken = accessToken2,
                refreshToken = refreshToken2
            )
        }
    }
}
