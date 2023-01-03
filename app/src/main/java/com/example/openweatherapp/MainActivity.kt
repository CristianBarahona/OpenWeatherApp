package com.example.openweatherapp

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity() {

    //Declaring variables from the layout
    private lateinit var cityTextView: TextView
    private lateinit var tempTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var minTemperatureTextView: TextView
    private lateinit var maxTempTextView: TextView
    private lateinit var sunriseTextView: TextView
    private lateinit var sunsetTextView: TextView
    private lateinit var windTextView: TextView
    private lateinit var pressureTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var descriptionTextView: TextView

    //City and api key for API call
    private  val apiKey = "efeebd5ed0932b0ae5fd8895189229b2"
    private val city = "Suffolk County"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempTextView = findViewById(R.id.temp)
        cityTextView = findViewById(R.id.address)
        dateTextView = findViewById(R.id.dateText)
        descriptionTextView= findViewById(R.id.description)
        minTemperatureTextView= findViewById(R.id.temp_min)
        maxTempTextView = findViewById(R.id.temp_max)
        sunriseTextView = findViewById(R.id.sunriseText)
        sunsetTextView = findViewById(R.id.sunsetText)
        windTextView = findViewById(R.id.windText)
        pressureTextView = findViewById(R.id.pressureText)
        humidityTextView  = findViewById(R.id.humidityText)
        descriptionTextView= findViewById(R.id.description)
        humidityTextView = findViewById(R.id.humidityText)

        //Setting the APi call and interface, should display a toast if failed to get a response
        Utils.getApiInterface()?.geCityWeather(city,
            apiKey)?.enqueue(object  :Callback<WeatherData>{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if(response.isSuccessful){
                    setDataOnViews(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Toast.makeText( applicationContext,"ERROR",Toast.LENGTH_SHORT).show()
            }
        })
    }
    //Launching a setDataOnViews() function
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDataOnViews(body: WeatherData?) {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate =sdf.format(Date())

        dateTextView.text = currentDate
        tempTextView.text = "${kelvinToCelsius(body!!.main.temp )}°F"
        descriptionTextView.text = body.weather[0].main
        minTemperatureTextView.text = "Min Temp: ${kelvinToCelsius(body!!.main.temp_min)} °F"
        maxTempTextView.text = "Max Temp: ${kelvinToCelsius(body!!.main.temp_max)} °F"
        sunriseTextView.text = timeStampToLocalDate(body.sys.sunrise.toLong())
        sunsetTextView.text = timeStampToLocalDate(body.sys.sunset.toLong())
        windTextView.text = body.wind.speed.toString() + " m/s"
        pressureTextView.text = body.main.pressure.toString()
        humidityTextView.text = body.main.humidity.toString() + " %"

    }

    //Function to convert sunrise/sunset data in  time of the day
    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeStampToLocalDate(timeStamp: Long): String {
        val localTime = timeStamp.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }
    return localTime.toString()
    }

    //Function to convert kelvin degrees to farenheit
    private fun kelvinToCelsius(temp: Double): Any {
        var intTemp = temp
        intTemp = (intTemp * 1.8) - 459.67
        return intTemp.toBigDecimal().setScale(1,RoundingMode.UP).toDouble()
    }


}













