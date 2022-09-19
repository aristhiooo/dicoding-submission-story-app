package id.co.iconpln.dicodingintermediate_storyapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.co.iconpln.dicodingintermediate_storyapp.databinding.ActivityLoginBinding
import id.co.iconpln.dicodingintermediate_storyapp.repository.Result
import id.co.iconpln.dicodingintermediate_storyapp.repository.local.UserPreference
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarError
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarSuccess
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.LoginViewModel
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.ViewModelsFactory
import java.util.Timer
import kotlin.concurrent.timerTask

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        if (UserPreference().getUser().isLogin == true) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

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
                            isLoading(true)
                        }
                        is Result.Success -> {
                            isLoading(false)
                            binding.root.snackbarSuccess(result.data)
                            Timer().schedule(timerTask {
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            },1000)
                        }
                        is Result.Error -> {
                            isLoading(false)
                            binding.root.snackbarError(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnLogin.visibility = View.GONE
            binding.progressIndicator.visibility = View.VISIBLE
        } else {
            binding.btnLogin.visibility = View.VISIBLE
            binding.progressIndicator.visibility = View.GONE
        }
    }
}