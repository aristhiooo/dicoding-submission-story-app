package id.co.iconpln.dicodingintermediate_storyapp.repository.remote

import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun getLoginCredential(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}