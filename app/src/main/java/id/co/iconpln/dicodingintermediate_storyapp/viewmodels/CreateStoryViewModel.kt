package id.co.iconpln.dicodingintermediate_storyapp.viewmodels

import androidx.lifecycle.ViewModel
import id.co.iconpln.dicodingintermediate_storyapp.repository.AppRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoryViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun sendNewStory(photo: MultipartBody.Part,
                     description: RequestBody,
                     longitude: RequestBody,
                     latitude: RequestBody
    ) = appRepository.postNewStory(photo, description, longitude, latitude)
}