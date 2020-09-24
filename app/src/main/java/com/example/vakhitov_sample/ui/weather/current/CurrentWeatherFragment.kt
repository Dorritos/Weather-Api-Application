package com.example.vakhitov_sample.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.vakhitov_sample.R
import com.example.vakhitov_sample.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.current_weather_fragment.view.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            it?.let {
                group_loading.visibility = View.GONE
                updateDayTime(it.isDay)
                updateLocation("Kazan")
                updateTemperature(it.temperature, it.feelslike)
                updatePrecip(it.precip)
                updateWind(it.windSpeed)
            }
        })

        val currentLocation = viewModel.weatherLocation.await()
        currentLocation.observe( this@CurrentWeatherFragment, Observer { location ->
             if (location == null)
             {
                 return@Observer
             }
                updateLocation(location.name)
        })

        val currentDate = viewModel.weatherLocation.await()
        currentLocation.observe( this@CurrentWeatherFragment, Observer {
            updateDate(it.localtime)
        })
    }

    private fun updateLocation(name: String) {
        textViewCity.text = "$name"
    }

    private fun updateDate(localtime: String) {
        textViewDate.text = "$localtime"
    }

    private fun updateTemperature(temperature: Int, feelslike: Int) {
        if (temperature > 30 || temperature < -20)
        {
            imageViewDead.setImageResource(R.drawable.ic_dead)
            textViewDead.text = "$temperature °C"
            imageViewDayTime.visibility = View.INVISIBLE
            textViewCity.visibility = View.INVISIBLE
            textViewDate.visibility = View.INVISIBLE
            textViewTemp.visibility = View.INVISIBLE
            textViewFeels.visibility = View.INVISIBLE
            textViewWind.visibility = View.INVISIBLE
            textViewPrecip.visibility = View.INVISIBLE

        }
        else
        {
            textViewDead.visibility = View.INVISIBLE
            textViewTemp.text = "$temperature°C"
            textViewFeels.text = "Feels like: $feelslike°C"
        }
    }

    private fun updatePrecip(precip: Double) {
        textViewPrecip.text = "Precip: $precip" + "  mm"
    }

    private fun updateWind(windSpeed: Int) {
        textViewWind.text = "Wind: $windSpeed" + " m/s"
    }

    private fun updateDayTime(isDay: String) {
        if (isDay == "yes")
        {
            imageViewDayTime.setImageResource(R.drawable.ic_sunny)
        }
        else
        {
            CurrentWeatherScreen.setBackgroundColor(resources.getColor(R.color.colorDarkTheme))
            bottom_nav.setBackgroundColor(resources.getColor(R.color.colorDarkTheme))

            textViewCity.setTextColor(resources.getColor(R.color.colorLightTextTheme))
            textViewDate.setTextColor(resources.getColor(R.color.colorLightTextTheme))
            textViewTemp.setTextColor(resources.getColor(R.color.colorLightTextTheme))
            textViewFeels.setTextColor(resources.getColor(R.color.colorLightTextTheme))
            textViewWind.setTextColor(resources.getColor(R.color.colorLightTextTheme))
            textViewPrecip.setTextColor(resources.getColor(R.color.colorLightTextTheme))
        }
    }

}