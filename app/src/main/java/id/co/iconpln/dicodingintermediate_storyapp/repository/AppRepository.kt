package id.co.iconpln.dicodingintermediate_storyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.co.iconpln.dicodingintermediate_storyapp.models.User
import id.co.iconpln.dicodingintermediate_storyapp.repository.local.UserPreference
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.ApiConfig
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.ApiService
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.GetAllStoriesResponse
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.ListStoryItem
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.LoginResponse
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.PostMethodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AppRepository private constructor(private val apiService: ApiService) {
    private val userPreference = UserPreference()

    private val resultLogin = MediatorLiveData<Result<String>>()
    private val resultRegister = MediatorLiveData<Result<String>>()
    private val resultGetAllStories = MediatorLiveData<Result<List<ListStoryItem>>>()
    private val resultPostNewStory = MediatorLiveData<Result<String>>()

    fun postLogin(email: String, password: String): LiveData<Result<String>> {
        resultLogin.value = Result.Loading
        apiService.getLoginCredential(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val loginResult = responseBody?.loginResult
                    userPreference.setUser(
                        User(true, loginResult?.name, loginResult?.userId, loginResult?.token)
                    )
                    resultLogin.value = Result.Success(responseBody?.message.toString().uppercase())
                } else {
                    Timber.e(response.errorBody()?.charStream().toString())
                    val errorBody = ApiConfig.errorResponseHandler(response.errorBody())
                    val errorMessage = errorBody.message
                    resultLogin.value = Result.Error(errorMessage)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Timber.e(t)
                resultLogin.value = Result.Error(t.message.toString())
            }
        })
        return resultLogin
    }

    fun postRegister(name: String, email: String, password: String): LiveData<Result<String>> {
        resultRegister.value = Result.Loading
        apiService.postRegisterAccount(name, email, password)
            .enqueue(object : Callback<PostMethodResponse> {
                override fun onResponse(
                    call: Call<PostMethodResponse>,
                    response: Response<PostMethodResponse>
                ) {
                    if (response.isSuccessful) {
                        resultRegister.value = Result.Success(response.body()?.message.toString())
                    } else {
                        resultRegister.value =
                            Result.Error(ApiConfig.errorResponseHandler(response.errorBody()).message.uppercase())
                    }
                }

                override fun onFailure(call: Call<PostMethodResponse>, t: Throwable) {
                    Timber.e(t)
                    resultRegister.value = Result.Error(t.message.toString())
                }
            })
        return resultRegister
    }

    fun getAllStories(): LiveData<Result<List<ListStoryItem>>> {
        resultGetAllStories.value = Result.Loading
        apiService.getAllStories("Bearer ${UserPreference().getUser().token}")
            .enqueue(object : Callback<GetAllStoriesResponse> {
                override fun onResponse(
                    call: Call<GetAllStoriesResponse>,
                    response: Response<GetAllStoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        val stories = response.body()?.listStory
                        resultGetAllStories.value = Result.Success(stories!!)
                    } else {
                        val errorBody = ApiConfig.errorResponseHandler(response.errorBody())
                        val errorMessage = errorBody.message
                        resultGetAllStories.value = Result.Error(errorMessage)
                        Timber.e("$errorBody")
                    }
                }

                override fun onFailure(call: Call<GetAllStoriesResponse>, t: Throwable) {
                    Timber.e(t)
                    resultGetAllStories.value = Result.Error(t.message.toString())
                }
            })
        return resultGetAllStories
    }

    fun postNewStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        longitude: RequestBody,
        latitude: RequestBody
    ): LiveData<Result<String>> {
        resultPostNewStory.value = Result.Loading
        apiService.postNewStory(
            "Bearer ${UserPreference().getUser().token}",
            description, photo, latitude, longitude
        ).enqueue(object : Callback<PostMethodResponse> {
            override fun onResponse(
                call: Call<PostMethodResponse>,
                response: Response<PostMethodResponse>
            ) {
                if (response.isSuccessful) {
                    val message = response.body()?.message.toString()
                    resultPostNewStory.value = Result.Success(message)
                } else {
                    val errorBody = ApiConfig.errorResponseHandler(response.errorBody())
                    val errorMessage = errorBody.message
                    resultPostNewStory.value = Result.Error(errorMessage)
                    Timber.e("$errorBody")
                }
            }

            override fun onFailure(call: Call<PostMethodResponse>, t: Throwable) {
                Timber.e(t)
                resultPostNewStory.value = Result.Error(t.message.toString())
            }

        })
        return resultPostNewStory
    }

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null
        fun getInstance(apiService: ApiService): AppRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AppRepository(apiService)
        }.also { INSTANCE = it }
    }
}