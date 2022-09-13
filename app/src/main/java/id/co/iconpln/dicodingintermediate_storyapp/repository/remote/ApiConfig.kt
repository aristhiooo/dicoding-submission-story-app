package id.co.iconpln.dicodingintermediate_storyapp.repository.remote

import id.co.iconpln.dicodingintermediate_storyapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun getApiService(): ApiService {
        val urlEndpoint = "https://story-api.dicoding.dev/v1/"
        val connectionTimeOut: Long = 30
        val client = OkHttpClient.Builder()
            .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
            .build()
        return Retrofit.Builder()
            .baseUrl(urlEndpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}