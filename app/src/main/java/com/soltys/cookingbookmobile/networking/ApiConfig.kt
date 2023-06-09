package com.soltys.cookingbookmobile.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConfig {
    companion object {
        fun getApiService(): ApiService {

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("X-RapidAPI-Key", "40faa789d4mshbc3b0eb07e98efap1429f8jsn889b682016c1")
                    .addHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })

            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            httpClient.readTimeout(30, TimeUnit.SECONDS)

            val client: OkHttpClient = httpClient.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://tasty.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}