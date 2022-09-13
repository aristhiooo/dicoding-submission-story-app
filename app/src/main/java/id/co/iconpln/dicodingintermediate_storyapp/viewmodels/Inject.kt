package id.co.iconpln.dicodingintermediate_storyapp.viewmodels

import android.content.Context
import id.co.iconpln.dicodingintermediate_storyapp.repository.AppRepository
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.ApiConfig

object Inject {
    fun provideRepository(context: Context): AppRepository  {
        val apiService = ApiConfig.getApiService()
        return AppRepository.getInstance(apiService)
    }
}