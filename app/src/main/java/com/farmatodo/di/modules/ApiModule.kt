package com.farmatodo.di.modules

import com.farmatodo.interfaces.ApiServices
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [])
class ApiModule {

    companion object {
        const val CONNECT_TIMEOUT: Long = 10
        const val READ_TIME_OUT: Long = 30
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", ApiServices.TS.toString())
                .addQueryParameter("apikey", ApiServices.PUBLIC_KEY)
                .addQueryParameter("hash", ApiServices.getHash())
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient
    }

    @Singleton
    @Provides
    fun providesApiService(httpClient: OkHttpClient.Builder): ApiServices {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(ApiServices.BASE_URL)
            .client(httpClient.build())
            .build()
        return retrofit.create<ApiServices>(ApiServices::class.java)
    }
}