package com.slowerror.justweather.data.repository

import com.slowerror.justweather.data.remote.WeatherApiService
import com.slowerror.justweather.data.remote.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApiService
) : WeatherRepository {

    override suspend fun getCurrentWeatherByLatLon(lat: Double, lon: Double): WeatherResponse =
        weatherApi.getWeatherByLatLon(lat, lon)

    override suspend fun getCurrentWeatherByCity(cityName: String): WeatherResponse =
        weatherApi.getWeatherByCity(cityName)

}