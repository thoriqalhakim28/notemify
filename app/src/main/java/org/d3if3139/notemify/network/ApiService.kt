package org.d3if3139.notemify.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3139.notemify.model.About
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "thoriqalhakim28/notemify/json/json/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("data.json")
    suspend fun getAbout(): List<About>
}

object AboutApi {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    fun getAboutImg(imageId: String) : String {
        return "$BASE_URL$imageId.jpg"
    }
}

enum class ApiStatus {LOADING, SUCCESS, FAILED}