package id.co.iconpln.dicodingintermediate_storyapp.viewmodels

import androidx.lifecycle.ViewModel
import id.co.iconpln.dicodingintermediate_storyapp.repository.AppRepository

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun getAllStories() = appRepository.getAllStories()
}