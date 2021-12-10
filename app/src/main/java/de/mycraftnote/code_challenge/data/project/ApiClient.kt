package de.mycraftnote.code_challenge.data.project


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val HEADER_KEY = "X-CN-API-KEY"
    private const val API_KEY = "c322f488-05e7-4f4a-a2b3-41a4f31af501"
    private const val BASE_URL = "https://europe-west1-craftnote-development.cloudfunctions.net/api/v1/"

    private val okHttpClient = OkHttpClient.Builder().addInterceptor {
        val newRequest = it.request().newBuilder().addHeader(HEADER_KEY, API_KEY).build()
        it.proceed(newRequest)
    }.build()

    val projectService: ProjectService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProjectService::class.java)
}