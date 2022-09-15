package id.co.iconpln.dicodingintermediate_storyapp.repository.remote

import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.GetAllStoriesResponse
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.LoginResponse
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.PostMethodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun getLoginCredential(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun postRegisterAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<PostMethodResponse>

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") authToken: String
    ): Call<GetAllStoriesResponse>

    @Multipart
    @Headers("Content-Type: multipart/form-data")
    @POST("stories")
    fun postNewStory(
        @Part("description") description: RequestBody,
        @Part("photo") photo: MultipartBody.Part,
        @Part("lat") latitude: RequestBody,
        @Part("lon") longitude: RequestBody,
    ): Call<PostMethodResponse>
}