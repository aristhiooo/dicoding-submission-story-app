package id.co.iconpln.dicodingintermediate_storyapp.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import id.co.iconpln.dicodingintermediate_storyapp.R
import id.co.iconpln.dicodingintermediate_storyapp.databinding.FragmentRegisterNewUserBinding
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarError

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
        binding.toolbar.setNavigationOnClickListener { dismiss() }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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