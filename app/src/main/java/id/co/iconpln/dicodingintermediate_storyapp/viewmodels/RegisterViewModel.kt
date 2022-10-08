package id.co.iconpln.dicodingintermediate_storyapp.viewmodels

import androidx.lifecycle.ViewModel
import id.co.iconpln.dicodingintermediate_storyapp.repository.AppRepository

class RegisterViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun sendRegister(name: String, email: String, password: String) =
        appRepository.postRegister(name, email, password)
}