package com.example.openweatherapp

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getWeatherData(
        @Query("lat")latitude:String,
        @Query("lon")longitude: String,
        @Query("APPID") api_key: String
    ): Call<WeatherData>

    @GET("weather")
    fun geCityWeather(
        @Query("q")cityName: String,
        @Query("APPID") api_key: String
    ): Call<WeatherData>
}