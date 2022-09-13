package id.co.iconpln.dicodingintermediate_storyapp.viewmodels

import androidx.lifecycle.ViewModel
import id.co.iconpln.dicodingintermediate_storyapp.repository.AppRepository
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.EditTextInputEmail
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.EditTextInputPassword

class LoginViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun sendLogin(email: String, password: String) = appRepository.postLogin(email, password)
}