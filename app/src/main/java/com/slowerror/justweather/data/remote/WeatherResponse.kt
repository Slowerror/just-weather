package com.slowerror.justweather.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("name")
    val cityName: String,
    @SerialName("coord")
    val coordinate: Coordinate,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val sys: Sys
)

@Serializable
data class Coordinate(
    val lon: Double,
    val lat: Double
)

@Serializable
data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)


@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String
)


@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null
)
