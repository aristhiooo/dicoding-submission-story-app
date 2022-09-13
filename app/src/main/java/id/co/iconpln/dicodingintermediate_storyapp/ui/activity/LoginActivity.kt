package id.co.iconpln.dicodingintermediate_storyapp.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.co.iconpln.dicodingintermediate_storyapp.databinding.ActivityLoginBinding
import id.co.iconpln.dicodingintermediate_storyapp.repository.Result
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarError
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarSuccess
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.LoginViewModel
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.ViewModelsFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        Glide.with(this)
            .load("https://bucket.cloud.lintasarta.co.id:8082/dts-pelatihan/thumbnail/271f81da-cc11-4080-ae9c-116c188227ea-August.jpeg")
            .into(binding.ivLogoLogin)

        val factory: ViewModelsFactory = ViewModelsFactory.getInstance(this)
        val viewModel: LoginViewModel by viewModels { factory }

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().trim()
            val password = binding.edLoginPassword.text.toString().trim()

            viewModel.sendLogin(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {

                        }
                        is Result.Success -> {
                            binding.root.snackbarSuccess(result.data)
                        }
                        is Result.Error -> {
                            binding.root.snackbarError(result.error.uppercase())
                        }
                    }
                }
            }
        }
    }
}