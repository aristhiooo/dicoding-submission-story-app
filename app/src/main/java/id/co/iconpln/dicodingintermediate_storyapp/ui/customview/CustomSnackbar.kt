package id.co.iconpln.dicodingintermediate_storyapp.ui.customview

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import id.co.iconpln.dicodingintermediate_storyapp.R

object CustomSnackbar {
    fun View.snackbarSuccess(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setBackgroundTint(
                ContextCompat.getColor(
                    context,
                    R.color.green_message_success
                )
            )
            snackbar.setTextColor(Color.WHITE)
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
        }.show()
    }

    fun View.snackbarError(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setBackgroundTint(
                ContextCompat.getColor(
                    context,
                    R.color.red_message_error
                )
            )
            snackbar.setTextColor(Color.WHITE)
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
        }.show()
    }
}