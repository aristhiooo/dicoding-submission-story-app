package id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostMethodResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
) : Parcelable
