package com.slowerror.justweather.data.remote

import com.slowerror.justweather.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET(WEATHER_ENDPOINT)
    suspend fun getWeatherByLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = API_KEY
    ): WeatherResponse


    @GET(WEATHER_ENDPOINT)
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = API_KEY
    ): WeatherResponse

    @GET(FORECAST_ENDPOINT)
    suspend fun getForecastFiveDays(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = API_KEY
    ) : ForecastResponse

    companion object {
        private const val API_KEY = BuildConfig.OPEN_WEATHER_MAP_API_KEY
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        private const val WEATHER_ENDPOINT = "weather"
        private const val FORECAST_ENDPOINT = "forecast"

        private val json = Json {
            ignoreUnknownKeys = true
        }

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()


        val weatherApi: WeatherApiService by lazy {
            retrofit.create(WeatherApiService::class.java)
        }

    }
}