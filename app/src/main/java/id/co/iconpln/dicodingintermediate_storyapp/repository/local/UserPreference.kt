package id.co.iconpln.dicodingintermediate_storyapp.repository.local

import android.content.Context
import android.content.Intent
import id.co.iconpln.dicodingintermediate_storyapp.ApplicationController.Companion.instance
import id.co.iconpln.dicodingintermediate_storyapp.models.User
import timber.log.Timber

internal class UserPreference {

    private val preference = instance.applicationContext.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)

    fun setUser(value: User) {
        val editor = preference.edit()
        editor.apply {
            putString(USER_ID, value.userId)
            putString(USER_NAME, value.name)
            putString(USER_TOKEN, value.token)
            putBoolean(USER_IS_LOGIN, value.isLogin!!)
        }
        editor.apply()
    }

    fun getUser(): User {
        val user = User()
        user.apply {
            userId = preference.getString(USER_ID, "")
            name = preference.getString(USER_NAME, "")
            token = preference.getString(USER_TOKEN, "")
            isLogin = preference.getBoolean(USER_IS_LOGIN, false)
        }
        return user
    }

    fun clearAndLogout() {
        val editor = preference.edit()
        editor.clear()
        editor.apply()
        val intent: Intent? = instance.applicationContext.packageManager.getLaunchIntentForPackage(
            instance.applicationContext.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        instance.applicationContext.startActivity(intent)
        Timber.i("Clear user data")
    }

    companion object {
        private const val USER_PREF = "user_pref"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val USER_TOKEN = "user_token"
        private const val USER_IS_LOGIN = "user_is_login"
    }
}