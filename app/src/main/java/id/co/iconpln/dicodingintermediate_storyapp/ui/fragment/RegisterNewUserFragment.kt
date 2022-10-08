package id.co.iconpln.dicodingintermediate_storyapp.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import id.co.iconpln.dicodingintermediate_storyapp.R
import id.co.iconpln.dicodingintermediate_storyapp.databinding.FragmentRegisterNewUserBinding
import id.co.iconpln.dicodingintermediate_storyapp.repository.Result
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarError
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.RegisterViewModel
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.ViewModelsFactory

class RegisterNewUserFragment : DialogFragment() {

    private var _binding: FragmentRegisterNewUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CreateStoryDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelsFactory = ViewModelsFactory.getInstance()
        val viewModel: RegisterViewModel by viewModels { factory }

        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.btnRegisterSend.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            viewModel.sendRegister(name, email, password).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        is Result.Loading -> {
                            isLoading(true)
                        }
                        is Result.Success -> {
                            isLoading(false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnRegisterSend.visibility = View.GONE
            binding.progressLoading.visibility = View.VISIBLE
        } else {
            binding.btnRegisterSend.visibility = View.VISIBLE
            binding.progressLoading.visibility = View.GONE
        }
    }

    companion object {
        fun display(fragmentManager: FragmentManager?): RegisterNewUserFragment {
            val dialog = RegisterNewUserFragment()
            if (fragmentManager != null) {
                dialog.show(fragmentManager, Companion::class.java.name)
            }
            return dialog
        }
    }
}