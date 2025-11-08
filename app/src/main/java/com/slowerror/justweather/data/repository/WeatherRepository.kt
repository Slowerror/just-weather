package com.slowerror.justweather.data.repository

import com.slowerror.justweather.data.remote.WeatherResponse


interface WeatherRepository {
    suspend fun getCurrentWeatherByLatLon(lat: Double, lon: Double) : WeatherResponse

    suspend fun getCurrentWeatherByCity(cityName: String) : WeatherResponse
}