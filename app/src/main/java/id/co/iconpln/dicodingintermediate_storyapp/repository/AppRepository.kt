package id.co.iconpln.dicodingintermediate_storyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.co.iconpln.dicodingintermediate_storyapp.models.User
import id.co.iconpln.dicodingintermediate_storyapp.repository.local.UserPreference
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.ApiService
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AppRepository private constructor(private val apiService: ApiService) {
    private val userPreference = UserPreference()

    private val resultLogin = MediatorLiveData<Result<String>>()

    fun postLogin(email: String, password: String): LiveData<Result<String>> {
        resultLogin.value = Result.Loading
        apiService.getLoginCredential(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val loginResult = responseBody?.loginResult
                        userPreference.setUser(
                            User(
                                isLogin = true,
                                userId = loginResult?.userId,
                                name = loginResult?.name,
                                token = loginResult?.token
                            )
                        )
                        resultLogin.value = Result.Success(responseBody?.message.toString())
                    } else {
                        Timber.e(response.errorBody().toString() + response.body())
                        val errorBody = JSONObject(response.errorBody().toString())
                        val errorMessage = errorBody.getString("message")
                        resultLogin.value = Result.Error(errorMessage)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Timber.e(t)
                    Result.Error(t.message.toString())
                }

            })
        return resultLogin
    }

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(apiService: ApiService): AppRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AppRepository(apiService)
        }.also { INSTANCE = it }
    }
}