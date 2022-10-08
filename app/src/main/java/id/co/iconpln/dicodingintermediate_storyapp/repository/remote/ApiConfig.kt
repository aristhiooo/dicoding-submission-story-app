package id.co.iconpln.dicodingintermediate_storyapp.repository.remote

import com.google.gson.Gson
import id.co.iconpln.dicodingintermediate_storyapp.BuildConfig
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.PostMethodResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun getApiService(): ApiService {
        val connectionTimeOut: Long = 30
        val client = OkHttpClient.Builder()
            .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    fun errorResponseHandler(response: ResponseBody?): PostMethodResponse {
        val gson = Gson()
        return gson.fromJson(response?.charStream(), PostMethodResponse::class.java)
    }
}