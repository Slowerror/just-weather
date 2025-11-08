package com.slowerror.justweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET(WEATHER_ENDPOINT)
    suspend fun getWeatherByLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = "ru"
    ): WeatherResponse


    @GET(WEATHER_ENDPOINT)
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("lang") lang: String = "ru"
    ): WeatherResponse

    @GET(FORECAST_ENDPOINT)
    suspend fun getForecastFiveDays(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String = "ru"
    ) : ForecastResponse

    companion object {
        private const val WEATHER_ENDPOINT = "weather"
        private const val FORECAST_ENDPOINT = "forecast"
    }
}