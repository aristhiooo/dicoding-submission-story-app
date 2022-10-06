package id.co.iconpln.dicodingintermediate_storyapp.ui.fragment

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.co.iconpln.dicodingintermediate_storyapp.BuildConfig
import id.co.iconpln.dicodingintermediate_storyapp.R
import id.co.iconpln.dicodingintermediate_storyapp.databinding.FragmentCreateStoryBinding
import id.co.iconpln.dicodingintermediate_storyapp.repository.Result
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarError
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarSuccess
import id.co.iconpln.dicodingintermediate_storyapp.utils.ImageHelper
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.CreateStoryViewModel
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.ViewModelsFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CreateStoryFragment : DialogFragment() {

    private var _binding: FragmentCreateStoryBinding? = null
    private val binding get() = _binding!!
    private var getFile: File? = null
    private lateinit var photoPath: String

    private val resultPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (!granted) {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Tidak mendapatkan Permission")
                    .setTitle("Warning!")
                    .setPositiveButton("OK") { _, _ -> dismiss() }
                    .show()
            }
        }

    private val openGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                val thisFile = ImageHelper.uriToFile(selectedImg, requireContext())
                getFile = thisFile

                binding.imgPhoto.setImageURI(selectedImg)
            }
        }

    private val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val photoTaken = File(photoPath)
                getFile = photoTaken
                val photo = BitmapFactory.decodeFile(getFile?.path)
                binding.imgPhoto.setImageBitmap(photo)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CreateStoryDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionGranted()) {
            resultPermission.launch(PERMISSIONS)
        }

        val factory: ViewModelsFactory = ViewModelsFactory.getInstance()
        val viewModel: CreateStoryViewModel by viewModels { factory }

        binding.toolbar.setNavigationOnClickListener { dismiss() }

        binding.fabOpenGallery.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a picture")
            openGallery.launch(chooser)
        }

        binding.fabOpenCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(requireActivity().packageManager)
            ImageHelper.createCustomTempFile(requireContext()).also {
                val photoUri: Uri =
                    FileProvider.getUriForFile(requireActivity(), BuildConfig.APPLICATION_ID, it)
                photoPath = it.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                openCamera.launch(intent)
            }
        }

        binding.buttonAdd.setOnClickListener {
            if (getFile != null) {
                val photo = ImageHelper.fileCompressor(getFile as File)
                val photoRequest = photo.asRequestBody("image/jpg".toMediaTypeOrNull())
                val photoBody = MultipartBody.Part.createFormData("photo", photo.name, photoRequest)
                val desc = binding.edAddDescription.editText?.text.toString().trim()
                    .toRequestBody("text/plain".toMediaType())
                val latitude = "-6.273738".toRequestBody("text/plain".toMediaType())
                val longitude = "106.847182".toRequestBody("text/plain".toMediaType())
                viewModel.sendNewStory(photoBody, desc, longitude, latitude).observe(this) { result ->
                    if (result != null) {
                        when(result) {
                            is Result.Loading -> {
                                isLoading(true)
                            }
                            is Result.Success -> {
                                isLoading(false)
                                binding.root.snackbarSuccess(result.data)
                                dismiss()
                            }
                            is Result.Error -> {
                                binding.root.snackbarError(result.error)
                                isLoading(false)
                            }
                        }
                    }
                }

            } else {
                binding.root.snackbarError("Select image")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.buttonAdd.visibility = View.GONE
            binding.progressLoading.visibility = View.VISIBLE
        } else {
            binding.buttonAdd.visibility = View.VISIBLE
            binding.progressLoading.visibility = View.GONE
        }
    }

    private fun allPermissionGranted() = PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        val PERMISSIONS = arrayOf(READ_EXTERNAL_STORAGE, CAMERA)

        fun display(fragmentManager: FragmentManager?): CreateStoryFragment {
            val dialog = CreateStoryFragment()
            if (fragmentManager != null) {
                dialog.show(fragmentManager, Companion::class.java.name)
            }
            return dialog
        }
    }
}