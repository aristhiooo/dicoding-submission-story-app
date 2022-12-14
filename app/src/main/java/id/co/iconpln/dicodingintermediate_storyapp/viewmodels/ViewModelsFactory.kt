package id.co.iconpln.dicodingintermediate_storyapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.iconpln.dicodingintermediate_storyapp.repository.AppRepository

class ViewModelsFactory private constructor(private val appRepository: AppRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(CreateStoryViewModel::class.java) -> {
                CreateStoryViewModel(appRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(appRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelsFactory? = null
        fun getInstance(): ViewModelsFactory = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ViewModelsFactory(Inject.provideRepository())
        }.also { INSTANCE = it }
    }
}