package com.slowerror.justweather.di

import com.slowerror.justweather.BuildConfig
import com.slowerror.justweather.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_KEY = BuildConfig.OPEN_WEATHER_MAP_API_KEY
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"


    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Этот интерсептор встраивает параметры (например api ключ) во все запросы
    @Provides
    @Singleton
    fun provideQueryInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        queryInterceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(queryInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)

}