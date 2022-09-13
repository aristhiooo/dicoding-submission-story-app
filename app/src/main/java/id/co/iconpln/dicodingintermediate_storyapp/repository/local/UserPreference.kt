package id.co.iconpln.dicodingintermediate_storyapp.repository.local

import android.content.Context
import id.co.iconpln.dicodingintermediate_storyapp.ApplicationController.Companion.instance
import id.co.iconpln.dicodingintermediate_storyapp.models.User

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

    companion object {
        private const val USER_PREF = "user_pref"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val USER_TOKEN = "user_token"
        private const val USER_IS_LOGIN = "user_is_login"
    }
}